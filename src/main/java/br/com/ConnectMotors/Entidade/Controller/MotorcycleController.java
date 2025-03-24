package br.com.ConnectMotors.Entidade.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ConnectMotors.Entidade.Model.Motorcycle.Motorcycle;
import br.com.ConnectMotors.Entidade.Model.Motorcycle.MotorcycleRequestDTO;
import br.com.ConnectMotors.Entidade.Service.MotorcycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/motorcycles")
@CrossOrigin
@Tag(name = "Motos", description = "Endpoints para gerenciamento de motos")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;

    @PostMapping("/register")
    @Operation(summary = "Cadastrar uma moto", description = "Registra uma nova moto no sistema.")
    @ApiResponse(responseCode = "200", description = "Moto cadastrada com sucesso", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Motorcycle.class)))
    public ResponseEntity<Motorcycle> registerMotorcycle(@ModelAttribute MotorcycleRequestDTO motorcycle) throws IOException {
        return ResponseEntity.ok(motorcycleService.registerMotorcycle(motorcycle));
    }

    @GetMapping
    @Operation(summary = "Listar todas as motos", description = "Retorna uma lista de todas as motos cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de motos retornada com sucesso")
    public ResponseEntity<List<Motorcycle>> getAllMotorcycles() {
        return ResponseEntity.ok(motorcycleService.getAllMotorcycles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID", description = "Retorna uma moto específica pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Moto encontrada")
    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    public ResponseEntity<Motorcycle> getMotorcycleById(@Parameter(description = "ID da moto", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(motorcycleService.getMotorcycleById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de uma moto", description = "Edita os detalhes de uma moto, como preço e quilometragem.")
    @ApiResponse(responseCode = "200", description = "Moto atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    public ResponseEntity<Motorcycle> updateMotorcycle(@Parameter(description = "ID da moto", example = "1") @PathVariable Long id, 
                                                       @RequestBody MotorcycleRequestDTO motorcycleRequest) throws IOException {
        return ResponseEntity.ok(motorcycleService.updateMotorcycle(id, motorcycleRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma moto", description = "Remove uma moto cadastrada do banco de dados.")
    @ApiResponse(responseCode = "200", description = "Moto excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
    public ResponseEntity<?> deleteMotorcycle(@Parameter(description = "ID da moto", example = "1") @PathVariable Long id) {
        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.ok().build();
    }
}
