package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Motorcycle.Motorcycle;
import br.com.ConnectMotors.Entidade.Model.Motorcycle.MotorcycleRequestDTO;
import br.com.ConnectMotors.Entidade.Repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public MotorcycleService(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    /**
     * Registra uma nova moto no banco de dados, incluindo upload de imagens.
     */
    public Motorcycle registerMotorcycle(MotorcycleRequestDTO motorcycleRequest) throws IOException {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setBrand(motorcycleRequest.getBrand());
        motorcycle.setModel(motorcycleRequest.getModel());
        motorcycle.setYear(motorcycleRequest.getYear());
        motorcycle.setMileage(motorcycleRequest.getMileage());
        motorcycle.setPrice(motorcycleRequest.getPrice());

        List<String> imageFileNames = saveImages(motorcycleRequest.getImages());
        motorcycle.setImageFileNames(imageFileNames);

        return motorcycleRepository.save(motorcycle);
    }

    /**
     * Retorna a lista de todas as motos cadastradas.
     */
    public List<Motorcycle> getAllMotorcycles() {
        return motorcycleRepository.findAll();
    }

    /**
     * Retorna uma moto pelo ID.
     */
    public Motorcycle getMotorcycleById(Long id) {
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
    }

    /**
     * Atualiza os dados de uma moto existente.
     */
    public Motorcycle updateMotorcycle(Long id, MotorcycleRequestDTO motorcycleRequest) throws IOException {
        Motorcycle motorcycle = getMotorcycleById(id);
        motorcycle.setBrand(motorcycleRequest.getBrand());
        motorcycle.setModel(motorcycleRequest.getModel());
        motorcycle.setYear(motorcycleRequest.getYear());
        motorcycle.setMileage(motorcycleRequest.getMileage());
        motorcycle.setPrice(motorcycleRequest.getPrice());

        if (motorcycleRequest.getImages() != null && !motorcycleRequest.getImages().isEmpty()) {
            List<String> imageFileNames = saveImages(motorcycleRequest.getImages());
            motorcycle.setImageFileNames(imageFileNames);
        }

        return motorcycleRepository.save(motorcycle);
    }

    /**
     * Exclui uma moto do banco de dados.
     */
    public void deleteMotorcycle(Long id) {
        Motorcycle motorcycle = getMotorcycleById(id);
        motorcycleRepository.delete(motorcycle);
    }

    /**
     * Método auxiliar para salvar imagens no servidor.
     */
    private List<String> saveImages(List<MultipartFile> images) throws IOException {
        List<String> imageFileNames = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                image.transferTo(filePath.toFile());
                imageFileNames.add(fileName);
            }
        }
        return imageFileNames;
    }
}