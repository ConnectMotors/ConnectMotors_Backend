package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Car.Car;
import br.com.ConnectMotors.Entidade.Model.Car.CarRequestDTO;
import br.com.ConnectMotors.Entidade.Repository.CarRepository;
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
public class CarService {

    private final CarRepository carRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Registra um novo carro no banco de dados, incluindo upload de imagens.
     */
    public Car registerCar(CarRequestDTO carRequest) throws IOException {
        Car car = new Car();
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setMileage(carRequest.getMileage());
        car.setPrice(carRequest.getPrice());

        List<String> imageFileNames = saveImages(carRequest.getImages());
        car.setImageFileNames(imageFileNames);

        return carRepository.save(car);
    }

    /**
     * Retorna a lista de todos os carros cadastrados.
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Retorna um carro pelo ID.
     */
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
    }

    /**
     * Atualiza os dados de um carro existente.
     */
    public Car updateCar(Long id, CarRequestDTO carRequest) throws IOException {
        Car car = getCarById(id);
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        car.setMileage(carRequest.getMileage());
        car.setPrice(carRequest.getPrice());

        if (carRequest.getImages() != null && !carRequest.getImages().isEmpty()) {
            List<String> imageFileNames = saveImages(carRequest.getImages());
            car.setImageFileNames(imageFileNames);
        }

        return carRepository.save(car);
    }

    /**
     * Exclui um carro do banco de dados.
     */
    public void deleteCar(Long id) {
        Car car = getCarById(id);
        carRepository.delete(car);
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
