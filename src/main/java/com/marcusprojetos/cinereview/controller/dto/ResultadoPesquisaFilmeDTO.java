package com.marcusprojetos.cinereview.controller.dto;

import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;

import java.math.BigDecimal;
import java.util.UUID;

public record ResultadoPesquisaFilmeDTO(
        UUID id,
        String titulo,
        String sinopse,
        GeneroFilme generoFilme,
        Integer anoLancamento
) {
}
