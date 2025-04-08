package br.com.ConnectMotors.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:5173",  // Frontend local
                "https://seu-dominio-de-producao.com.br",  // Domínio de produção
                "http://localhost:8080"   // Caso tenha outro serviço local
            )
            .allowedMethods(
                "GET", 
                "POST", 
                "PUT", 
                "DELETE", 
                "OPTIONS", 
                "PATCH"
            )
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600); // Tempo máximo de cache das opções de CORS
    }
}