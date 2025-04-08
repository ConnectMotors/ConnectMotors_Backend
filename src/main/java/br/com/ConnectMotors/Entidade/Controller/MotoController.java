package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.Moto.MotoDTO;
import br.com.ConnectMotors.Entidade.Service.MotoService;
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
@RequestMapping("/admin/motos")
@CrossOrigin
@Tag(name = "Moto", description = "Endpoints para cadastro, edição e exclusão de motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @PostMapping
    @Operation(
        summary = "Cadastrar uma nova moto",
        description = "Cadastra uma nova moto no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Moto cadastrada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Moto.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos")
        }
    )
    public ResponseEntity<Moto> cadastrarMoto(@Valid @RequestBody MotoDTO motoDTO) {
        Moto novaMoto = motoService.cadastrarMoto(motoDTO);
        return ResponseEntity.ok(novaMoto);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Editar uma moto existente",
        description = "Edita os dados de uma moto já cadastrada.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Moto editada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = Moto.class))),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
        }
    )
    public ResponseEntity<Moto> editarMoto(
            @PathVariable Long id,
            @Valid @RequestBody MotoDTO motoDTO
    ) {
        Moto motoEditada = motoService.editarMoto(id, motoDTO);
        return ResponseEntity.ok(motoEditada);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas as motos",
        description = "Retorna a lista de todas as motos cadastradas no sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de motos recuperada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
        }
    )
    public ResponseEntity<List<Moto>> listarMotos() {
        List<Moto> motos = motoService.listarMotos();
        return ResponseEntity.ok(motos);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir uma moto",
        description = "Exclui uma moto do sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Moto excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moto não encontrada")
        }
    )
    public ResponseEntity<Void> excluirMoto(@PathVariable Long id) {
        motoService.excluirMoto(id);
        return ResponseEntity.ok().build();
    }
}