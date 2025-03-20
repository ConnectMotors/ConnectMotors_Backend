package br.com.ConnectMotors.Entidade.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ConnectMotors.Entidade.Model.User.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
