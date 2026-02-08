package com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa;

import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;

import java.util.UUID;

public record ResultadoPesquisaFilmeDTO(
        UUID id,
        String titulo,
        String sinopse,
        GeneroFilme generoFilme,
        Integer anoLancamento
) {
}
