package br.laemcasa.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No token provided")
public class TokenRequiredException extends RuntimeException {
    public TokenRequiredException(String message) {
        super(message);
    }
}
