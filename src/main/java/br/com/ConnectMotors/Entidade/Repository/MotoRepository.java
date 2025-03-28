package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
}