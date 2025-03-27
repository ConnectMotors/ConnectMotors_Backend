package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    public Anuncio criarAnuncio(Anuncio anuncio) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));

        anuncio.setUsuario(usuario);

        return anuncioRepository.save(anuncio);
    }
}
