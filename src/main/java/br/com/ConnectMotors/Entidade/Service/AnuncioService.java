package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Enums.Cambio;
import br.com.ConnectMotors.Entidade.Enums.Carroceria;
import br.com.ConnectMotors.Entidade.Enums.Combustivel;
import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.Anuncio.CepResponse;
import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnuncioService {

    private static final Logger logger = LoggerFactory.getLogger(AnuncioService.class);

    private final AnuncioRepository anuncioRepository;
    private final CarroRepository carroRepository;
    private final MotoRepository motoRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final CorRepository corRepository;
    private final RestTemplate restTemplate;
    private final String uploadDir;

    public AnuncioService(
            AnuncioRepository anuncioRepository,
            UserRepository userRepository,
            CarroRepository carroRepository,
            MotoRepository motoRepository,
            MarcaRepository marcaRepository,
            ModeloRepository modeloRepository,
            CorRepository corRepository,
            RestTemplate restTemplate,
            @Value("${file.upload-dir}") String uploadDir
    ) {
        this.anuncioRepository = anuncioRepository;
        this.carroRepository = carroRepository;
        this.motoRepository = motoRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
        this.corRepository = corRepository;
        this.restTemplate = restTemplate;
        this.uploadDir = uploadDir;
    }

    private CepResponse consultarCep(String cep) {
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        String url = "https://viacep.com.br/ws/" + cepLimpo + "/json/";
        try {
            ResponseEntity<CepResponse> response = restTemplate.getForEntity(url, CepResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                CepResponse cepResponse = response.getBody();
                if (cepResponse.getCep() == null || cepResponse.getLocalidade() == null || cepResponse.getUf() == null) {
                    throw new IllegalArgumentException("CEP inválido ou não encontrado: " + cep);
                }
                return cepResponse;
            } else {
                throw new IllegalArgumentException("Erro ao consultar o CEP: " + cep);
            }
        } catch (Exception e) {
            logger.error("Erro ao consultar o CEP {}: {}", cep, e.getMessage());
            throw new IllegalArgumentException("Erro ao consultar o CEP: " + cep, e);
        }
    }

    public List<Anuncio> listarAnuncios() {
        logger.info("Listando todos os anúncios");
        return anuncioRepository.findAll();
    }

    @Transactional
    public List<Anuncio> filtrarAnuncios(
            Long marcaId, Long modeloId, Long corId, String cambio, String combustivel,
            String carroceria, String freio, String partida, String cilindrada,
            Integer anoFabricacao, Integer anoModelo, String motor, String versao,
            Double precoMin, Double precoMax, String quilometragemMax, String tipoVeiculo
    ) {
        logger.info("Filtrando anúncios com os critérios fornecidos");

        List<Anuncio> anuncios = new ArrayList<>();

        // Converte parâmetros para enums, se aplicável
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
            carroceriaEnum = carroceria != null && !carroceria.isEmpty() ? Carroceria.valueOf(carroceria.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            logger.warn("Carroceria inválida: {}", carroceria);
        }

        // Filtra por tipo de veículo
        if (tipoVeiculo == null || tipoVeiculo.isEmpty() || "CARRO".equalsIgnoreCase(tipoVeiculo)) {
            // Filtra anúncios de Carro
            List<Anuncio> anunciosCarro = anuncioRepository.findByFiltrosCarro(
                    marcaId, modeloId, corId, cambioEnum, combustivelEnum, carroceriaEnum,
                    anoFabricacao, anoModelo, motor, versao, precoMin, precoMax, quilometragemMax
            );
            anuncios.addAll(anunciosCarro);
        }

        if (tipoVeiculo == null || tipoVeiculo.isEmpty() || "MOTO".equalsIgnoreCase(tipoVeiculo)) {
            // Filtra anúncios de Moto
            List<Anuncio> anunciosMoto = anuncioRepository.findByFiltrosMoto(
                    marcaId, modeloId, corId, freio, partida, cilindrada, combustivel,
                    anoFabricacao, anoModelo, versao, precoMin, precoMax, quilometragemMax
            );
            anuncios.addAll(anunciosMoto);
        }

        // Remove duplicatas (se houver) e retorna a lista
        return anuncios.stream().distinct().collect(Collectors.toList());
    }

    @Transactional
    public Anuncio criarAnuncio(@Valid AnuncioDTO anuncioDTO, User usuario) {
        logger.info("Criando novo anúncio para o usuário: {}", usuario.getUsername());

        // Consulta a API de CEP
        CepResponse cepResponse = consultarCep(anuncioDTO.getCep());

        // Valida tipo de veículo
        if (!"CARRO".equalsIgnoreCase(anuncioDTO.getTipoVeiculo()) && !"MOTO".equalsIgnoreCase(anuncioDTO.getTipoVeiculo())) {
            throw new IllegalArgumentException("Tipo de veículo inválido: " + anuncioDTO.getTipoVeiculo());
        }

        // Cria o veículo
        if ("CARRO".equalsIgnoreCase(anuncioDTO.getTipoVeiculo())) {
            // Valida campos obrigatórios para Carro
            if (anuncioDTO.getCambio() == null || anuncioDTO.getCombustivel() == null || anuncioDTO.getCarroceria() == null) {
                throw new IllegalArgumentException("Câmbio, combustível e carroceria são obrigatórios para carros.");
            }

            Carro carro = new Carro();
            carro.setMarca(marcaRepository.findById(anuncioDTO.getMarcaId())
                    .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + anuncioDTO.getMarcaId())));
            carro.setModelo(modeloRepository.findById(anuncioDTO.getModeloId())
                    .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + anuncioDTO.getModeloId())));
            carro.setCor(corRepository.findById(anuncioDTO.getCorId())
                    .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + anuncioDTO.getCorId())));
            carro.setAnoFabricacao(anuncioDTO.getAnoFabricacao());
            carro.setAnoModelo(anuncioDTO.getAnoModelo());
            carro.setVersao(anuncioDTO.getVersao());

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

            // Salva o carro
            carro = carroRepository.save(carro);
            logger.info("Carro salvo com ID: {}", carro.getId());

            // Cria o anúncio
            Anuncio anuncio = new Anuncio();
            anuncio.setUsuario(usuario);
            anuncio.setCarro(carro);
            anuncio.setMoto(null);
            anuncio.setCep(anuncioDTO.getCep());
            anuncio.setLocalidade(cepResponse.getLocalidade());
            anuncio.setUf(cepResponse.getUf());
            anuncio.setPreco(anuncioDTO.getPreco());
            anuncio.setDescricao(anuncioDTO.getDescricao());
            anuncio.setQuilometragem(anuncioDTO.getQuilometragem());
            anuncio.setImagensPaths(salvarImagens(anuncioDTO.getImagens()));

            // Salva o anúncio
            Anuncio savedAnuncio = anuncioRepository.save(anuncio);
            logger.info("Anúncio salvo com ID: {}", savedAnuncio.getId());
            return savedAnuncio;
        } else {
            // Valida campos obrigatórios para Moto
            if (anuncioDTO.getFreio() == null || anuncioDTO.getPartida() == null || anuncioDTO.getCilindrada() == null) {
                throw new IllegalArgumentException("Freio, partida e cilindrada são obrigatórios para motos.");
            }

            Moto moto = new Moto();
            moto.setMarca(marcaRepository.findById(anuncioDTO.getMarcaId())
                    .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + anuncioDTO.getMarcaId())));
            moto.setModelo(modeloRepository.findById(anuncioDTO.getModeloId())
                    .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + anuncioDTO.getModeloId())));
            moto.setCor(corRepository.findById(anuncioDTO.getCorId())
                    .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + anuncioDTO.getCorId())));
            moto.setAnoFabricacao(anuncioDTO.getAnoFabricacao());
            moto.setAnoModelo(anuncioDTO.getAnoModelo());
            moto.setVersao(anuncioDTO.getVersao());
            moto.setFreio(anuncioDTO.getFreio());
            moto.setPartida(anuncioDTO.getPartida());
            moto.setCilindrada(anuncioDTO.getCilindrada());
            moto.setCombustivel(anuncioDTO.getCombustivel());

            // Salva a moto
            moto = motoRepository.save(moto);
            logger.info("Moto salva com ID: {}", moto.getId());

            // Cria o anúncio
            Anuncio anuncio = new Anuncio();
            anuncio.setUsuario(usuario);
            anuncio.setCarro(null);
            anuncio.setMoto(moto);
            anuncio.setCep(anuncioDTO.getCep());
            anuncio.setLocalidade(cepResponse.getLocalidade());
            anuncio.setUf(cepResponse.getUf());
            anuncio.setPreco(anuncioDTO.getPreco());
            anuncio.setDescricao(anuncioDTO.getDescricao());
            anuncio.setQuilometragem(anuncioDTO.getQuilometragem());
            anuncio.setImagensPaths(salvarImagens(anuncioDTO.getImagens()));

            // Salva o anúncio
            Anuncio savedAnuncio = anuncioRepository.save(anuncio);
            logger.info("Anúncio salvo com ID: {}", savedAnuncio.getId());
            return savedAnuncio;
        }
    }

    private List<String> salvarImagens(List<MultipartFile> imagens) {
        List<String> imagensPaths = new ArrayList<>();
        if (imagens != null && !imagens.isEmpty()) {
            for (MultipartFile imagem : imagens) {
                String imagemPath = salvarImagem(imagem);
                imagensPaths.add(imagemPath);
            }
        }
        return imagensPaths;
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
            Path uploadPath = Paths.get(uploadDir).normalize();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);
            Files.createDirectories(uploadPath);
            if (!Files.isWritable(uploadPath)) {
                throw new IOException("Sem permissão para escrever no diretório: " + uploadPath);
            }
            Files.write(filePath, foto.getBytes());
            logger.info("Imagem salva em: {}", filePath);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            logger.error("Erro ao salvar a imagem: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    }
}