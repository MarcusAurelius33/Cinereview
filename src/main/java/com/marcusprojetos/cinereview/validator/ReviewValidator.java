package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {


    private ReviewRepository repository;

    public void ValidarReview(Review reivew){

    }

    //private boolean exiteReviw


}
