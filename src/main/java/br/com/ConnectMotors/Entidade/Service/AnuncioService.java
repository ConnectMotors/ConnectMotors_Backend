package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.Anuncio.CepResponse;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import br.com.ConnectMotors.Entidade.Controller.CepController;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;

import java.util.List;

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
    private CarroRepository carroRepository;

    @Autowired
    private CepController cepController; // Injeção do CepController para obter dados do CEP

    public Anuncio criarAnuncio(AnuncioDTO anuncioDTO) {
        // Obter o nome de usuário da sessão ou contexto de segurança
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Buscar o usuário no banco de dados
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Buscar o carro pelo ID
        Carro carro = carroRepository.findById(anuncioDTO.getCarroId())
                .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado"));

        // Consultar o CEP para obter a cidade, estado e bairro
        CepResponse cepResponse = cepController.buscarCep(anuncioDTO.getCep());

        // Criar o anúncio
        Anuncio anuncio = new Anuncio();
        anuncio.setUsuario(usuario);
        anuncio.setCarro(carro);
        anuncio.setPreco(anuncioDTO.getPreco());
        anuncio.setDescricao(anuncioDTO.getDescricao());
        anuncio.setQuilometragem(anuncioDTO.getQuilometragem());
        anuncio.setCep(anuncioDTO.getCep());
        anuncio.setCidade(cepResponse.getLocalidade());
        anuncio.setEstado(cepResponse.getUf());
        anuncio.setBairro(cepResponse.getBairro());
        anuncio.setFotos(List.of(anuncioDTO.getFoto())); // Adiciona a foto como uma lista
        anuncio.setDadosConfirmados(anuncioDTO.isDadosConfirmados());

        // Salvar o anúncio
        return anuncioRepository.save(anuncio);
    }

    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }
}