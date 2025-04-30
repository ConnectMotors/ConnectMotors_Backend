package br.com.ConnectMotors.Entidade.Model.Carro;

import br.com.ConnectMotors.Entidade.Enums.Cambio;
import br.com.ConnectMotors.Entidade.Enums.Carroceria;
import br.com.ConnectMotors.Entidade.Enums.Combustivel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CarroDTO {

    @NotNull(message = "O campo 'marcaId' é obrigatório.")
    private Long marcaId;

    @NotNull(message = "O campo 'modeloId' é obrigatório.")
    private Long modeloId;

    @NotNull(message = "O campo 'corId' é obrigatório.")
    private Long corId;

    @NotNull(message = "O campo 'ano de fabricação' é obrigatório.")
    @Min(value = 1886, message = "O ano de fabricação deve ser maior ou igual a 1886.") // Primeiro carro fabricado
    private int anoFabricacao;

    @NotNull(message = "O campo 'ano do modelo' é obrigatório.")
    @Min(value = 1886, message = "O ano do modelo deve ser maior ou igual a 1886.")
    private int anoModelo;

    @NotNull(message = "O campo 'câmbio' é obrigatório.")
    private Cambio cambio;

    @NotNull(message = "O campo 'combustível' é obrigatório.")
    private Combustivel combustivel;

    @NotNull(message = "O campo 'carroceria' é obrigatório.")
    private Carroceria carroceria;

    private String motor;
    private String versao;

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

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Carroceria getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(Carroceria carroceria) {
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
}