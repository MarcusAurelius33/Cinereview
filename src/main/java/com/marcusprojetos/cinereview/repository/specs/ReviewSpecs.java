package com.marcusprojetos.cinereview.repository.specs;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.entities.Usuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ReviewSpecs {

    public static Specification<Review> nomeFilmeLike(String nomeFilme){
        return (root, query, cb) -> {
            if (nomeFilme == null || nomeFilme.isBlank()) {
                return cb.conjunction();
            }
            Join<Review, Filme> filme = root.join("filme");
            return cb.like(cb.upper(filme.get("titulo")), "%" + nomeFilme.toUpperCase() + "%");
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

    public static Specification<Review> nomeUsuarioReviewLike(String nomeUsuarioReview){
        return ((root, query, cb)
                -> {
            if (nomeUsuarioReview == null || nomeUsuarioReview.isBlank()) {
                return cb.conjunction();
            }

            Join<Lista, Usuario> usuario = root.join("usuario");
            return cb.like(cb.upper(usuario.get("login")), "%" + nomeUsuarioReview.toUpperCase() + "%");
        });
    }
}
