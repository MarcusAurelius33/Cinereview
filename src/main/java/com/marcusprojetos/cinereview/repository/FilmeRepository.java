package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository extends JpaRepository<Filme, UUID> {

    List<Filme> findByTitulo(String titulo);

    List<Filme> findByNota(Double nota);

    List<Filme> findByTituloAndNota(String titulo, Double nota);

    Optional<Filme> findByTituloAndSinopseAndGeneroFilmeAndNota(String titulo,
                                                           String sinopse,
                                                           GeneroFilme generoFilme,
                                                           Double nota);
}
