package com.marcusprojetos.cinereview.repository.specs;

import com.marcusprojetos.cinereview.entities.Review;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReviewSpecs {

    public static Specification<Review> nomeFilmeLike(String nomeFilme){
        // upper(livro.titulo) like (%:param%)
        return (root, query, cb) -> {
          return cb.like(cb.upper(root.get("filme").get("titulo")), "%" + nomeFilme.toUpperCase() + "%");
        };
    }

    public static Specification<Review> notaReviewEqual(BigDecimal notaReview){
        return (root, query, cb) -> cb.equal(root.get("nota"), notaReview);
    }

    public static Specification<Review> anoPublicacaoEqual(Integer anoPublicacao){
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataCadastro"), cb.literal("YYYY")), anoPublicacao.toString());
    }
}
