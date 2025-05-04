package br.com.ConnectMotors.Entidade.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ConnectMotors.Config.JwtTokenUtil;
import br.com.ConnectMotors.Config.JwtUserDetailsService;
import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Model.User.UserRequestDTO;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public String authenticateUser(UserRequestDTO authenticationRequest) throws Exception {
        String identifier = authenticationRequest.getUsername(); // Pode ser username ou email
        String password = authenticationRequest.getPassword();

        // Validação inicial do identificador
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new Exception("Identificador (username ou email) é obrigatório");
        }

        // Busca o usuário pelo username ou email
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(identifier);
        if (!userOptional.isPresent()) {
            throw new Exception("Usuário não encontrado com o identificador: " + identifier);
        }

        // Extrai o username real do usuário
        User user = userOptional.get();
        String username = user.getUsername();

        // Autentica usando o username real
        authenticate(username, password);

        // Carrega os detalhes do usuário para gerar o token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new Exception("Falha ao carregar detalhes do usuário para: " + username);
        }

        return jwtTokenUtil.generateToken(userDetails);
    }

    public void registerUser(UserRequestDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(bcryptEncoder.encode(userDTO.getPassword()));
        List<String> roles = userDTO.getRoles() != null && !userDTO.getRoles().isEmpty() 
            ? userDTO.getRoles() 
            : Arrays.asList("ROLE_USER");
        newUser.setRoles(roles);
        userRepository.save(newUser);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Usuário desativado", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciais inválidas para o usuário: " + username, e);
        } catch (Exception e) {
            throw new Exception("Erro durante a autenticação: " + e.getMessage(), e);
        }
    }
}