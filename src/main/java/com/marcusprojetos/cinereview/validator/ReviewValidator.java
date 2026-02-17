package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.OperacaoNaopermitidaException;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import com.marcusprojetos.cinereview.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewValidator {


    private final ReviewRepository reviewRepository;
    private final SecurityService securityService;

    public void validarReview(Review review){
        if (existeReviewDoUsuario(review)){
            throw new RegistroDuplicadoException("O usuário já possui um review desse filme!");
        }
    }

    public void validarAtualizacao(Review review){
        if(review.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o filme já tenha sido salvo!");
        }

        Usuario usuarioLogado = securityService.obterUsuarioLogado();

        if(!review.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new OperacaoNaopermitidaException("Você não tem permissão para alterar esta review!");
        }
    }

    public void validarExclusao(Review review){
        if(!existeReviewDoUsuario(review)){
            throw new OperacaoNaopermitidaException("O usuário não possui um review desse filme!");
        }

        Usuario usuarioLogado = securityService.obterUsuarioLogado();

        if(!review.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new OperacaoNaopermitidaException("Você não tem permissão para excluir esta review!");
        }
    }

    private boolean existeReviewDoUsuario(Review review){
        Optional<Review> reviewAux = reviewRepository.findByFilmeAndUsuario(review.getFilme(), review.getUsuario());
        return reviewAux.isPresent();
    }
}
