package br.com.ConnectMotors.Entidade.Model.Anuncio;

import br.com.ConnectMotors.Entidade.Model.Carro.Carro;
import br.com.ConnectMotors.Entidade.Model.Moto.Moto;
import br.com.ConnectMotors.Entidade.Model.User.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Anuncio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = true)
    private Carro carro;

    @ManyToOne
    @JoinColumn(name = "moto_id", nullable = true)
    private Moto moto;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private String quilometragem;

    @ElementCollection
    @CollectionTable(name = "anuncio_imagens", joinColumns = @JoinColumn(name = "anuncio_id"))
    @Column(name = "imagem_path")
    private List<String> imagensPaths;

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

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public List<String> getImagensPaths() {
        return imagensPaths;
    }

    public void setImagensPaths(List<String> imagensPaths) {
        this.imagensPaths = imagensPaths;
    }
}