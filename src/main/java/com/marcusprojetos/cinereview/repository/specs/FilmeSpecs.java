package com.marcusprojetos.cinereview.repository.specs;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import org.springframework.data.jpa.domain.Specification;

public class FilmeSpecs {

    public static Specification<Filme> tituloFilmeLike(String tituloFilme){
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + tituloFilme.toUpperCase() + "%");
    }

    public static Specification<Filme> generoFilmeLike(GeneroFilme generoFilme){
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("generoFilme").as(String.class)), "%" + generoFilme
                        .toString()
                        .toUpperCase() + "%"));
    }

    public static Specification<Filme> anoLancamentoFilmeEqual(Integer anoLancamentoFilme){
        return ((root, query, cb) ->
                cb.equal(root.get("anoLancamento"), anoLancamentoFilme));
    }

}
