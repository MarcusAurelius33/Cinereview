package com.marcusprojetos.cinereview.entities;

import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "filme")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Filme {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo", length = 100, nullable = false )
    private String titulo;

    @Column(name = "sinopse", length = 500, nullable = false)
    private String sinopse;

    @Column(name = "nota", precision = 14)
    private Double nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 100, nullable = false)
    private GeneroFilme generoFilme;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;


    @Column(name = "id_usuario")
    private UUID idUsuario;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public GeneroFilme getGeneroFilme() {
        return generoFilme;
    }

    public void setGeneroFilme(GeneroFilme generoFilme) {
        this.generoFilme = generoFilme;
    }
}
