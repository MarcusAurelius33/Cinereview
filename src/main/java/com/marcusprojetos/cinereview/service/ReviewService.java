package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public Review salvar(Review review) {
        return repository.save(review);
    }
}
