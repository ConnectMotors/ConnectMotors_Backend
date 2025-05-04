package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import br.com.ConnectMotors.Entidade.Service.AnuncioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
@CrossOrigin
public class AnuncioController {

    private static final Logger logger = LoggerFactory.getLogger(AnuncioController.class);

    private final AnuncioService anuncioService;
    private final UserRepository userRepository;

    public AnuncioController(AnuncioService anuncioService, UserRepository userRepository) {
        this.anuncioService = anuncioService;
        this.userRepository = userRepository;
    }

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
            @RequestParam(required = false) String freio,
            @RequestParam(required = false) String partida,
            @RequestParam(required = false) String cilindrada,
            @RequestParam(required = false) Integer anoFabricacao,
            @RequestParam(required = false) Integer anoModelo,
            @RequestParam(required = false) String motor,
            @RequestParam(required = false) String versao,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(required = false) String quilometragemMax,
            @RequestParam(required = false) String tipoVeiculo
    ) {
        List<Anuncio> anuncios = anuncioService.filtrarAnuncios(
                marcaId, modeloId, corId, cambio, combustivel, carroceria,
                freio, partida, cilindrada, anoFabricacao, anoModelo, motor, versao,
                precoMin, precoMax, quilometragemMax, tipoVeiculo
        );
        return ResponseEntity.ok(anuncios);
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Criar um novo anúncio",
            description = "Cadastra um novo anúncio no sistema, incluindo os dados do carro ou moto e múltiplas imagens. O usuário é identificado automaticamente com base na autenticação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Anúncio criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Anuncio.class))),
                    @ApiResponse(responseCode = "400", description = "Erro na validação dos dados fornecidos"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            }
    )
    public ResponseEntity<?> criarAnuncio(
            @RequestPart("anuncio") @Valid AnuncioDTO anuncioDTO,
            @RequestPart(value = "imagens", required = false) List<MultipartFile> imagens
    ) {
        try {
            // Obtém o principal do contexto de segurança
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                logger.error("Principal não é UserDetails: {}", principal.getClass().getName());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
            }

            logger.info("Username extraído: {}", username);

            // Busca o usuário no banco pelo username
            User usuario = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));

            if (imagens != null && !imagens.isEmpty()) {
                for (MultipartFile imagem : imagens) {
                    String contentType = imagem.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.badRequest().body("Apenas arquivos de imagem são permitidos.");
                    }
                }
                anuncioDTO.setImagens(imagens);
            }
            Anuncio novoAnuncio = anuncioService.criarAnuncio(anuncioDTO, usuario);
            return ResponseEntity.ok(novoAnuncio);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro ao criar anúncio: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o anúncio: " + e.getMessage());
        }
    }
}