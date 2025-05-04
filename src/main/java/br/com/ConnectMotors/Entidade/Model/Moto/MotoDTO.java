package br.com.ConnectMotors.Entidade.Model.Moto;

import jakarta.validation.constraints.*;

public class MotoDTO {

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

    @Size(max = 100, message = "A versão não pode exceder 100 caracteres.")
    private String versao;

    @NotBlank(message = "O campo 'freio' é obrigatório.")
    private String freio;

    @NotBlank(message = "O campo 'partida' é obrigatório.")
    private String partida;

    @NotBlank(message = "O campo 'cilindrada' é obrigatório.")
    private String cilindrada;

    @NotBlank(message = "O campo 'combustível' é obrigatório.")
    private String combustivel;

    // Getters e Setters
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

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getFreio() {
        return freio;
    }

    public void setFreio(String freio) {
        this.freio = freio;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }
}