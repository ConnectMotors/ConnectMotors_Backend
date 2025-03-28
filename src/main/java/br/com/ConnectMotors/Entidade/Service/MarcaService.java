package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public Marca cadastrarMarca(Marca marca) {
        if (marca.getNome() == null || marca.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da marca não pode ser vazio");
        }
        return marcaRepository.save(marca);
    }

    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    public Marca editarMarca(Long id, Marca marca) {
        Optional<Marca> marcaExistente = marcaRepository.findById(id);
        if (marcaExistente.isPresent()) {
            Marca marcaAtualizada = marcaExistente.get();
            marcaAtualizada.setNome(marca.getNome()); // Assumindo que "nome" é o único atributo editável
            return marcaRepository.save(marcaAtualizada);
        }
        return null;
    }

    public boolean excluirMarca(Long id) {
        Optional<Marca> marcaExistente = marcaRepository.findById(id);
        if (marcaExistente.isPresent()) {
            marcaRepository.delete(marcaExistente.get());
            return true;
        }
        return false;
    }

    public Optional<Marca> buscarPorNome(String nome) {
        return marcaRepository.findByNome(nome);
    }

}
