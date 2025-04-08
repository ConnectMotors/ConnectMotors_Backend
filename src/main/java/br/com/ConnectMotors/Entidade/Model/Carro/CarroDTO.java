package br.com.ConnectMotors.Entidade.Model.Carro;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarroDTO {

    @NotBlank(message = "O campo 'marca' é obrigatório.")
    private String marca;

    @NotBlank(message = "O campo 'modelo' é obrigatório.")
    private String modelo;

    @NotNull(message = "O campo 'ano' é obrigatório.")
    @Min(value = 1, message = "O ano deve ser maior que zero.")
    private int ano;

    @NotBlank(message = "O campo 'cor' é obrigatório.")
    private String cor;

    @NotBlank(message = "O campo 'câmbio' é obrigatório.")
    private String cambio;

    @NotBlank(message = "O campo 'combustível' é obrigatório.")
    private String combustivel;

    @NotBlank(message = "O campo 'carroceria' é obrigatório.")
    private String carroceria;

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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
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
}