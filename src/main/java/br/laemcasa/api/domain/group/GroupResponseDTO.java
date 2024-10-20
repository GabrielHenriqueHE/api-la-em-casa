package br.laemcasa.api.domain.group;

import java.util.UUID;

public record GroupResponseDTO(String name, String description, UUID owner) {
}
