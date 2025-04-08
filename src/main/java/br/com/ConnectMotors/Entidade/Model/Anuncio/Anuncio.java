package br.com.ConnectMotors.Entidade.Model.Anuncio;

import jakarta.persistence.*;
import java.util.List;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.User.User;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private String quilometragem;

    @Column(nullable = false)
    private String cep;

    private String cidade;
    private String estado;
    private String bairro;

    @ElementCollection
    @CollectionTable(name = "anuncio_fotos", joinColumns = @JoinColumn(name = "anuncio_id"))
    @Column(name = "foto", nullable = false)
    private List<String> fotos; // Lista para armazenar múltiplas imagens

    @Column(nullable = false)
    private boolean dadosConfirmados;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public boolean isDadosConfirmados() {
        return dadosConfirmados;
    }

    public void setDadosConfirmados(boolean dadosConfirmados) {
        this.dadosConfirmados = dadosConfirmados;
    }
}