package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Recomendaçao.Recomendaçao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecomendaçaoRepository extends JpaRepository<Recomendaçao, Long> {
}