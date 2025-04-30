package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Repository.CorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorService {

    @Autowired
    private CorRepository corRepository;

    public List<Cor> listarTodas() {
        return corRepository.findAll();
    }

    public Optional<Cor> buscarPorId(Long id) {
        return corRepository.findById(id);
    }

    public Cor salvar(Cor cor) {
        return corRepository.save(cor);
    }

    public void deletar(Long id) {
        corRepository.deleteById(id);
    }

    
}
