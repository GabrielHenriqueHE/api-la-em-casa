package br.laemcasa.api.services;

import br.laemcasa.api.domain.user.User;
import br.laemcasa.api.domain.user.UserRegisterDTO;
import br.laemcasa.api.domain.user.UserRole;
import br.laemcasa.api.exceptions.UserAlreadyExistsException;
import br.laemcasa.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

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
}
