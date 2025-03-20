package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Car.Car;
import br.com.ConnectMotors.Entidade.Model.Car.CarRequestDTO;
import br.com.ConnectMotors.Entidade.Service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin
@Tag(name = "Carros", description = "Endpoints para gerenciamento de carros")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/register")
    @Operation(summary = "Cadastrar um carro", description = "Registra um novo carro no sistema.")
    @ApiResponse(responseCode = "200", description = "Carro cadastrado com sucesso", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)))
    public ResponseEntity<Car> registerCar(@ModelAttribute CarRequestDTO carRequest) throws IOException {
        return ResponseEntity.ok(carService.registerCar(carRequest));
    }

    @GetMapping
    @Operation(summary = "Listar todos os carros", description = "Retorna uma lista de todos os carros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de carros retornada com sucesso")
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carro por ID", description = "Retorna um carro específico pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Carro encontrado")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public ResponseEntity<Car> getCarById(@Parameter(description = "ID do carro", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um carro", description = "Edita os detalhes de um carro, como preço e quilometragem.")
    @ApiResponse(responseCode = "200", description = "Carro atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public ResponseEntity<Car> updateCar(@Parameter(description = "ID do carro", example = "1") @PathVariable Long id, 
                                         @RequestBody CarRequestDTO carRequest) throws IOException {
        return ResponseEntity.ok(carService.updateCar(id, carRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um carro", description = "Remove um carro cadastrado do banco de dados.")
    @ApiResponse(responseCode = "200", description = "Carro excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public ResponseEntity<?> deleteCar(@Parameter(description = "ID do carro", example = "1") @PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
