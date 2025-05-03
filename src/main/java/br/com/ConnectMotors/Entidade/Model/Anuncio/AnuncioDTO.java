package br.com.ConnectMotors.Entidade.Model.Anuncio;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AnuncioDTO {

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 12345-678.")
    private String cep;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private Double preco;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres.")
    private String descricao;

    @NotBlank(message = "A quilometragem é obrigatória.")
    @Pattern(regexp = "\\d+", message = "A quilometragem deve ser um número inteiro positivo.")
    private String quilometragem;

    // Atributos do carro
    @NotNull(message = "O campo 'marcaId' é obrigatório.")
    private Long marcaId;

    @NotNull(message = "O campo 'modeloId' é obrigatório.")
    private Long modeloId;

    @NotNull(message = "O campo 'corId' é obrigatório.")
    private Long corId;

    @NotNull(message = "O campo 'ano de fabricação' é obrigatório.")
    @Min(value = 1900, message = "O ano de fabricação deve ser maior ou igual a 1900.")
    @Max(value = 2026, message = "O ano de fabricação não pode ser maior que 2026.")
    private Integer anoFabricacao;

    @NotNull(message = "O campo 'ano do modelo' é obrigatório.")
    @Min(value = 1900, message = "O ano do modelo deve ser maior ou igual a 1900.")
    @Max(value = 2026, message = "O ano do modelo não pode ser maior que 2026.")
    private Integer anoModelo;

    @NotBlank(message = "O campo 'câmbio' é obrigatório.")
    private String cambio;

    @NotBlank(message = "O campo 'combustível' é obrigatório.")
    private String combustivel;

    @NotBlank(message = "O campo 'carroceria' é obrigatório.")
    private String carroceria;

    @Size(max = 50, message = "O motor não pode exceder 50 caracteres.")
    private String motor;

    @Size(max = 100, message = "A versão não pode exceder 100 caracteres.")
    private String versao;

    // Campo para upload de múltiplas imagens
    private List<MultipartFile> imagens;

    // Getters e Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(String quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    public Long getCorId() {
        return corId;
    }

    public void setCorId(Long corId) {
        this.corId = corId;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
}