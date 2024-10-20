package br.laemcasa.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseStatusDTO> handleUserAlreadyExistsException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseStatusDTO(HttpStatus.CONFLICT, "User already exists."));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStatusDTO> handleUserNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatusDTO(HttpStatus.BAD_REQUEST, "User not found by email."));
    }

    @ExceptionHandler(TokenRequiredException.class)
    public ResponseEntity<ResponseStatusDTO> handleTokenRequiredException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseStatusDTO(HttpStatus.BAD_REQUEST, "Invalid or no token provided."));
    }
}
