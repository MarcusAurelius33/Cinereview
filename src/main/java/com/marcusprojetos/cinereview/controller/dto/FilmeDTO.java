package com.marcusprojetos.cinereview.controller.dto;

import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import jakarta.validation.constraints.*;


import java.util.UUID;

public record FilmeDTO(
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 100, message = "quantidade de caracteres não permitida")
        String titulo,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 10, max = 500, message = "quantidade de caracteres não permitida")
        String sinopse,
        @NotNull(message = "campo obrigatório")
        GeneroFilme generoFilme,
        @NotNull(message = "campo obrigatório")
        Integer anoLancamento
       ) {
}
