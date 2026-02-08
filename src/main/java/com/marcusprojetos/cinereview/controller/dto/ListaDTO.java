package com.marcusprojetos.cinereview.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record ListaDTO(
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 100, message = "quantidade de caracteres não permitida")
        String titulo,
        String descricao,
        List<UUID> id_filmes
        ) {

}
