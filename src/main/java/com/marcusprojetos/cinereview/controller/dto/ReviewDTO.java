package com.marcusprojetos.cinereview.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ReviewDTO(
        @NotNull(message = "campo obrigatório")
        UUID id_filme,
        @NotBlank(message = "campo obrigatório")
        String texto,
        @NotNull(message = "campo obrigatório")
        BigDecimal nota
                        ) {
}

