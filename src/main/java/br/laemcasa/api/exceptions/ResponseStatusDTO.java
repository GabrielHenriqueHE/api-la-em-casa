package br.laemcasa.api.exceptions;

import org.springframework.http.HttpStatus;

public record ResponseStatusDTO(HttpStatus status, String message) {
}
