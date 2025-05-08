package br.com.ConnectMotors.Entidade.Controller;

import br.com.ConnectMotors.Entidade.Model.Mensagem.Mensagem;
import br.com.ConnectMotors.Entidade.Model.Mensagem.MensagemRequest;
import br.com.ConnectMotors.Entidade.Service.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@CrossOrigin
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<Mensagem> criarMensagem(@RequestBody MensagemRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Mensagem novaMensagem = mensagemService.enviarMensagem(req, username);
        return ResponseEntity.ok(novaMensagem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Mensagem>> listarMensagens(@PathVariable Long userId) {
        List<Mensagem> mensagens = mensagemService.listarMensagens(userId);
        return ResponseEntity.ok(mensagens);
    }
}
