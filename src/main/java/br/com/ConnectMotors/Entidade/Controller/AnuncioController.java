package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Service.AnuncioService;
import br.com.ConnectMotors.Entidade.Service.MarcaService;
import br.com.ConnectMotors.Entidade.Service.ModeloService;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ModeloService modeloService;

    @PostMapping
    public Anuncio criarAnuncio(@RequestBody Anuncio anuncio) {
        return anuncioService.criarAnuncio(anuncio);
    }

    @GetMapping("/marcas")
    public List<Marca> listarMarcas() {
        return marcaService.listarMarcas();
    }

    @GetMapping("/modelos/{marcaId}")
    public List<Modelo> listarModelosPorMarca(@PathVariable Long marcaId) {
        return modeloService.listarModelosPorMarca(marcaId);
    }
}
