package br.com.ConnectMotors.Entidade.Model.User;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "O username ou email é obrigatório")
    private String identifier;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}