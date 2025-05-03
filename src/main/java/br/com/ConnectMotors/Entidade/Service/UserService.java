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
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        return jwtTokenUtil.generateToken(userDetails);
    }
    
    public void registerUser(UserRequestDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail()); // Adicionando esta linha para definir o email
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
            throw new Exception("USUÁRIO DESATIVADO", e);
        } catch (BadCredentialsException e) {
            throw new Exception("CREDENCIAIS INVÁLIDAS", e);
        }
    }
}