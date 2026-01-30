package com.marcusprojetos.cinereview.controller.dto;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import jakarta.validation.constraints.*;


import java.util.UUID;

public record FilmeDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 2, max = 100, message = "quantidade de caracteres não permitida")
        String titulo,
        @NotBlank(message = "campo obrigatório")
        @Size(min = 10, max = 500, message = "quantidade de caracteres não permitida")
        String sinopse,
        @NotNull(message = "campo obrigatório")
        GeneroFilme generoFilme,
        @NotNull(message = "campo obrigatório")
        @Positive(message = "campo deve ser preenchido com um número positivo")
        Double nota) {

    public Filme mapearParaFilme(){
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setSinopse(sinopse);
        filme.setGeneroFilme(generoFilme);
        filme.setNota(nota);
        return filme;
    }
}
