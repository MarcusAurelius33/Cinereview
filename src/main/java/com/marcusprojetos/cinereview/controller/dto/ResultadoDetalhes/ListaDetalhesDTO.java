package com.marcusprojetos.cinereview.controller.dto.ResultadoDetalhes;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ListaDetalhesDTO(
        String titulo,

        String descricao,

        String nomeUsuario,

        LocalDateTime dataCriacao,

        LocalDateTime dataModificacao,

        List<FilmeDTO> filmes
) {
}
