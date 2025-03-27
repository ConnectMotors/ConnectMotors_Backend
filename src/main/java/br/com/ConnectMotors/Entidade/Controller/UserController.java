package br.com.ConnectMotors.Entidade.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ConnectMotors.Entidade.Model.User.UserRequestDTO;
import br.com.ConnectMotors.Entidade.Model.User.UserResponseDTO;
import br.com.ConnectMotors.Entidade.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class UserController {

    @Autowired
    private UserService authenticationService;

    @PostMapping("/login")
    @Operation(
        summary = "Autenticar usuário",
        description = "Gera um token JWT para acesso aos endpoints protegidos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso", 
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
        }
    )
    public ResponseEntity<?> createAuthenticationToken(
        @RequestBody(description = "Dados para autenticação", required = true, 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class)))
        @org.springframework.web.bind.annotation.RequestBody UserRequestDTO authenticationRequest
    ) throws Exception {
        String token = authenticationService.authenticateUser(authenticationRequest);
        return ResponseEntity.ok(new UserResponseDTO(token));
    }


    @PostMapping("/register")
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria um novo usuário no sistema e retorna uma mensagem de sucesso.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar usuário")
        }
    )
    public ResponseEntity<?> saveUser(
        @RequestBody(description = "Dados para registro de usuário", required = true, 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDTO.class)))
        @org.springframework.web.bind.annotation.RequestBody UserRequestDTO user
    ) {
        authenticationService.registerUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário registrado com sucesso");

        return ResponseEntity.ok(response);
    }
}