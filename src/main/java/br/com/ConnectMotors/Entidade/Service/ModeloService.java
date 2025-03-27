package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    // Cadastrar um novo modelo
    public Modelo cadastrarModelo(Modelo modelo) {
        if (modelo.getNome() == null || modelo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do modelo não pode ser vazio");
        }
        if (modelo.getMarca() == null || modelo.getMarca().getId() == null) {
            throw new IllegalArgumentException("A marca é obrigatória para cadastrar um modelo");
        }

        Marca marca = marcaRepository.findById(modelo.getMarca().getId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + modelo.getMarca().getId()));
        
        modelo.setMarca(marca);
        return modeloRepository.save(modelo);
    }

    // Listar todos os modelos
    public List<Modelo> listarModelos() {
        return modeloRepository.findAll();
    }

    // Listar modelos por marca
    public List<Modelo> listarModelosPorMarca(Long marcaId) {
        marcaRepository.findById(marcaId)
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + marcaId));
        
        return modeloRepository.findByMarcaId(marcaId);
    }

    // Editar um modelo
    public Modelo editarModelo(Long id, Modelo modelo) {
        Optional<Modelo> modeloExistente = modeloRepository.findById(id);
        if (modeloExistente.isPresent()) {
            Modelo modeloAtualizado = modeloExistente.get();
            modeloAtualizado.setNome(modelo.getNome());
            modeloAtualizado.setMarca(modelo.getMarca()); // Atualiza a marca
            return modeloRepository.save(modeloAtualizado);
        }
        return null; // Retorna null caso o modelo não seja encontrado
    }

    // Excluir um modelo
    public boolean excluirModelo(Long id) {
        Optional<Modelo> modeloExistente = modeloRepository.findById(id);
        if (modeloExistente.isPresent()) {
            modeloRepository.delete(modeloExistente.get());
            return true; // Retorna true se a exclusão foi bem-sucedida
        }
        return false; // Retorna false se o modelo não for encontrado
    }
}
