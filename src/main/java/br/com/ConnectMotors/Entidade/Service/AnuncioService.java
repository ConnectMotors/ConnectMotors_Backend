package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Enums.Cambio;
import br.com.ConnectMotors.Entidade.Enums.Carroceria;
import br.com.ConnectMotors.Entidade.Enums.Combustivel;
import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import br.com.ConnectMotors.Entidade.Model.Anuncio.AnuncioDTO;
import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.AnuncioRepository;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;
import br.com.ConnectMotors.Entidade.Repository.CorRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService {

    @Autowired
private MarcaRepository marcaRepository;

@Autowired
private ModeloRepository modeloRepository;

@Autowired
private CorRepository corRepository;
    

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarroRepository carroRepository;

    public List<Anuncio> listarAnuncios() {
        return anuncioRepository.findAll();
    }

    public List<Anuncio> filtrarAnuncios(Long marcaId, Long modeloId, Long corId, String cambio, String combustivel,
    String carroceria, Integer anoFabricacao, Integer anoModelo, String motor,
    String versao, Double precoMin, Double precoMax, String quilometragemMax) {
return anuncioRepository.findByFiltros(marcaId, modeloId, corId, cambio, combustivel, carroceria, anoFabricacao,
          anoModelo, motor, versao, precoMin, precoMax, quilometragemMax);
}

    public Anuncio criarAnuncio(AnuncioDTO anuncioDTO) {
        // Valida e busca o usuário
        User usuario = userRepository.findById(anuncioDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + anuncioDTO.getUsuarioId()));

        // Cria o carro com os dados fornecidos no DTO
        Carro carro = new Carro();
        carro.setMarca(carroRepository.findMarcaById(anuncioDTO.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + anuncioDTO.getMarcaId())));
        carro.setModelo(carroRepository.findModeloById(anuncioDTO.getModeloId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + anuncioDTO.getModeloId())));
        carro.setCor(carroRepository.findCorById(anuncioDTO.getCorId())
                .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + anuncioDTO.getCorId())));
        carro.setAnoFabricacao(anuncioDTO.getAnoFabricacao());
        carro.setAnoModelo(anuncioDTO.getAnoModelo());
        carro.setCambio(Cambio.valueOf(anuncioDTO.getCambio().toUpperCase()));
        carro.setCombustivel(Combustivel.valueOf(anuncioDTO.getCombustivel().toUpperCase()));
        carro.setCarroceria(Carroceria.valueOf(anuncioDTO.getCarroceria().toUpperCase()));
        carro.setMotor(anuncioDTO.getMotor());
        carro.setVersao(anuncioDTO.getVersao());

        // Salva o carro no banco de dados
        carro = carroRepository.save(carro);

        // Cria o anúncio
        Anuncio anuncio = new Anuncio();
        anuncio.setUsuario(usuario);
        anuncio.setCarro(carro);
        anuncio.setCep(anuncioDTO.getCep());
        anuncio.setPreco(anuncioDTO.getPreco());
        anuncio.setDescricao(anuncioDTO.getDescricao());
        anuncio.setQuilometragem(anuncioDTO.getQuilometragem());

        // Salva o anúncio no banco de dados
        return anuncioRepository.save(anuncio);
    }
}