package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByMarcaId(Long marcaId);
    Optional<Modelo> findByNome(String nome);
}