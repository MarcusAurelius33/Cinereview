package com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa;

import java.util.UUID;

public record ResultadoPesquisaListaDTO(
        UUID id,
        String nomeUsuario,
        String titulo,
        String descricao
        //List<FilmeDTO> filmes
        ) {
}
