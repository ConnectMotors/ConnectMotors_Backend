package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    // Cadastrar um novo carro
    public Carro cadastrarCarro(Carro carro) {
        // Validações básicas
        if (carro.getAno() <= 0) {
            throw new IllegalArgumentException("O ano do carro deve ser maior que zero");
        }
        if (carro.getCor() == null || carro.getCor().isEmpty()) {
            throw new IllegalArgumentException("A cor do carro é obrigatória");
        }
        if (carro.getCambio() == null || carro.getCambio().isEmpty()) {
            throw new IllegalArgumentException("O tipo de câmbio é obrigatório");
        }
        if (carro.getCombustivel() == null || carro.getCombustivel().isEmpty()) {
            throw new IllegalArgumentException("O tipo de combustível é obrigatório");
        }
        if (carro.getCarroceria() == null || carro.getCarroceria().isEmpty()) {
            throw new IllegalArgumentException("O tipo de carroceria é obrigatório");
        }

        // Verifica se a marca existe
        Marca marca = marcaRepository.findById(carro.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + carro.getMarca().getId()));

        // Verifica se o modelo existe
        Modelo modelo = modeloRepository.findById(carro.getModelo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + carro.getModelo().getId()));

        // Associa as entidades validadas
        carro.setMarca(marca);
        carro.setModelo(modelo);

        // Salva o carro
        return carroRepository.save(carro);
    }

    // Listar todos os carros
    public List<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    // Excluir um carro
    public boolean excluirCarro(Long id) {
        if (!carroRepository.existsById(id)) {
            throw new IllegalArgumentException("Carro não encontrado com o ID: " + id);
        }
        carroRepository.deleteById(id);
        return true;
    }
}
