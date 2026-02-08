package com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResultadoPesquisaReviewDTO(
        UUID id,
        String nomeUsuario,
        @JsonIgnoreProperties({"sinopse"})
        FilmeDTO filme,
        LocalDateTime dataCadastro,
        String texto,
        BigDecimal nota
) {
}
