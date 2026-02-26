package com.marcusprojetos.cinereview.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Usuario;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
