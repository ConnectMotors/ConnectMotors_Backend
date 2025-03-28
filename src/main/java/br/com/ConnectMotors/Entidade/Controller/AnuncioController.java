package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Service.AnuncioService;
import br.com.ConnectMotors.Entidade.Service.MarcaService;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private MarcaService marcaService;

    // Endpoint para criar um anúncio
    @PostMapping
    public ResponseEntity<Anuncio> criarAnuncio(@RequestBody Anuncio anuncio) {
        // Validar os dados do anúncio antes de salvar
        if (anuncio == null || anuncio.getCarro() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Anuncio novoAnuncio = anuncioService.criarAnuncio(anuncio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAnuncio);
    }

    // Endpoint para listar todas as marcas
    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> listarMarcas() {
        List<Marca> marcas = marcaService.listarMarcas();
        
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(marcas);
    }

    // Endpoint para listar modelos de uma marca específica
}