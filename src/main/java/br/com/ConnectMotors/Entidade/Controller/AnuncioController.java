package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Service.AnuncioService;
import br.com.ConnectMotors.Entidade.Service.MarcaService;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private MarcaService marcaService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> criarAnuncio(
            @RequestPart("anuncio") AnuncioDTO anuncioDTO,
            @RequestPart("fotos") List<MultipartFile> fotos) {
        if (!anuncioDTO.isDadosConfirmados()) {
            return ResponseEntity.badRequest().body("Os dados do anunciante devem ser confirmados.");
        }

        // Verificar se a lista de fotos está vazia
        if (fotos == null || fotos.isEmpty()) {
            return ResponseEntity.badRequest().body("Pelo menos uma foto deve ser enviada.");
        }

        // Salvar cada imagem e coletar as URLs
        List<String> fotoUrls = new ArrayList<>();
        for (MultipartFile foto : fotos) {
            String fotoUrl = anuncioService.salvarImagem(foto);
            fotoUrls.add(fotoUrl);
        }

        // Definir as URLs no DTO
        anuncioDTO.setFotos(fotoUrls);

        try {
            Anuncio novoAnuncio = anuncioService.criarAnuncio(anuncioDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Anúncio criado com sucesso!");
            response.put("anuncioId", novoAnuncio.getId().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o anúncio: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Anuncio>> listarAnuncios() {
        List<Anuncio> anuncios = anuncioService.listarAnuncios();

        if (anuncios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(anuncios);
    }

    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> listarMarcas() {
        List<Marca> marcas = marcaService.listarMarcas();

        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(marcas);
    }

    @GetMapping("/marcas/{marcaId}/modelos")
    public ResponseEntity<?> listarModelosPorMarca(@PathVariable Long marcaId) {
        try {
            List<String> modelos = marcaService.listarModelosPorMarca(marcaId);

            if (modelos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(modelos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}