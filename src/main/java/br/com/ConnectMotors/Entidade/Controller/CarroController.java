package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Carro.CarroDTO;
import br.com.ConnectMotors.Entidade.Service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/carros")
@CrossOrigin
@Tag(name = "Carro", description = "Endpoints para cadastro, edição e exclusão de carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo carro",
        description = "Cadastra um novo carro no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carro cadastrado com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carro.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos")
        }
    )
    public ResponseEntity<Carro> cadastrarCarro(@Valid @RequestBody CarroDTO carroDTO) {
        Carro novoCarro = carroService.cadastrarCarro(carroDTO);
        return ResponseEntity.ok(novoCarro);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os carros",
        description = "Retorna a lista de todos os carros cadastrados no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de carros recuperada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
        }
    )
    public ResponseEntity<List<Carro>> listarCarros() {
        List<Carro> carros = carroService.listarCarros();
        return ResponseEntity.ok(carros);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Editar um carro existente",
        description = "Edita os dados de um carro já cadastrado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carro editado com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carro.class))),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
        }
    )
    public ResponseEntity<Carro> editarCarro(
            @PathVariable Long id,
            @Valid @RequestBody CarroDTO carroDTO
    ) {
        Carro carroEditado = carroService.editarCarro(id, carroDTO);
        return ResponseEntity.ok(carroEditado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um carro",
        description = "Exclui um carro do sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
        }
    )
    public ResponseEntity<Void> excluirCarro(@PathVariable Long id) {
        carroService.excluirCarro(id);
        return ResponseEntity.ok().build();
    }
}