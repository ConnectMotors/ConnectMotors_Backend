package br.com.ConnectMotors.Entidade.Service;

import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.Moto.MotoDTO;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Repository.MotoRepository;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
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

    /**
     * Cadastra uma moto a partir de um MotoDTO, buscando Marca e Modelo pelo nome.
     * @param motoDTO Dados da moto em formato DTO.
     * @return Entidade Moto salva no banco de dados.
     */
    public Moto cadastrarMoto(MotoDTO motoDTO) {
        validarMotoDTO(motoDTO);

        Marca marca = buscarMarcaPorNome(motoDTO.getMarca());
        Modelo modelo = buscarModeloPorNome(motoDTO.getModelo());

        Moto moto = criarEntidadeMoto(motoDTO, marca, modelo);
        return motoRepository.save(moto);
    }

    /**
     * Edita uma moto existente no banco de dados.
     * @param id ID da moto a ser editada.
     * @param motoDTO Dados atualizados da moto.
     * @return Entidade Moto atualizada.
     */
    public Moto editarMoto(Long id, MotoDTO motoDTO) {
        validarMotoDTO(motoDTO);

        Moto motoExistente = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada com o ID: " + id));

        Marca marca = buscarMarcaPorNome(motoDTO.getMarca());
        Modelo modelo = buscarModeloPorNome(motoDTO.getModelo());

        motoExistente.setMarca(marca);
        motoExistente.setModelo(modelo);
        motoExistente.setCor(motoDTO.getCor());
        motoExistente.setAno(motoDTO.getAno());
        motoExistente.setFreio(motoDTO.getFreio());
        motoExistente.setPartida(motoDTO.getPartida());
        motoExistente.setCilindrada(motoDTO.getCilindrada());
        motoExistente.setCombustivel(motoDTO.getCombustivel());

        return motoRepository.save(motoExistente);
    }

    /**
     * Lista todas as motos cadastradas.
     * @return Lista de entidades Moto.
     */
    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    /**
     * Exclui uma moto pelo ID.
     * @param id ID da moto a ser excluída.
     * @return true se excluída com sucesso.
     */
    public boolean excluirMoto(Long id) {
        if (!motoRepository.existsById(id)) {
            throw new IllegalArgumentException("Moto não encontrada com o ID: " + id);
        }
        motoRepository.deleteById(id);
        return true;
    }

    // Métodos auxiliares privados

    private void validarMotoDTO(MotoDTO motoDTO) {
        if (motoDTO == null || motoDTO.getMarca() == null || motoDTO.getMarca().isEmpty() ||
            motoDTO.getModelo() == null || motoDTO.getModelo().isEmpty() ||
            motoDTO.getCor() == null || motoDTO.getCor().isEmpty() ||
            motoDTO.getFreio() == null || motoDTO.getFreio().isEmpty() ||
            motoDTO.getPartida() == null || motoDTO.getPartida().isEmpty() ||
            motoDTO.getCombustivel() == null || motoDTO.getCombustivel().isEmpty()) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos");
        }
        if (motoDTO.getAno() <= 0) {
            throw new IllegalArgumentException("O ano da moto deve ser maior que zero");
        }
        if (motoDTO.getCilindrada() <= 0) {
            throw new IllegalArgumentException("A cilindrada da moto deve ser maior que zero");
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

    private Moto criarEntidadeMoto(MotoDTO motoDTO, Marca marca, Modelo modelo) {
        Moto moto = new Moto();
        moto.setMarca(marca);
        moto.setModelo(modelo);
        moto.setCor(motoDTO.getCor());
        moto.setAno(motoDTO.getAno());
        moto.setFreio(motoDTO.getFreio());
        moto.setPartida(motoDTO.getPartida());
        moto.setCilindrada(motoDTO.getCilindrada());
        moto.setCombustivel(motoDTO.getCombustivel());
        return moto;
    }
}