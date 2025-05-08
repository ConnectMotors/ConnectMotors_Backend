package br.com.ConnectMotors;

import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ModeloRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModeloRepository modeloRepository;

    private Marca marca;
    private Modelo modelo;

    @BeforeEach
    void setUp() {
        // Configurando objetos para teste
        marca = new Marca();
        marca.setNome("Toyota");
        
        // Persistir a marca primeiro
        marca = entityManager.persist(marca);
        entityManager.flush();
        
        modelo = new Modelo();
        modelo.setNome("Corolla");
        modelo.setMarca(marca);
        
        // Persistir o modelo
        modelo = entityManager.persist(modelo);
        entityManager.flush();
    }

    @Test
    void testFindByMarcaId() {
        // Act
        List<Modelo> modelos = modeloRepository.findByMarcaId(marca.getId());

        // Assert
        assertFalse(modelos.isEmpty());
        assertEquals(1, modelos.size());
        assertEquals("Corolla", modelos.get(0).getNome());
        assertEquals("Toyota", modelos.get(0).getMarca().getNome());
    }

    @Test
    void testFindByNome() {
        // Act
        Optional<Modelo> encontrado = modeloRepository.findByNome("Corolla");

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Corolla", encontrado.get().getNome());
        assertEquals("Toyota", encontrado.get().getMarca().getNome());
    }

    @Test
    void testFindByNomeInexistente() {
        // Act
        Optional<Modelo> encontrado = modeloRepository.findByNome("Modelo Inexistente");

        // Assert
        assertFalse(encontrado.isPresent());
    }

    @Test
    void testSaveAndFindById() {
        // Arrange
        Modelo novoModelo = new Modelo();
        novoModelo.setNome("Hilux");
        novoModelo.setMarca(marca);
        
        // Act
        Modelo salvo = modeloRepository.save(novoModelo);
        Optional<Modelo> encontrado = modeloRepository.findById(salvo.getId());
        
        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Hilux", encontrado.get().getNome());
        assertEquals("Toyota", encontrado.get().getMarca().getNome());
    }

    @Test
    void testDeleteById() {
        // Act
        modeloRepository.deleteById(modelo.getId());
        Optional<Modelo> encontrado = modeloRepository.findById(modelo.getId());
        
        // Assert
        assertFalse(encontrado.isPresent());
    }
}