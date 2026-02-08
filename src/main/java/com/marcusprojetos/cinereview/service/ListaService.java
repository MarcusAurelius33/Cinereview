package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.repository.ListaRepository;
import com.marcusprojetos.cinereview.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ListaService {

    private final ListaRepository repository;
    private final SecurityService securityService;

    public void salvar(Lista lista) {
        Usuario usuario = securityService.obterUsuarioLogado();
        lista.setUsuario(usuario);
        repository.save(lista);
    }

/*
    public Optional<Review> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Review review){
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
        if(review.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o filme já tenha sido salvo!");
        }
        repository.save(review);
    }
*/

}
