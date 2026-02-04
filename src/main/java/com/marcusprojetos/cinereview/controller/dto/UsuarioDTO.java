package com.marcusprojetos.cinereview.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
        @NotNull(message = "campo obrigatório")
        String login,
        @NotNull(message = "campo obrigatório")
        String senha,
        @NotNull(message = "campo obrigatório")
        List<String> roles
) {
}
