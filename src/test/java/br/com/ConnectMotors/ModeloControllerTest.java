package br.com.ConnectMotors;

import br.com.ConnectMotors.Entidade.Controller.ModeloController;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Modelo.ModeloDTO;
import br.com.ConnectMotors.Entidade.Service.ModeloService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ModeloController.class)
public class ModeloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModeloService modeloService;

    @Autowired
    private ObjectMapper objectMapper;

    private Modelo modelo;
    private ModeloDTO modeloDTO;
    private Marca marca;

    @BeforeEach
    void setUp() {
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
    @WithMockUser(roles = "ADMIN") // Simulando um usuário autenticado com role ADMIN
    void testCadastrarModelo() throws Exception {
        // Arrange
        when(modeloService.cadastrarModelo(any(ModeloDTO.class))).thenReturn(modelo);

        // Act & Assert
        mockMvc.perform(post("/admin/modelos")
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Necessário para requisições POST com Spring Security
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Corolla")));

        verify(modeloService, times(1)).cadastrarModelo(any(ModeloDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCadastrarModeloComDadosInvalidos() throws Exception {
        // Arrange
        ModeloDTO modeloInvalido = new ModeloDTO();
        modeloInvalido.setNome("");  // Nome vazio

        // Act & Assert
        mockMvc.perform(post("/admin/modelos")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloInvalido)))
                .andExpect(status().isBadRequest());

        verify(modeloService, times(0)).cadastrarModelo(any(ModeloDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testListarModelos() throws Exception {
        // Arrange
        List<Modelo> modelos = Arrays.asList(modelo);
        when(modeloService.listarModelos()).thenReturn(modelos);

        // Act & Assert
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nome", is("Corolla")));

        verify(modeloService, times(1)).listarModelos();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditarModelo() throws Exception {
        // Arrange
        ModeloDTO modeloAtualizado = new ModeloDTO();
        modeloAtualizado.setNome("Corolla Cross");
        modeloAtualizado.setMarca("Toyota");

        Modelo modeloEditado = new Modelo();
        modeloEditado.setId(1L);
        modeloEditado.setNome("Corolla Cross");
        modeloEditado.setMarca(marca);

        when(modeloService.editarModelo(eq(1L), any(ModeloDTO.class))).thenReturn(modeloEditado);

        // Act & Assert
        mockMvc.perform(put("/admin/modelos/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Corolla Cross")));

        verify(modeloService, times(1)).editarModelo(eq(1L), any(ModeloDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditarModeloNaoEncontrado() throws Exception {
        // Arrange
        when(modeloService.editarModelo(eq(999L), any(ModeloDTO.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/admin/modelos/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloDTO)))
                .andExpect(status().isNotFound());

        verify(modeloService, times(1)).editarModelo(eq(999L), any(ModeloDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testExcluirModelo() throws Exception {
        // Arrange
        when(modeloService.excluirModelo(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/admin/modelos/1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        verify(modeloService, times(1)).excluirModelo(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testExcluirModeloNaoEncontrado() throws Exception {
        // Arrange
        when(modeloService.excluirModelo(999L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/admin/modelos/999")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());

        verify(modeloService, times(1)).excluirModelo(999L);
    }
    
    @Test
    void testAcessoNegadoSemAutenticacao() throws Exception {
        // Tentar acessar endpoint sem autenticação
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isUnauthorized());
    }
}