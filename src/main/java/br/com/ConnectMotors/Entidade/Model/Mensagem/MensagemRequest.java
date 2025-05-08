package br.com.ConnectMotors.Entidade.Model.Mensagem;

public class MensagemRequest {
    private Long destinatarioId;
    private String destinatarioUsername;
    private String conteudo;

    // Getters e setters
    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getDestinatarioUsername() {
        return destinatarioUsername;
    }

    public void setDestinatarioUsername(String destinatarioUsername) {
        this.destinatarioUsername = destinatarioUsername;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
