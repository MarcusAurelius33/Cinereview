package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.CampoInvalidoException;
import com.marcusprojetos.cinereview.exceptions.FonteNaoEncontradaException;
import com.marcusprojetos.cinereview.exceptions.OperacaoNaopermitidaException;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import com.marcusprojetos.cinereview.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewValidator {


    private final ReviewRepository reviewRepository;
    private final SecurityService securityService;

    public void validarReview(Review review){
        if (existeReviewDoUsuario(review)){
            throw new RegistroDuplicadoException("O usuário já possui um review desse filme!");
        }

        if(review.getNota().toBigInteger().doubleValue() < 0.0 || review.getNota().toBigInteger().doubleValue() > 5.0){
            throw new CampoInvalidoException("nota", "A nota deve ser maior ou igual a 0 e menor ou igual a 5.");
        }
    }

    public void validarAtualizacao(Review review){
        if(review.getNota().toBigInteger().doubleValue() < 0.0 || review.getNota().toBigInteger().doubleValue() > 5.0){
            throw new CampoInvalidoException("nota", "A nota deve ser maior ou igual a 0 e menor ou igual a 5.");
        }

        Usuario usuarioLogado = securityService.obterUsuarioLogado();

        boolean isDono = review.getUsuario().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getRoles().contains("ADMIN");

        if(!isDono && !isAdmin){
            throw  new OperacaoNaopermitidaException("Você não tem permissão para alterar essa lista.");
        }
    }

    public void validarExclusao(Review review){
        if(!existeReviewDoUsuario(review)){
            throw new OperacaoNaopermitidaException("O usuário não possui um review desse filme!");
        }

        if(!existeReview(review)){
            throw new FonteNaoEncontradaException("Review não encontrado!");
        }

        Usuario usuarioLogado = securityService.obterUsuarioLogado();

        boolean isDono = review.getUsuario().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getRoles().contains("ADMIN");

        if(!isDono && !isAdmin){
            throw  new OperacaoNaopermitidaException("Você não tem permissão para alterar essa lista.");
        }
    }

    public Optional<Review> validarGetId(UUID reviewId){
        Optional<Review> reviewAux = reviewRepository.findById(reviewId);
        if (!reviewAux.isPresent()){
            throw new FonteNaoEncontradaException("Review não encontrada.");
        }
        return reviewAux;
    }

    private boolean existeReview(Review review){
        Optional<Review> reviewAux = reviewRepository.findById(review.getId());
        return reviewAux.isPresent();
    }

    private boolean existeReviewDoUsuario(Review review){
        Optional<Review> reviewAux = reviewRepository.findByFilmeAndUsuario(review.getFilme(), review.getUsuario());
        return reviewAux.isPresent();
    }
}
