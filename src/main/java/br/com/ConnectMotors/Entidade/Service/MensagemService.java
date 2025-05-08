package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Mensagem.Mensagem;
import br.com.ConnectMotors.Entidade.Model.Mensagem.MensagemRequest;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.MensagemRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UserRepository userRepository;

    public Mensagem enviarMensagem(MensagemRequest req, String remetenteUsername) {
        User remetente = userRepository.findByUsername(remetenteUsername)
                .orElseThrow(() -> new RuntimeException("Usuário remetente não encontrado."));

        User destinatario;

        if (req.getDestinatarioId() != null) {
            destinatario = userRepository.findById(req.getDestinatarioId())
                    .orElseThrow(() -> new RuntimeException("Destinatário não encontrado pelo ID."));
        } else if (req.getDestinatarioUsername() != null) {
            destinatario = userRepository.findByUsername(req.getDestinatarioUsername())
                    .orElseThrow(() -> new RuntimeException("Destinatário não encontrado pelo username."));
        } else {
            throw new RuntimeException("É necessário fornecer o ID ou o username do destinatário.");
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setRemetente(remetente);
        mensagem.setDestinatario(destinatario);
        mensagem.setConteudo(req.getConteudo());
        mensagem.setDataEnvio(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> listarMensagens(Long userId) {
        return mensagemRepository.findByRemetenteIdOrDestinatarioId(userId, userId);
    }
}
