package com.marcusprojetos.cinereview.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResultadoPesquisaReviewDTO(
        UUID id,
        FilmeDTO filme,
        LocalDateTime dataCadastro,
        String texto,
        BigDecimal nota
) {
}
