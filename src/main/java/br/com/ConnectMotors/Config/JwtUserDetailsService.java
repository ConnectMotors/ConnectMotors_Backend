package br.com.ConnectMotors.Config;

import br.com.ConnectMotors.Entidade.Model.User.User;
import br.com.ConnectMotors.Entidade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(JwtUserDetailsService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Carregando usuário com username: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.severe("Usuário não encontrado: " + username);
                    return new UsernameNotFoundException("Usuário não encontrado: " + username);
                });

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Adiciona prefixo ROLE_
                .collect(Collectors.toList());

        LOGGER.info("Usuário encontrado: " + user.getUsername() + ", roles: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}