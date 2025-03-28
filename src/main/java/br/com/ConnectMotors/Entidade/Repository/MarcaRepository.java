package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNome(String nome);
}