package com.marcusprojetos.cinereview.entities;

import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "filme")
@ToString
@EntityListeners(AuditingEntityListener.class)
@Data
public class Filme {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo", length = 100, nullable = false )
    private String titulo;

    @Column(name = "sinopse", length = 500, nullable = false)
    private String sinopse;

    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 100, nullable = false)
    private GeneroFilme generoFilme;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
