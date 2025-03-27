package br.com.ConnectMotors.Entidade.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/admin/marcas")
@CrossOrigin
@Tag(name = "Marca", description = "Endpoints para cadastro, edição e exclusão de marcas de veículos")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @PostMapping
    @Operation(
        summary = "Cadastrar uma nova marca",
        description = "Cadastra uma nova marca no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Marca cadastrada com sucesso", 
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Marca.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos")
        }
    )
    public ResponseEntity<Marca> cadastrarMarca(@org.springframework.web.bind.annotation.RequestBody Marca marca) {
        if (marca == null || marca.getNome() == null || marca.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da marca não pode ser vazio");
        }
        System.out.println("Marca recebida: " + marca.getNome());
        Marca novaMarca = marcaService.cadastrarMarca(marca);
        return ResponseEntity.ok(novaMarca);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas as marcas",
        description = "Retorna a lista de todas as marcas cadastradas no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas recuperada com sucesso", 
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
        }
    )
    public ResponseEntity<List<Marca>> listarMarcas() {
        List<Marca> marcas = marcaService.listarMarcas();
        return ResponseEntity.ok(marcas);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Editar uma marca existente",
        description = "Edita os dados de uma marca já cadastrada.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Marca editada com sucesso", 
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Marca.class))),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada")
        }
    )
    public ResponseEntity<Marca> editarMarca(
        @PathVariable Long id,
        @org.springframework.web.bind.annotation.RequestBody Marca marca
    ) {
        if (marca == null || marca.getNome() == null || marca.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da marca não pode ser vazio");
        }
        Marca marcaEditada = marcaService.editarMarca(id, marca);
        if (marcaEditada != null) {
            return ResponseEntity.ok(marcaEditada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir uma marca",
        description = "Exclui uma marca do sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Marca excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada")
        }
    )
    public ResponseEntity<Void> excluirMarca(@PathVariable Long id) {
        boolean excluida = marcaService.excluirMarca(id);
        if (excluida) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}