package br.com.ConnectMotors.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> 
                    auth
                        // Authentication endpoints
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        
                        // Swagger documentation
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Public endpoints (GET only for anuncios)
                        .requestMatchers("GET", "/anuncios/marcas").permitAll()
                        .requestMatchers("GET", "/uploads/**").permitAll()
                        .requestMatchers("GET", "/anuncios/modelos/{marcaId}").permitAll()
                        .requestMatchers("GET", "/anuncios").permitAll()

                        // Admin endpoints (for car, brand, and model management)
                        .requestMatchers("/admin/carros/**").hasRole("ADMIN")
                        .requestMatchers("/admin/marcas/**").hasRole("ADMIN")
                        .requestMatchers("/admin/modelos/**").hasRole("ADMIN")

                        // Authenticated endpoints (POST for anuncios requires login)
                        .requestMatchers("POST", "/anuncios").authenticated()
                        .requestMatchers("/api/private").authenticated()

                        // Admin-only endpoint
                        .requestMatchers("/api/admin").hasRole("ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}