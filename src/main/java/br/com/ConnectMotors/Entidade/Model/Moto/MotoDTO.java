package br.com.ConnectMotors.Entidade.Model.Moto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MotoDTO {

    @NotBlank(message = "O campo 'marca' é obrigatório.")
    private String marca;

    @NotBlank(message = "O campo 'modelo' é obrigatório.")
    private String modelo;

    @NotBlank(message = "O campo 'cor' é obrigatório.")
    private String cor;

    @NotNull(message = "O campo 'ano' é obrigatório.")
    @Min(value = 1, message = "O ano deve ser maior que zero.")
    private int ano;

    @NotBlank(message = "O campo 'freio' é obrigatório.")
    private String freio;

    @NotBlank(message = "O campo 'partida' é obrigatório.")
    private String partida;

    @NotNull(message = "O campo 'cilindrada' é obrigatório.")
    @Min(value = 1, message = "A cilindrada deve ser maior que zero.")
    private int cilindrada;

    @NotBlank(message = "O campo 'combustível' é obrigatório.")
    private String combustivel;

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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
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

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }
}