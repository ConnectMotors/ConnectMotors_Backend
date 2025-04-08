package br.com.ConnectMotors.Entidade.Model.Carro;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarroDTO {

    @NotBlank(message = "O campo 'marca' é obrigatório.")
    private String marca;

    @NotBlank(message = "O campo 'modelo' é obrigatório.")
    private String modelo;

    @NotNull(message = "O campo 'ano de fabricação' é obrigatório.")
    @Min(value = 1886, message = "O ano de fabricação deve ser maior ou igual a 1886.") // Primeiro carro fabricado
    private int anoFabricacao;

    @NotNull(message = "O campo 'ano do modelo' é obrigatório.")
    @Min(value = 1886, message = "O ano do modelo deve ser maior ou igual a 1886.")
    private int anoModelo;

    @NotBlank(message = "O campo 'cor' é obrigatório.")
    private String cor;

    @NotBlank(message = "O campo 'câmbio' é obrigatório.")
    private String cambio;

    @NotBlank(message = "O campo 'combustível' é obrigatório.")
    private String combustivel;

    @NotBlank(message = "O campo 'carroceria' é obrigatório.")
    private String carroceria;

    @NotBlank(message = "O campo 'motor' é obrigatório.")
    private String motor;

    @NotBlank(message = "O campo 'versão' é obrigatório.")
    private String versao;

    // Getters e Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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
}