package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.Moto.MotoDTO;
import br.com.ConnectMotors.Entidade.Service.MotoService;
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
    public ResponseEntity<Moto> cadastrarMoto(@RequestBody MotoDTO motoDTO) {
        // Validação básica no controller
        if (motoDTO == null || motoDTO.getMarca() == null || motoDTO.getMarca().isEmpty() ||
            motoDTO.getModelo() == null || motoDTO.getModelo().isEmpty() ||
            motoDTO.getCor() == null || motoDTO.getCor().isEmpty() ||
            motoDTO.getFreio() == null || motoDTO.getFreio().isEmpty() ||
            motoDTO.getPartida() == null || motoDTO.getPartida().isEmpty() ||
            motoDTO.getCombustivel() == null || motoDTO.getCombustivel().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios (marca, modelo, cor, freio, partida, combustível) devem ser preenchidos");
        }
        if (motoDTO.getAno() <= 0) {
            throw new IllegalArgumentException("O ano da moto deve ser maior que zero");
        }
        if (motoDTO.getCilindrada() <= 0) {
            throw new IllegalArgumentException("A cilindrada da moto deve ser maior que zero");
        }

        Moto novaMoto = motoService.cadastrarMoto(motoDTO);
        return ResponseEntity.ok(novaMoto);
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
            @RequestBody MotoDTO motoDTO
    ) {
        // Validação básica no controller
        if (motoDTO == null || motoDTO.getMarca() == null || motoDTO.getMarca().isEmpty() ||
            motoDTO.getModelo() == null || motoDTO.getModelo().isEmpty() ||
            motoDTO.getCor() == null || motoDTO.getCor().isEmpty() ||
            motoDTO.getFreio() == null || motoDTO.getFreio().isEmpty() ||
            motoDTO.getPartida() == null || motoDTO.getPartida().isEmpty() ||
            motoDTO.getCombustivel() == null || motoDTO.getCombustivel().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios (marca, modelo, cor, freio, partida, combustível) devem ser preenchidos");
        }
        if (motoDTO.getAno() <= 0) {
            throw new IllegalArgumentException("O ano da moto deve ser maior que zero");
        }
        if (motoDTO.getCilindrada() <= 0) {
            throw new IllegalArgumentException("A cilindrada da moto deve ser maior que zero");
        }

        Moto motoEditada = motoService.cadastrarMoto(motoDTO); // Reutiliza o método para edição (pode ser ajustado)
        motoEditada.setId(id); // Define o ID para edição
        {
            return ResponseEntity.ok(motoEditada);
        }
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
        boolean excluido = motoService.excluirMoto(id);
        if (excluido) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}