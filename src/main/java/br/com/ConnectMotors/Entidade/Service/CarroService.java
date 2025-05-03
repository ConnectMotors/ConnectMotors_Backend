package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Carro.CarroDTO;
import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Repository.CarroRepository;
import br.com.ConnectMotors.Entidade.Repository.CorRepository;
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

    @Autowired
    private CorRepository corRepository;

    // ============================
    // Métodos Públicos
    // ============================

    /**
     * Cadastra um carro a partir de um CarroDTO.
     * @param carroDTO Dados do carro em formato DTO.
     * @return Entidade Carro salva no banco de dados.
     */
    public Carro cadastrarCarro(CarroDTO carroDTO) {
        validarCarroDTO(carroDTO);

        Marca marca = buscarMarcaPorId(carroDTO.getMarcaId());
        Modelo modelo = buscarModeloPorId(carroDTO.getModeloId());
        Cor cor = buscarCorPorId(carroDTO.getCorId());

        Carro carro = criarEntidadeCarro(carroDTO, marca, modelo, cor);
        return carroRepository.save(carro);
    }

    /**
     * Edita um carro existente no banco de dados.
     * @param id ID do carro a ser editado.
     * @param carroDTO Dados atualizados do carro.
     * @return Entidade Carro atualizada.
     */
    public Carro editarCarro(Long id, CarroDTO carroDTO) {
        validarCarroDTO(carroDTO);

        Carro carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado com o ID: " + id));

        Marca marca = buscarMarcaPorId(carroDTO.getMarcaId());
        Modelo modelo = buscarModeloPorId(carroDTO.getModeloId());
        Cor cor = buscarCorPorId(carroDTO.getCorId());

        atualizarEntidadeCarro(carroExistente, carroDTO, marca, modelo, cor);

        return carroRepository.save(carroExistente);
    }

    /**
     * Lista todos os carros cadastrados.
     * @return Lista de entidades Carro.
     */
    public List<Carro> listarCarros() {
        return carroRepository.findAll();
    }

    /**
     * Lista carros filtrados por marca.
     * @param marcaId ID da marca.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorMarca(Long marcaId) {
        return carroRepository.findByMarcaId(marcaId);
    }

    /**
     * Lista carros filtrados por modelo.
     * @param modeloId ID do modelo.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorModelo(Long modeloId) {
        return carroRepository.findByModeloId(modeloId);
    }

    /**
     * Lista carros filtrados por cor.
     * @param corId ID da cor.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorCor(Long corId) {
        return carroRepository.findByCorId(corId);
    }

    /**
     * Lista carros filtrados por câmbio.
     * @param cambio Tipo de câmbio.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorCambio(String cambio) {
        if (cambio == null || cambio.isEmpty()) {
            throw new IllegalArgumentException("O tipo de câmbio não pode ser nulo ou vazio.");
        }
        return carroRepository.findByCambio(cambio);
    }

    /**
     * Lista carros filtrados por combustível.
     * @param combustivel Tipo de combustível.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorCombustivel(String combustivel) {
        if (combustivel == null || combustivel.isEmpty()) {
            throw new IllegalArgumentException("O tipo de combustível não pode ser nulo ou vazio.");
        }
        return carroRepository.findByCombustivel(combustivel);
    }

    /**
     * Lista carros filtrados por carroceria.
     * @param carroceria Tipo de carroceria.
     * @return Lista de carros filtrados.
     */
    public List<Carro> listarCarrosPorCarroceria(String carroceria) {
        if (carroceria == null || carroceria.isEmpty()) {
            throw new IllegalArgumentException("O tipo de carroceria não pode ser nulo ou vazio.");
        }
        return carroRepository.findByCarroceria(carroceria);
    }

    /**
     * Exclui um carro pelo ID.
     * @param id ID do carro a ser excluído.
     * @return true se excluído com sucesso.
     */
    public boolean excluirCarro(Long id) {
        if (!carroRepository.existsById(id)) {
            throw new IllegalArgumentException("Carro não encontrado com o ID: " + id);
        }
        carroRepository.deleteById(id);
        return true;
    }

    // ============================
    // Métodos Auxiliares Privados
    // ============================

    public List<Carro> filtrarCarros(Long marcaId, Long modeloId, Long corId, String cambio, String combustivel, String carroceria) {
        // Validação de parâmetros opcionais
        if (cambio != null && cambio.isEmpty()) {
            throw new IllegalArgumentException("O tipo de câmbio não pode ser vazio.");
        }
        if (combustivel != null && combustivel.isEmpty()) {
            throw new IllegalArgumentException("O tipo de combustível não pode ser vazio.");
        }
        if (carroceria != null && carroceria.isEmpty()) {
            throw new IllegalArgumentException("O tipo de carroceria não pode ser vazio.");
        }
    
        // Chama o repositório para buscar os carros com base nos filtros
        return carroRepository.findByFiltros(marcaId, modeloId, corId, cambio, combustivel, carroceria);
    }
private void validarCarroDTO(CarroDTO carroDTO) {
    if (carroDTO == null || carroDTO.getMarcaId() == null ||
        carroDTO.getModeloId() == null || carroDTO.getCorId() == null ||
        carroDTO.getCambio() == null || // Removido isEmpty()
        carroDTO.getCombustivel() == null || // Removido isEmpty()
        carroDTO.getCarroceria() == null || // Removido isEmpty()
        carroDTO.getMotor() == null || carroDTO.getMotor().isEmpty() ||
        carroDTO.getVersao() == null || carroDTO.getVersao().isEmpty()) {
        throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
    }
    if (carroDTO.getAnoFabricacao() < 1886) {
        throw new IllegalArgumentException("O ano de fabricação deve ser maior ou igual a 1886");
    }
    if (carroDTO.getAnoModelo() < 1886) {
        throw new IllegalArgumentException("O ano do modelo deve ser maior ou igual a 1886");
    }
}

    private Marca buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + id));
    }

    private Modelo buscarModeloPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + id));
    }

    private Cor buscarCorPorId(Long id) {
        return corRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + id));
    }

    private Carro criarEntidadeCarro(CarroDTO carroDTO, Marca marca, Modelo modelo, Cor cor) {
        Carro carro = new Carro();
        carro.setMarca(marca);
        carro.setModelo(modelo);
        carro.setCor(cor);
        carro.setAnoFabricacao(carroDTO.getAnoFabricacao());
        carro.setAnoModelo(carroDTO.getAnoModelo());
        carro.setCambio(carroDTO.getCambio());
        carro.setCombustivel(carroDTO.getCombustivel());
        carro.setCarroceria(carroDTO.getCarroceria());
        carro.setMotor(carroDTO.getMotor());
        carro.setVersao(carroDTO.getVersao());
        return carro;
    }

    private void atualizarEntidadeCarro(Carro carro, CarroDTO carroDTO, Marca marca, Modelo modelo, Cor cor) {
        carro.setMarca(marca);
        carro.setModelo(modelo);
        carro.setCor(cor);
        carro.setAnoFabricacao(carroDTO.getAnoFabricacao());
        carro.setAnoModelo(carroDTO.getAnoModelo());
        carro.setCambio(carroDTO.getCambio());
        carro.setCombustivel(carroDTO.getCombustivel());
        carro.setCarroceria(carroDTO.getCarroceria());
        carro.setMotor(carroDTO.getMotor());
        carro.setVersao(carroDTO.getVersao());
    }
}