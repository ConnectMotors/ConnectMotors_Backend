package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Enums.Cambio;
import br.com.ConnectMotors.Entidade.Enums.Carroceria;
import br.com.ConnectMotors.Entidade.Enums.Combustivel;
import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Repository.CorRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnuncioService {

    private static final Logger logger = LoggerFactory.getLogger(AnuncioService.class);

    private final AnuncioRepository anuncioRepository;
    private final UserRepository userRepository;
    private final CarroRepository carroRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final CorRepository corRepository;
    private final String uploadDir;

    public AnuncioService(
            AnuncioRepository anuncioRepository,
            UserRepository userRepository,
            CarroRepository carroRepository,
            MarcaRepository marcaRepository,
            ModeloRepository modeloRepository,
            CorRepository corRepository,
            @Value("${file.upload-dir}") String uploadDir
    ) {
        this.anuncioRepository = anuncioRepository;
        this.userRepository = userRepository;
        this.carroRepository = carroRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.corRepository = corRepository;
        this.uploadDir = uploadDir;
    }

    public List<Anuncio> listarAnuncios() {
        logger.info("Listando todos os anúncios");
        return anuncioRepository.findAll();
    }

    @Transactional
    public List<Anuncio> filtrarAnuncios(
            Long marcaId, Long modeloId, Long corId, String cambio, String combustivel,
            String carroceria, Integer anoFabricacao, Integer anoModelo, String motor,
            String versao, Double precoMin, Double precoMax, String quilometragemMax
    ) {
        logger.info("Filtrando anúncios com os critérios fornecidos");

        // Converte as strings para enums (se não forem nulas ou vazias)
        Cambio cambioEnum = null;
        Combustivel combustivelEnum = null;
        Carroceria carroceriaEnum = null;

        try {
            cambioEnum = cambio != null && !cambio.isEmpty() ? Cambio.valueOf(cambio.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            logger.warn("Câmbio inválido: {}", cambio);
        }

        try {
            combustivelEnum = combustivel != null && !combustivel.isEmpty() ? Combustivel.valueOf(combustivel.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            logger.warn("Combustível inválido: {}", combustivel);
        }

        try {
            carroceriaEnum = carroceria != null && !cambio.isEmpty() ? Carroceria.valueOf(carroceria.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            logger.warn("Carroceria inválida: {}", carroceria);
        }

        // Chama o repositório passando os enums
        return anuncioRepository.findByFiltros(
                marcaId, modeloId, corId, cambioEnum, combustivelEnum, carroceriaEnum, anoFabricacao,
                anoModelo, motor, versao, precoMin, precoMax, quilometragemMax
        );
    }

    @Transactional
    public Anuncio criarAnuncio(@Valid AnuncioDTO anuncioDTO) {
        logger.info("Criando novo anúncio para o usuário ID: {}", anuncioDTO.getUsuarioId());

        // Valida e busca o usuário
        User usuario = userRepository.findById(anuncioDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + anuncioDTO.getUsuarioId()));

        // Cria o carro com os dados fornecidos
        Carro carro = new Carro();
        carro.setMarca(marcaRepository.findById(anuncioDTO.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + anuncioDTO.getMarcaId())));
        carro.setModelo(modeloRepository.findById(anuncioDTO.getModeloId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + anuncioDTO.getModeloId())));
        carro.setCor(corRepository.findById(anuncioDTO.getCorId())
                .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + anuncioDTO.getCorId())));
        carro.setAnoFabricacao(anuncioDTO.getAnoFabricacao());
        carro.setAnoModelo(anuncioDTO.getAnoModelo());

        // Valida e definition os enums
        try {
            carro.setCambio(Cambio.valueOf(anuncioDTO.getCambio().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Câmbio inválido: " + anuncioDTO.getCambio());
        }
        try {
            carro.setCombustivel(Combustivel.valueOf(anuncioDTO.getCombustivel().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Combustível inválido: " + anuncioDTO.getCombustivel());
        }
        try {
            carro.setCarroceria(Carroceria.valueOf(anuncioDTO.getCarroceria().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Carroceria inválida: " + anuncioDTO.getCarroceria());
        }

        carro.setMotor(anuncioDTO.getMotor());
        carro.setVersao(anuncioDTO.getVersao());

        // Salva o carro no banco
        carro = carroRepository.save(carro);
        logger.info("Carro salvo com ID: {}", carro.getId());

        // Salva as imagens no servidor
        List<String> imagensPaths = new ArrayList<>();
        if (anuncioDTO.getImagens() != null && !anuncioDTO.getImagens().isEmpty()) {
            for (MultipartFile imagem : anuncioDTO.getImagens()) {
                String imagemPath = salvarImagem(imagem);
                imagensPaths.add(imagemPath);
            }
        }

        // Cria o anúncio
        Anuncio anuncio = new Anuncio();
        anuncio.setUsuario(usuario);
        anuncio.setCarro(carro);
        anuncio.setCep(anuncioDTO.getCep());
        anuncio.setPreco(anuncioDTO.getPreco());
        anuncio.setDescricao(anuncioDTO.getDescricao());
        anuncio.setQuilometragem(anuncioDTO.getQuilometragem());
        anuncio.setImagensPaths(imagensPaths);

        // Salva o anúncio no banco
        Anuncio savedAnuncio = anuncioRepository.save(anuncio);
        logger.info("Anúncio salvo com ID: {}", savedAnuncio.getId());
        return savedAnuncio;
    }

    public String salvarImagem(MultipartFile foto) {
        if (foto == null || foto.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado.");
        }

        String originalFileName = foto.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo inválido.");
        }

        try {
            // Normaliza o caminho do diretório
            Path uploadPath = Paths.get(uploadDir).normalize();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            // Cria os diretórios, se não existirem
            Files.createDirectories(uploadPath);

            // Verifica permissões de escrita
            if (!Files.isWritable(uploadPath)) {
                throw new IOException("Sem permissão para escrever no diretório: " + uploadPath);
            }

            // Salva o arquivo
            Files.write(filePath, foto.getBytes());
            logger.info("Imagem salva em: {}", filePath);

            // Retorna o caminho relativo
            return "/uploads/" + fileName;
        } catch (IOException e) {
            logger.error("Erro ao salvar a imagem: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    }
}