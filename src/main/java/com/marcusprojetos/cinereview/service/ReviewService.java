package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.repository.ReviewRepository;
import com.marcusprojetos.cinereview.repository.specs.ReviewSpecs;
import com.marcusprojetos.cinereview.security.SecurityService;
import com.marcusprojetos.cinereview.validator.ReviewValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;
    private final SecurityService securityService;
    private final ReviewValidator reviewValidator;

    public Review salvar(Review review) {
        Usuario usuario = securityService.obterUsuarioLogado();
        review.setUsuario(usuario);
        reviewValidator.validarReview(review);
        return repository.save(review);
    }

    public Optional<Review> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Review review){
        reviewValidator.validarExclusao(review);
        repository.delete(review);
    }

    public Page<Review> pesquisa(
            String nomeFilme,
            BigDecimal nota,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina){

        Specification<Review> specs = Specification.where((root, query, cb) ->
                cb.conjunction());

        if(nomeFilme != null){
            specs = specs.and(ReviewSpecs.nomeFilmeLike(nomeFilme));
        }

        if(nota != null){
            specs = specs.and(ReviewSpecs.notaReviewEqual(nota));
        }

        if(anoPublicacao != null){
            specs = specs.and(ReviewSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Review review) {
        reviewValidator.validarAtualizacao(review);
        repository.save(review);
    }
}
