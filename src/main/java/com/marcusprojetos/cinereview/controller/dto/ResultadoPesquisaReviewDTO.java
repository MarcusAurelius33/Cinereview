package com.marcusprojetos.cinereview.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ResultadoPesquisaReviewDTO(
        UUID id,
        FilmeDTO filme,
        String texto,
        BigDecimal nota
) {
}
