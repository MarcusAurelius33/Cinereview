package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository extends JpaRepository<Filme, UUID>, JpaSpecificationExecutor<Filme> {

    @Override
    Optional<Filme> findById(UUID id);

//    List<Filme> findByTitulo(String titulo);

//    List<Filme> findByAnoLancamento(Integer anoLancamento);

//    List<Filme> findByTituloAndAnoLancamento(String titulo, Integer anoLancamento);

    Optional<Filme> findByTituloAndSinopseAndGeneroFilmeAndAnoLancamento(String titulo,
                                                           String sinopse,
                                                           GeneroFilme generoFilme,
                                                           Integer anoLancamento);
}
