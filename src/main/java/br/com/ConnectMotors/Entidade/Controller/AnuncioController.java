package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Service.AnuncioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
@CrossOrigin
public class AnuncioController {

    private final AnuncioService anuncioService;

    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    // ============================
    // Rotas Públicas
    // ============================

    @GetMapping
    @Operation(
            summary = "Listar todos os anúncios",
            description = "Retorna a lista de todos os anúncios cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de anúncios recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
            }
    )
    public ResponseEntity<List<Anuncio>> listarAnuncios() {
        List<Anuncio> anuncios = anuncioService.listarAnuncios();
        return ResponseEntity.ok(anuncios);
    }

    @GetMapping("/filtros")
    @Operation(
            summary = "Filtrar anúncios dinamicamente",
            description = "Filtra os anúncios cadastrados com base nos parâmetros fornecidos. Os filtros são opcionais e podem ser combinados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de anúncios filtrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
            }
    )
    public ResponseEntity<List<Anuncio>> filtrarAnuncios(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) Long corId,
            @RequestParam(required = false) String cambio,
            @RequestParam(required = false) String combustivel,
            @RequestParam(required = false) String carroceria,
            @RequestParam(required = false) Integer anoFabricacao,
            @RequestParam(required = false) Integer anoModelo,
            @RequestParam(required = false) String motor,
            @RequestParam(required = false) String versao,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(required = false) String quilometragemMax
    ) {
        List<Anuncio> anuncios = anuncioService.filtrarAnuncios(
                marcaId, modeloId, corId, cambio, combustivel, carroceria,
                anoFabricacao, anoModelo, motor, versao, precoMin, precoMax, quilometragemMax
        );
        return ResponseEntity.ok(anuncios);
    }

    // ============================
    // Rotas Administrativas
    // ============================

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Criar um novo anúncio",
            description = "Cadastra um novo anúncio no sistema, incluindo os dados do carro e múltiplas imagens.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Anúncio criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Anuncio.class))),
                    @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos")
            }
    )
    public ResponseEntity<Anuncio> criarAnuncio(
            @RequestPart("anuncio") @Valid AnuncioDTO anuncioDTO,
            @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens
    ) {
        // Valida tipo de arquivo das imagens
        if (imagens != null && !imagens.isEmpty()) {
            for (MultipartFile imagem : imagens) {
                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Apenas arquivos de imagem são permitidos.");
                }
            }
            anuncioDTO.setImagens(imagens);
        }
        Anuncio novoAnuncio = anuncioService.criarAnuncio(anuncioDTO);
        return ResponseEntity.ok(novoAnuncio);
    }
}