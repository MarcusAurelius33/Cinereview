package com.marcusprojetos.cinereview.controller.dto.ResultadoDetalhes;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewDetalhesDTO(
        String nomeUsuario,

        FilmeDTO filme,

        String texto,

        BigDecimal nota,

        LocalDateTime dataCadastro,

        LocalDateTime dataAtualizacao
) {
}
