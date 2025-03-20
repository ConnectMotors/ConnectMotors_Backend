package br.com.ConnectMotors.Entidade.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ConnectMotors.Entidade.Model.Car.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}