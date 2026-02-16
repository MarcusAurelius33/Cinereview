package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ListaRepository extends JpaRepository<Lista, UUID>, JpaSpecificationExecutor<Lista> {

    Optional<Lista> findByTituloAndUsuario(String titulo, Usuario usuario);
}
