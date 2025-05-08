package br.com.ConnectMotors;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Modelo.ModeloDTO;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import br.com.ConnectMotors.Entidade.Service.ModeloService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModeloServiceTest {

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private ModeloService modeloService;

    private ModeloDTO modeloDTO;
    private Modelo modelo;
    private Marca marca;

    @BeforeEach
    void setUp() {
        // Configurando objetos para teste
        marca = new Marca();
        marca.setId(1L);
        marca.setNome("Toyota");

        modelo = new Modelo();
        modelo.setId(1L);
        modelo.setNome("Corolla");
        modelo.setMarca(marca);

        modeloDTO = new ModeloDTO();
        modeloDTO.setNome("Corolla");
        modeloDTO.setMarca("Toyota");
    }

    @Test
    void testCadastrarModelo() {
        // Arrange
        when(marcaRepository.findByNome("Toyota")).thenReturn(Optional.of(marca));
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);

        // Act
        Modelo resultado = modeloService.cadastrarModelo(modeloDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Corolla", resultado.getNome());
        assertEquals("Toyota", resultado.getMarca().getNome());
        verify(marcaRepository, times(1)).findByNome("Toyota");
        verify(modeloRepository, times(1)).save(any(Modelo.class));
    }

    @Test
    void testCadastrarModeloMarcaNaoEncontrada() {
        // Arrange
        when(marcaRepository.findByNome("Toyota")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> modeloService.cadastrarModelo(modeloDTO)
        );
        
        assertEquals("Marca não encontrada: Toyota", exception.getMessage());
        verify(marcaRepository, times(1)).findByNome("Toyota");
        verify(modeloRepository, times(0)).save(any(Modelo.class));
    }

    @Test
    void testListarModelos() {
        // Arrange
        List<Modelo> modelos = Arrays.asList(modelo);
        when(modeloRepository.findAll()).thenReturn(modelos);

        // Act
        List<Modelo> resultado = modeloService.listarModelos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Corolla", resultado.get(0).getNome());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    void testEditarModelo() {
        // Arrange
        when(modeloRepository.findById(1L)).thenReturn(Optional.of(modelo));
        when(marcaRepository.findByNome("Toyota")).thenReturn(Optional.of(marca));
        
        ModeloDTO novoModeloDTO = new ModeloDTO();
        novoModeloDTO.setNome("Corolla Cross");
        novoModeloDTO.setMarca("Toyota");
        
        Modelo modeloEditado = new Modelo();
        modeloEditado.setId(1L);
        modeloEditado.setNome("Corolla Cross");
        modeloEditado.setMarca(marca);
        
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modeloEditado);

        // Act
        Modelo resultado = modeloService.editarModelo(1L, novoModeloDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Corolla Cross", resultado.getNome());
        verify(modeloRepository, times(1)).findById(1L);
        verify(marcaRepository, times(1)).findByNome("Toyota");
        verify(modeloRepository, times(1)).save(any(Modelo.class));
    }

    @Test
    void testEditarModeloNaoEncontrado() {
        // Arrange
        when(modeloRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Modelo resultado = modeloService.editarModelo(1L, modeloDTO);

        // Assert
        assertNull(resultado);
        verify(modeloRepository, times(1)).findById(1L);
        verify(marcaRepository, times(0)).findByNome(anyString());
        verify(modeloRepository, times(0)).save(any(Modelo.class));
    }

    @Test
    void testExcluirModelo() {
        // Arrange
        when(modeloRepository.existsById(1L)).thenReturn(true);
        doNothing().when(modeloRepository).deleteById(1L);

        // Act
        boolean resultado = modeloService.excluirModelo(1L);

        // Assert
        assertTrue(resultado);
        verify(modeloRepository, times(1)).existsById(1L);
        verify(modeloRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExcluirModeloNaoEncontrado() {
        // Arrange
        when(modeloRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean resultado = modeloService.excluirModelo(1L);

        // Assert
        assertFalse(resultado);
        verify(modeloRepository, times(1)).existsById(1L);
        verify(modeloRepository, times(0)).deleteById(anyLong());
    }
}