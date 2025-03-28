package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.CepResponse;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import br.com.ConnectMotors.Entidade.Controller.CepController;
import br.com.ConnectMotors.Entidade.Model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CepController cepController; // Injeção do CepController para obter dados do CEP

    public Anuncio criarAnuncio(Anuncio anuncio) {
        // Obter o nome de usuário da sessão ou contexto de segurança
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Buscar o usuário no banco de dados
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Associar o usuário ao anúncio
        anuncio.setUsuario(usuario);

        // Consultar o CEP para obter a cidade e estado
        CepResponse cepResponse = cepController.buscarCep(anuncio.getCep()); // Consumindo o CepController

        // Preencher os campos de cidade e estado a partir da resposta do ViaCEP
        anuncio.setCidade(cepResponse.getLocalidade());
        anuncio.setEstado(cepResponse.getUf());

        // Salvar o anúncio
        return anuncioRepository.save(anuncio);
    }
}
