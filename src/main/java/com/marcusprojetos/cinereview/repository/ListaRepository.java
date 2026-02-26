package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ListaRepository extends JpaRepository<Lista, UUID>, JpaSpecificationExecutor<Lista> {

    Optional<Lista> findByTituloAndUsuario(String titulo, Usuario usuario);

    boolean existsByIdAndFilmes_id(UUID idLista, UUID idFilme);

    @Query("SELECT l FROM Lista l LEFT JOIN FETCH l.filmes LEFT JOIN FETCH l.usuario WHERE l.id = :id")
    Optional<Lista> findByIdComDetalhes(@Param("id") UUID id);
}
