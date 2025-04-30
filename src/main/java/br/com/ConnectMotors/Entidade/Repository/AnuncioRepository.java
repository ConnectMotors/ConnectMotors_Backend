package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Anuncio.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.carro c " +
            "WHERE (:marcaId IS NULL OR c.marca.id = :marcaId) " +
            "AND (:modeloId IS NULL OR c.modelo.id = :modeloId) " +
            "AND (:corId IS NULL OR c.cor.id = :corId) " +
            "AND (:cambio IS NULL OR c.cambio = :cambio) " +
            "AND (:combustivel IS NULL OR c.combustivel = :combustivel) " +
            "AND (:carroceria IS NULL OR c.carroceria = :carroceria) " +
            "AND (:precoMin IS NULL OR a.preco >= :precoMin) " +
            "AND (:precoMax IS NULL OR a.preco <= :precoMax) " +
            "AND (:quilometragemMax IS NULL OR a.quilometragem <= :quilometragemMax)")
    List<Anuncio> findByFiltros(
            @Param("marcaId") Long marcaId,
            @Param("modeloId") Long modeloId,
            @Param("corId") Long corId,
            @Param("cambio") String cambio,
            @Param("combustivel") String combustivel,
            @Param("carroceria") String carroceria,
            @Param("precoMin") Double precoMin,
            @Param("precoMax") Double precoMax,
            @Param("quilometragemMax") String quilometragemMax
    );
}