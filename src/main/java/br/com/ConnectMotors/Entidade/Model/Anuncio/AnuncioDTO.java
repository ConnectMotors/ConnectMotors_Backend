package br.com.ConnectMotors.Entidade.Model.Anuncio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class AnuncioDTO {

    @NotNull(message = "O ID do carro é obrigatório.")
    private Long carroId;

    @NotNull(message = "O preço é obrigatório.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private double preco;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotBlank(message = "A quilometragem é obrigatória.")
    private String quilometragem;

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @NotNull(message = "A confirmação dos dados é obrigatória.")
    private boolean dadosConfirmados;

    // Campo para armazenar as URLs das fotos
    private List<String> fotos;

    // Getters e Setters
    public Long getCarroId() {
        return carroId;
    }

    public void setCarroId(Long carroId) {
        this.carroId = carroId;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isDadosConfirmados() {
        return dadosConfirmados;
    }

    public void setDadosConfirmados(boolean dadosConfirmados) {
        this.dadosConfirmados = dadosConfirmados;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}