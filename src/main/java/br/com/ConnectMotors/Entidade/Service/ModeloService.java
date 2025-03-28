package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Modelo.ModeloDTO;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
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

    public Modelo cadastrarModelo(ModeloDTO modeloDTO) {
        Marca marca = marcaRepository.findByNome(modeloDTO.getMarca())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada: " + modeloDTO.getMarca()));

        Modelo modelo = new Modelo();
        modelo.setNome(modeloDTO.getNome());
        modelo.setMarca(marca);

        return modeloRepository.save(modelo);
    }

    public List<Modelo> listarModelos() {
        return modeloRepository.findAll();
    }

    public Modelo editarModelo(Long id, ModeloDTO modeloDTO) {
        Optional<Modelo> modeloExistente = modeloRepository.findById(id);
        if (modeloExistente.isEmpty()) {
            return null;
        }

        Marca marca = marcaRepository.findByNome(modeloDTO.getMarca())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada: " + modeloDTO.getMarca()));

        Modelo modelo = modeloExistente.get();
        modelo.setNome(modeloDTO.getNome());
        modelo.setMarca(marca);

        return modeloRepository.save(modelo);
    }

    public boolean excluirModelo(Long id) {
        if (!modeloRepository.existsById(id)) {
            return false;
        }
        modeloRepository.deleteById(id);
        return true;
    }
}