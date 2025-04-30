package br.com.ConnectMotors.Entidade.Repository;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    // Filtrar carros por carroceria
    List<Carro> findByCarroceria(String carroceria);

    // Filtrar carros por marca
    List<Carro> findByMarcaId(Long marcaId);

    // Filtrar carros por modelo
    List<Carro> findByModeloId(Long modeloId);

    // Filtrar carros por cor
    List<Carro> findByCorId(Long corId);

    // Filtrar carros por câmbio
    List<Carro> findByCambio(String cambio);

    // Filtrar carros por combustível
    List<Carro> findByCombustivel(String combustivel);

    // Filtro avançado com múltiplos critérios
    @Query("SELECT c FROM Carro c WHERE " +
            "(:marcaId IS NULL OR c.marca.id = :marcaId) AND " +
            "(:modeloId IS NULL OR c.modelo.id = :modeloId) AND " +
            "(:corId IS NULL OR c.cor.id = :corId) AND " +
            "(:cambio IS NULL OR c.cambio = :cambio) AND " +
            "(:combustivel IS NULL OR c.combustivel = :combustivel) AND " +
            "(:carroceria IS NULL OR c.carroceria = :carroceria)")
    List<Carro> findByFiltros(
            @Param("marcaId") Long marcaId,
            @Param("modeloId") Long modeloId,
            @Param("corId") Long corId,
            @Param("cambio") String cambio,
            @Param("combustivel") String combustivel,
            @Param("carroceria") String carroceria
    );

    // Buscar uma marca associada a um carro
    @Query("SELECT c.marca FROM Carro c WHERE c.marca.id = :marcaId")
    Optional<Marca> findMarcaById(@Param("marcaId") Long marcaId);

    // Buscar um modelo associado a um carro
    @Query("SELECT c.modelo FROM Carro c WHERE c.modelo.id = :modeloId")
    Optional<Modelo> findModeloById(@Param("modeloId") Long modeloId);

    // Buscar uma cor associada a um carro
    @Query("SELECT c.cor FROM Carro c WHERE c.cor.id = :corId")
    Optional<Cor> findCorById(@Param("corId") Long corId);
}