package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Enums.Cambio;
import br.com.ConnectMotors.Entidade.Enums.Carroceria;
import br.com.ConnectMotors.Entidade.Enums.Combustivel;
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
            "AND (:anoFabricacao IS NULL OR c.anoFabricacao = :anoFabricacao) " +
            "AND (:anoModelo IS NULL OR c.anoModelo = :anoModelo) " +
            "AND (:motor IS NULL OR c.motor = :motor) " +
            "AND (:versao IS NULL OR c.versao = :versao) " +
            "AND (:precoMin IS NULL OR a.preco >= :precoMin) " +
            "AND (:precoMax IS NULL OR a.preco <= :precoMax) " +
            "AND (:quilometragemMax IS NULL OR a.quilometragem <= :quilometragemMax)")
    List<Anuncio> findByFiltrosCarro(
            @Param("marcaId") Long marcaId,
            @Param("modeloId") Long modeloId,
            @Param("corId") Long corId,
            @Param("cambio") Cambio cambio,
            @Param("combustivel") Combustivel combustivel,
            @Param("carroceria") Carroceria carroceria,
            @Param("anoFabricacao") Integer anoFabricacao,
            @Param("anoModelo") Integer anoModelo,
            @Param("motor") String motor,
            @Param("versao") String versao,
            @Param("precoMin") Double precoMin,
            @Param("precoMax") Double precoMax,
            @Param("quilometragemMax") String quilometragemMax
    );

    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.moto m " +
            "WHERE (:marcaId IS NULL OR m.marca.id = :marcaId) " +
            "AND (:modeloId IS NULL OR m.modelo.id = :modeloId) " +
            "AND (:corId IS NULL OR m.cor.id = :corId) " +
            "AND (:freio IS NULL OR m.freio = :freio) " +
            "AND (:partida IS NULL OR m.partida = :partida) " +
            "AND (:cilindrada IS NULL OR m.cilindrada = :cilindrada) " +
            "AND (:combustivel IS NULL OR m.combustivel = :combustivel) " +
            "AND (:anoFabricacao IS NULL OR m.anoFabricacao = :anoFabricacao) " +
            "AND (:anoModelo IS NULL OR m.anoModelo = :anoModelo) " +
            "AND (:versao IS NULL OR m.versao = :versao) " +
            "AND (:precoMin IS NULL OR a.preco >= :precoMin) " +
            "AND (:precoMax IS NULL OR a.preco <= :precoMax) " +
            "AND (:quilometragemMax IS NULL OR a.quilometragem <= :quilometragemMax)")
    List<Anuncio> findByFiltrosMoto(
            @Param("marcaId") Long marcaId,
            @Param("modeloId") Long modeloId,
            @Param("corId") Long corId,
            @Param("freio") String freio,
            @Param("partida") String partida,
            @Param("cilindrada") String cilindrada,
            @Param("combustivel") String combustivel,
            @Param("anoFabricacao") Integer anoFabricacao,
            @Param("anoModelo") Integer anoModelo,
            @Param("versao") String versao,
            @Param("precoMin") Double precoMin,
            @Param("precoMax") Double precoMax,
            @Param("quilometragemMax") String quilometragemMax
    );
}