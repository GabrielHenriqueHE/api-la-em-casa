package br.laemcasa.api.controllers;

import br.laemcasa.api.domain.user.User;
import br.laemcasa.api.domain.user.UserAuthenticationDTO;
import br.laemcasa.api.domain.user.UserRegisterDTO;
import br.laemcasa.api.exceptions.UserAlreadyExistsException;
import br.laemcasa.api.infra.security.TokenService;
import br.laemcasa.api.repositories.UserRepository;
import br.laemcasa.api.services.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserRegisterDTO data) {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new UserAlreadyExistsException(data.email());
        }

        return ResponseEntity.ok(authenticationService.register(data));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated UserAuthenticationDTO data) {
        return ResponseEntity.ok().body(authenticationService.login(data));
    }
}
