package br.laemcasa.api.services;

import br.laemcasa.api.domain.user.User;
import br.laemcasa.api.domain.user.UserAuthenticationDTO;
import br.laemcasa.api.domain.user.UserRegisterDTO;
import br.laemcasa.api.domain.user.UserRole;
import br.laemcasa.api.exceptions.UserAlreadyExistsException;
import br.laemcasa.api.infra.security.TokenService;
import br.laemcasa.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Transactional
    public User register(UserRegisterDTO data) throws UserAlreadyExistsException {
        User user = new User();
        user.setEmail(data.email());
        user.setUsername(data.username());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);
        user.setCreatedAt(Timestamp.from(Instant.now()));

        return userRepository.save(user);
    }

    public String login(UserAuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public User getUserByToken(String token) {
        return this.userRepository.findByEmail(this.tokenService.validateToken(token));
    }

}
