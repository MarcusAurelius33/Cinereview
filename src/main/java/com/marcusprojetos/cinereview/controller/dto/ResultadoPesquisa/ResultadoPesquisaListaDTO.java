package com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResultadoPesquisaListaDTO(
        UUID id,
        String nomeUsuario,
        String titulo
        //List<FilmeDTO> filmes
        ) {
}
