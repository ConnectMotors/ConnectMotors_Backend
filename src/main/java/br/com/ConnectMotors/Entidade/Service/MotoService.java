package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.Moto.MotoDTO;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Repository.MotoRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Repository.CorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private CorRepository corRepository;

    public Moto cadastrarMoto(MotoDTO motoDTO) {
        validarMotoDTO(motoDTO);

        Marca marca = marcaRepository.findById(motoDTO.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + motoDTO.getMarcaId()));
        Modelo modelo = modeloRepository.findById(motoDTO.getModeloId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + motoDTO.getModeloId()));
        Cor cor = corRepository.findById(motoDTO.getCorId())
                .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + motoDTO.getCorId()));

        Moto moto = new Moto();
        moto.setMarca(marca);
        moto.setModelo(modelo);
        moto.setCor(cor);
        moto.setAnoFabricacao(motoDTO.getAnoFabricacao());
        moto.setAnoModelo(motoDTO.getAnoModelo());
        moto.setVersao(motoDTO.getVersao());
        moto.setFreio(motoDTO.getFreio());
        moto.setPartida(motoDTO.getPartida());
        moto.setCilindrada(motoDTO.getCilindrada());
        moto.setCombustivel(motoDTO.getCombustivel());

        return motoRepository.save(moto);
    }

    public Moto editarMoto(Long id, MotoDTO motoDTO) {
        validarMotoDTO(motoDTO);

        Moto motoExistente = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada com o ID: " + id));

        Marca marca = marcaRepository.findById(motoDTO.getMarcaId())
                .orElseThrow(() -> new IllegalArgumentException("Marca não encontrada com o ID: " + motoDTO.getMarcaId()));
        Modelo modelo = modeloRepository.findById(motoDTO.getModeloId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo não encontrado com o ID: " + motoDTO.getModeloId()));
        Cor cor = corRepository.findById(motoDTO.getCorId())
                .orElseThrow(() -> new IllegalArgumentException("Cor não encontrada com o ID: " + motoDTO.getCorId()));

        motoExistente.setMarca(marca);
        motoExistente.setModelo(modelo);
        motoExistente.setCor(cor);
        motoExistente.setAnoFabricacao(motoDTO.getAnoFabricacao());
        motoExistente.setAnoModelo(motoDTO.getAnoModelo());
        motoExistente.setVersao(motoDTO.getVersao());
        motoExistente.setFreio(motoDTO.getFreio());
        motoExistente.setPartida(motoDTO.getPartida());
        motoExistente.setCilindrada(motoDTO.getCilindrada());
        motoExistente.setCombustivel(motoDTO.getCombustivel());

        return motoRepository.save(motoExistente);
    }

    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    public boolean excluirMoto(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new IllegalArgumentException("Moto não encontrada com o ID: " + id);
        }
        motoRepository.deleteById(id);
        return true;
    }

    private void validarMotoDTO(MotoDTO motoDTO) {
        if (motoDTO == null || motoDTO.getMarcaId() == null || motoDTO.getModeloId() == null ||
            motoDTO.getCorId() == null || motoDTO.getAnoFabricacao() == null ||
            motoDTO.getAnoModelo() == null || motoDTO.getFreio() == null || motoDTO.getFreio().isEmpty() ||
            motoDTO.getPartida() == null || motoDTO.getPartida().isEmpty() ||
            motoDTO.getCilindrada() == null || motoDTO.getCilindrada().isEmpty() ||
            motoDTO.getCombustivel() == null || motoDTO.getCombustivel().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }
        if (motoDTO.getAnoFabricacao() < 1900 || motoDTO.getAnoFabricacao() > 2026) {
            throw new IllegalArgumentException("O ano de fabricação deve estar entre 1900 e 2026");
        }
        if (motoDTO.getAnoModelo() < 1900 || motoDTO.getAnoModelo() > 2026) {
            throw new IllegalArgumentException("O ano do modelo deve estar entre 1900 e 2026");
        }
    }
}