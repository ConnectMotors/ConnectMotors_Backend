package br.com.ConnectMotors.Entidade.Model.User;

public class UserResponseDTO {
    private final String token;

    public UserResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}