package com.marcusprojetos.cinereview.entities;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.UUID;

@Entity
@ToString
public class Review {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //@JoinColumn(name = "filme_id")
    @OneToOne
    private Filme filme;

    @Column(name = "comentário")
    private String texto;

    @Column(name = "nota")
    private Double nota;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
