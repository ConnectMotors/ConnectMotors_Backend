package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Carro.CarroDTO;
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

    /**
     * Cadastra um carro a partir de um CarroDTO, buscando Marca e Modelo pelo nome.
     * @param carroDTO Dados do carro em formato DTO.
     * @return Entidade Carro salva no banco de dados.
     * @throws IllegalArgumentException Se os dados forem inválidos ou Marca/Modelo não existirem.
     */
    public Carro cadastrarCarro(CarroDTO carroDTO) {
        validarCarroDTO(carroDTO);

        Marca marca = buscarMarcaPorNome(carroDTO.getMarca());
        Modelo modelo = buscarModeloPorNome(carroDTO.getModelo());

        Carro carro = criarEntidadeCarro(carroDTO, marca, modelo);
        return carroRepository.save(carro);
    }

    /**
     * Cadastra um carro diretamente a partir da entidade Carro.
     * @param carro Entidade Carro com Marca e Modelo já associados.
     * @return Entidade Carro salva no banco de dados.
     * @throws IllegalArgumentException Se os dados forem inválidos.
     */
    public Carro cadastrarCarro(Carro carro) {
        validarCarro(carro);
        return carroRepository.save(carro);
    }

    /**
     * Lista todos os carros cadastrados.
     * @return Lista de entidades Carro.
     */
    public List<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    /**
     * Exclui um carro pelo ID.
     * @param id ID do carro a ser excluído.
     * @return true se excluído com sucesso.
     * @throws IllegalArgumentException Se o carro não for encontrado.
     */
    public boolean excluirCarro(Long id) {
        if (!carroRepository.existsById(id)) {
            throw new IllegalArgumentException("Carro não encontrado com o ID: " + id);
        }
        carroRepository.deleteById(id);
        return true;
    }

    // Métodos auxiliares privados

    private void validarCarroDTO(CarroDTO carroDTO) {
        if (carroDTO == null || carroDTO.getMarca() == null || carroDTO.getMarca().isEmpty() ||
            carroDTO.getModelo() == null || carroDTO.getModelo().isEmpty() ||
            carroDTO.getCor() == null || carroDTO.getCor().isEmpty() ||
            carroDTO.getCambio() == null || carroDTO.getCambio().isEmpty() ||
            carroDTO.getCombustivel() == null || carroDTO.getCombustivel().isEmpty() ||
            carroDTO.getCarroceria() == null || carroDTO.getCarroceria().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }
        if (carroDTO.getAno() <= 0) {
            throw new IllegalArgumentException("O ano do carro deve ser maior que zero");
        }
    }

    private void validarCarro(Carro carro) {
        if (carro == null || carro.getMarca() == null || carro.getModelo() == null ||
            carro.getCor() == null || carro.getCor().isEmpty() ||
            carro.getCambio() == null || carro.getCambio().isEmpty() ||
            carro.getCombustivel() == null || carro.getCombustivel().isEmpty() ||
            carro.getCarroceria() == null || carro.getCarroceria().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }
        if (carro.getAno() <= 0) {
            throw new IllegalArgumentException("O ano do carro deve ser maior que zero");
        }
    }

    private Marca buscarMarcaPorNome(String nome) {
        return marcaRepository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada: " + nome));
    }

    private Modelo buscarModeloPorNome(String nome) {
        return modeloRepository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado: " + nome));
    }

    private Carro criarEntidadeCarro(CarroDTO carroDTO, Marca marca, Modelo modelo) {
        Carro carro = new Carro();
        carro.setMarca(marca);
        carro.setModelo(modelo);
        carro.setAno(carroDTO.getAno());
        carro.setCor(carroDTO.getCor());
        carro.setCambio(carroDTO.getCambio());
        carro.setCombustivel(carroDTO.getCombustivel());
        carro.setCarroceria(carroDTO.getCarroceria());
        return carro;
    }
}