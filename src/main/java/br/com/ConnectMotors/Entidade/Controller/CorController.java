package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Service.CorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cores")
public class CorController {

    @Autowired  
    private CorService corService;

    @GetMapping
    public ResponseEntity<List<Cor>> listarTodas() {
        List<Cor> cores = corService.listarTodas();
        return ResponseEntity.ok(cores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cor> buscarPorId(@PathVariable Long id) {
        return corService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cor> salvar(@RequestBody Cor cor) {
        Cor novaCor = corService.salvar(cor);
        return ResponseEntity.ok(novaCor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (corService.buscarPorId(id).isPresent()) {
            corService.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}