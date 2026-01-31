package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public Review salvar(Review review) {
        return repository.save(review);
    }

    public Optional<Review> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Review review){
        repository.delete(review);
    }
}
