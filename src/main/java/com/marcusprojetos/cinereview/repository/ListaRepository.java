package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ListaRepository extends JpaRepository<Lista, UUID>, JpaSpecificationExecutor<Lista> {
}
