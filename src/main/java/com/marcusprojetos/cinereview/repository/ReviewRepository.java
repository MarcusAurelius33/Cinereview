package com.marcusprojetos.cinereview.repository;

import com.marcusprojetos.cinereview.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
