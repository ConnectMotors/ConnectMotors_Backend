package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private UserRepository userRepository; // Adicionado o repositório de usuários

    @PostMapping
    public Anuncio criarAnuncio(@RequestBody Anuncio anuncio) {
        // Obter o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Nome do usuário autenticado
    
        // Buscar o usuário no banco de dados
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));
    
        // Associar o usuário ao anúncio
        anuncio.setUsuario(usuario);
    
        // Salvar o anúncio
        return anuncioRepository.save(anuncio);
    }

    @GetMapping("/marcas")
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    @GetMapping("/modelos/{marcaId}")
    public List<Modelo> listarModelosPorMarca(@PathVariable Long marcaId) {
        return modeloRepository.findByMarcaId(marcaId);
    }
}