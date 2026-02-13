package com.marcusprojetos.cinereview.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ReviewDTO(
        @NotNull(message = "campo obrigatório")
        UUID id_filme,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 5000, message = "quantidade de caracteres não permitida")
        String texto,
        @NotNull(message = "campo obrigatório")
        BigDecimal nota
                        ) {
}

