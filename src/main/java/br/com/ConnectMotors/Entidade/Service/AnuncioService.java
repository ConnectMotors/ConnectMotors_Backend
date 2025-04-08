package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.Anuncio.CepResponse;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import br.com.ConnectMotors.Entidade.Controller.CepController;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private CepController cepController;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Anuncio criarAnuncio(AnuncioDTO anuncioDTO) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Carro carro = carroRepository.findById(anuncioDTO.getCarroId())
                .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado"));

        CepResponse cepResponse = cepController.buscarCep(anuncioDTO.getCep());

        String fotoUrl = salvarImagem(anuncioDTO.getFoto());

        Anuncio anuncio = new Anuncio();
        anuncio.setUsuario(usuario);
        anuncio.setCarro(carro);
        anuncio.setPreco(anuncioDTO.getPreco());
        anuncio.setDescricao(anuncioDTO.getDescricao());
        anuncio.setQuilometragem(anuncioDTO.getQuilometragem());
        anuncio.setCep(anuncioDTO.getCep());
        anuncio.setCidade(cepResponse.getLocalidade());
        anuncio.setEstado(cepResponse.getUf());
        anuncio.setBairro(cepResponse.getBairro());
        anuncio

.setFotos(List.of(fotoUrl));
        anuncio.setDadosConfirmados(anuncioDTO.isDadosConfirmados());

        return anuncioRepository.save(anuncio);
    }

    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    private String salvarImagem(MultipartFile foto) {
        if (foto == null || foto.isEmpty()) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado.");
        }

        try {
            String normalizedUploadDir = uploadDir.endsWith("/") ? uploadDir : uploadDir + "/";
            String fileName = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            String filePath = normalizedUploadDir + fileName;

            File directory = new File(normalizedUploadDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new IOException("Não foi possível criar o diretório: " + normalizedUploadDir);
                }
            }

            if (!directory.canWrite()) {
                throw new IOException("Sem permissão para escrever no diretório: " + normalizedUploadDir);
            }

            File destinationFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(destinationFile)) {
                fos.write(foto.getBytes());
            }

            if (!destinationFile.exists()) {
                throw new IOException("Falha ao salvar o arquivo: " + filePath);
            }

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    }
}