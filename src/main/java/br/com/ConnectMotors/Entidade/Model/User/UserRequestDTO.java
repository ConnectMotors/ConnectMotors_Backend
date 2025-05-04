package br.com.ConnectMotors.Entidade.Model.User;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {

    @NotBlank(message = "O username ou email é obrigatório")
    private String username; // Pode ser username ou email para login

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    private List<String> roles;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRequestDTO(String username, String email, String password, List<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}