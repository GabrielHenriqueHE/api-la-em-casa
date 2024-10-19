package br.laemcasa.api.controllers;

import br.laemcasa.api.domain.user.UserRegisterDTO;
import br.laemcasa.api.exceptions.UserAlreadyExistsException;
import br.laemcasa.api.repositories.UserRepository;
import br.laemcasa.api.services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserRegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new UserAlreadyExistsException(data.email());
        }

        return ResponseEntity.ok(authenticationService.register(data));
    }
}
