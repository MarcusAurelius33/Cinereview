package com.marcusprojetos.cinereview.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "campo obrigatório")
        String login,
        @NotBlank(message = "campo obrigatório")
        String senha,
        @NotNull(message = "campo obrigatório")
        List<String> roles
) {
}
