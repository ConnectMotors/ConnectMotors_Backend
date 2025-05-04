package br.com.ConnectMotors.Entidade.Model.Moto;

import br.com.ConnectMotors.Entidade.Model.Cor.Cor;
import br.com.ConnectMotors.Entidade.Model.Marca.Marca;
import br.com.ConnectMotors.Entidade.Model.Modelo.Modelo;
import jakarta.persistence.*;

@Entity
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "cor_id", nullable = false)
    private Cor cor;

    @Column(nullable = false)
    private Integer anoFabricacao; // Renomeado de 'ano' para consistência com Carro

    @Column(nullable = false)
    private Integer anoModelo; // Novo campo

    @Column(length = 100)
    private String versao; // Novo campo

    @Column(nullable = false)
    private String freio; // Ex.: "Disco", "Tambor"

    @Column(nullable = false)
    private String partida; // Ex.: "Elétrica", "Pedal"

    @Column(nullable = false)
    private String cilindrada; // Ex.: "150", "300" (em cc)

    @Column(nullable = false)
    private String combustivel; // Ex.: "Gasolina", "Flex"

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
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