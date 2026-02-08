package com.marcusprojetos.cinereview.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@ToString(exclude = "filme")
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn(name = "id_filme", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Filme filme;

    @Column(name = "texto", length = 5000, nullable = false)
    private String texto;

    @Column(name = "nota", precision = 14, scale = 2, nullable = false)
    private BigDecimal nota;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @CreatedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}
