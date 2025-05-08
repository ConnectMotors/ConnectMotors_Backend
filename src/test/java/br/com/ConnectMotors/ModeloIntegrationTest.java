package br.com.ConnectMotors;

import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import br.com.ConnectMotors.Entidade.Model.Modelo.ModeloDTO;
import br.com.ConnectMotors.Entidade.Repository.MarcaRepository;
import br.com.ConnectMotors.Entidade.Repository.ModeloRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ModeloIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    private Marca marca;
    private ModeloDTO modeloDTO;

    @BeforeEach
    void setUp() {
        // Limpar banco de dados de teste
        modeloRepository.deleteAll();
        marcaRepository.deleteAll();

        // Criar marca de teste
        marca = new Marca();
        marca.setNome("Toyota");
        marcaRepository.save(marca);

        // Preparar DTO para testes
        modeloDTO = new ModeloDTO();
        modeloDTO.setNome("Corolla");
        modeloDTO.setMarca("Toyota");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCicloDeVidaCompleto() throws Exception {
        // 1. Cadastrar um novo modelo
        String responseJson = mockMvc.perform(post("/admin/modelos")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Corolla")))
                .andReturn().getResponse().getContentAsString();

        // Extrair ID do modelo criado
        Modelo modeloCriado = objectMapper.readValue(responseJson, Modelo.class);
        Long modeloId = modeloCriado.getId();

        // 2. Verificar se o modelo está na lista de todos os modelos
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].nome", is("Corolla")));

        // 3. Atualizar o modelo
        ModeloDTO modeloAtualizado = new ModeloDTO();
        modeloAtualizado.setNome("Corolla Cross");
        modeloAtualizado.setMarca("Toyota");

        mockMvc.perform(put("/admin/modelos/" + modeloId)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Corolla Cross")));

        // 4. Verificar se a atualização foi salva
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Corolla Cross")));

        // 5. Excluir o modelo
        mockMvc.perform(delete("/admin/modelos/" + modeloId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

        // 6. Verificar se o modelo foi excluído
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(hasItem(hasProperty("id", is(modeloId.intValue()))))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCadastrarModeloComMarcaInexistente() throws Exception {
        // Arrange
        ModeloDTO modeloComMarcaInexistente = new ModeloDTO();
        modeloComMarcaInexistente.setNome("Civic");
        modeloComMarcaInexistente.setMarca("Honda"); // Marca que não existe no banco

        // Act & Assert
        mockMvc.perform(post("/admin/modelos")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloComMarcaInexistente)))
                .andExpect(status().is4xxClientError()); // Deve falhar com erro 400 ou 404
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditarModeloInexistente() throws Exception {
        // Arrange - usar um ID que certamente não existe
        Long idInexistente = 99999L;

        // Act & Assert
        mockMvc.perform(put("/admin/modelos/" + idInexistente)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modeloDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testExcluirModeloInexistente() throws Exception {
        // Arrange - usar um ID que certamente não existe
        Long idInexistente = 99999L;

        // Act & Assert
        mockMvc.perform(delete("/admin/modelos/" + idInexistente)
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testAcessoSemAutenticacao() throws Exception {
        // Tentar acessar o endpoint sem autenticação
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(roles = "USER") // Usuário com role insuficiente
    void testAcessoComRoleInsuficiente() throws Exception {
        // Tentar acessar com usuário não-admin
        mockMvc.perform(get("/admin/modelos"))
                .andExpect(status().isForbidden());
    }
}