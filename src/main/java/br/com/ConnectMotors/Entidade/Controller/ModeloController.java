package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Modelo.ModeloDTO;
import br.com.ConnectMotors.Entidade.Service.ModeloService;
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
@RequestMapping("/admin/modelos")
@CrossOrigin
@Tag(name = "Modelo", description = "Endpoints para cadastro, edição e exclusão de modelos de veículos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo modelo",
        description = "Cadastra um novo modelo no sistema associado a uma marca pelo nome.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Modelo cadastrado com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modelo.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos")
        }
    )
    public ResponseEntity<Modelo> cadastrarModelo(@RequestBody ModeloDTO modeloDTO) {
        // Validações básicas no controller
        if (modeloDTO == null || modeloDTO.getNome() == null || modeloDTO.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do modelo não pode ser vazio");
        }
        if (modeloDTO.getMarca() == null || modeloDTO.getMarca().isEmpty()) {
            throw new IllegalArgumentException("O nome da marca deve ser informado");
        }

        // Delega ao serviço a criação do modelo
        Modelo novoModelo = modeloService.cadastrarModelo(modeloDTO);
        return ResponseEntity.ok(novoModelo);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os modelos",
        description = "Retorna a lista de todos os modelos cadastrados no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de modelos recuperada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
        }
    )
    public ResponseEntity<List<Modelo>> listarModelos() {
        List<Modelo> modelos = modeloService.listarModelos();
        return ResponseEntity.ok(modelos);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Editar um modelo existente",
        description = "Edita os dados de um modelo já cadastrado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Modelo editado com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Modelo.class))),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
        }
    )
    public ResponseEntity<Modelo> editarModelo(
            @PathVariable Long id,
            @RequestBody ModeloDTO modeloDTO
    ) {
        // Validações básicas no controller
        if (modeloDTO == null || modeloDTO.getNome() == null || modeloDTO.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do modelo não pode ser vazio");
        }
        if (modeloDTO.getMarca() == null || modeloDTO.getMarca().isEmpty()) {
            throw new IllegalArgumentException("O nome da marca deve ser informado");
        }

        Modelo modeloEditado = modeloService.editarModelo(id, modeloDTO);
        if (modeloEditado != null) {
            return ResponseEntity.ok(modeloEditado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um modelo",
        description = "Exclui um modelo do sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Modelo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
        }
    )
    public ResponseEntity<Void> excluirModelo(@PathVariable Long id) {
        boolean excluido = modeloService.excluirModelo(id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}