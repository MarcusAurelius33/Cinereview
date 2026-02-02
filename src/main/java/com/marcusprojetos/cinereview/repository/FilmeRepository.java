package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository extends JpaRepository<Filme, UUID> {

    List<Filme> findByTitulo(String titulo);

    List<Filme> findByAnoLancamento(Integer anoLancamento);

    List<Filme> findByTituloAndAnoLancamento(String titulo, Integer anoLancamento);

    Optional<Filme> findByTituloAndSinopseAndGeneroFilmeAndAnoLancamento(String titulo,
                                                           String sinopse,
                                                           GeneroFilme generoFilme,
                                                           Integer anoLancamento);
}
