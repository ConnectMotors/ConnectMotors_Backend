package br.com.ConnectMotors.Entidade.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("/public")
    public Map<String, String> publicResource() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Este é um recurso público");
        return response;
    }

    @GetMapping("/private")
    public Map<String, String> privateResource() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Este é um recurso privado que requer autenticação");
        return response;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> adminResource() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Este é um recurso restrito para administradores");
        return response;
    }
}
