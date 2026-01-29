package com.marcusprojetos.cinereview.controller.dto;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;

import java.util.UUID;

public record FilmeDTO(
        UUID id,
        String titulo,
        String sinopse,
        GeneroFilme genero,
        Double nota) {

    public Filme mapearParaFilme(){
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setSinopse(sinopse);
        filme.setGeneroFilme(genero);
        filme.setNota(nota);
        return filme;
    }
}
