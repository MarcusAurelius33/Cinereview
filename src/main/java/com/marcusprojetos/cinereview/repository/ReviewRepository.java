package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review> {

    Optional<Review> findByFilmeAndUsuario(Filme filme, Usuario usuario);
}
