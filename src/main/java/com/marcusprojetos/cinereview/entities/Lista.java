package com.marcusprojetos.cinereview.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lista")
@ToString
@Data
@EntityListeners(AuditingEntityListener.class)
public class Lista {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @CreatedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    @ManyToOne
    @JoinColumn (name = "id_usuario")
    private Usuario usuario;

    @ManyToMany // Use ManyToMany pois um filme pode estar em várias listas
    @JoinTable(
            name = "lista_filme", // Nome da nova tabela de ligação
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private List<Filme> filmes;
}
