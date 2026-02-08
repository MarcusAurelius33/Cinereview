package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewValidator {


    private final ReviewRepository reviewRepository;

    public void validarReview(Review review){
        if (existeReviewDoUsuario(review)){
            throw new RegistroDuplicadoException("O usuário já possui um review desse filme!");
        }
    }

    private boolean existeReviewDoUsuario(Review review){
        Optional<Review> reviewAux = reviewRepository.findByFilmeAndUsuario(review.getFilme(), review.getUsuario());
        return reviewAux.isPresent();
    }
}
