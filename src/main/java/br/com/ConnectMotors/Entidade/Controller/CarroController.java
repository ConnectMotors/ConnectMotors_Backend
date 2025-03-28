package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Service.CarroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<Carro> cadastrarCarro(@RequestBody Carro carro) {
        // Validação básica no controller
        if (carro == null || carro.getMarca() == null || carro.getModelo() == null ||
            carro.getCor() == null || carro.getCor().isEmpty() ||
            carro.getCambio() == null || carro.getCambio().isEmpty() ||
            carro.getCombustivel() == null || carro.getCombustivel().isEmpty() ||
            carro.getCarroceria() == null || carro.getCarroceria().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios (marca, modelo, cor, câmbio, combustível e carroceria) devem ser preenchidos");
        }
        if (carro.getAno() <= 0) {
            throw new IllegalArgumentException("O ano do carro deve ser maior que zero");
        }

        Carro novoCarro = carroService.cadastrarCarro(carro);
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
        summary = "Editing um carro existente",
        description = "Edita os dados de um carro já cadastrado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Carro editado com sucesso", 
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Carro.class))),
            @ApiResponse(responseCode = "404", description = "Carro não encontrado")
        }
    )
    public ResponseEntity<Carro> editarCarro(
        @PathVariable Long id,
        @RequestBody Carro carro
    ) {
        // Validação básica no controller
        if (carro == null || carro.getMarca() == null || carro.getModelo() == null ||
            carro.getCor() == null || carro.getCor().isEmpty() ||
            carro.getCambio() == null || carro.getCambio().isEmpty() ||
            carro.getCombustivel() == null || carro.getCombustivel().isEmpty() ||
            carro.getCarroceria() == null || carro.getCarroceria().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios (marca, modelo, cor, câmbio, combustível e carroceria) devem ser preenchidos");
        }
        if (carro.getAno() <= 0) {
            throw new IllegalArgumentException("O ano do carro deve ser maior que zero");
        }

        carro.setId(id); // Define o ID do carro a ser editado
        Carro carroEditado = carroService.cadastrarCarro(carro); // Reutiliza o método de cadastro para edição
        if (carroEditado != null) {
            return ResponseEntity.ok(carroEditado);
        }
        return ResponseEntity.notFound().build();
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
        boolean excluido = carroService.excluirCarro(id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}