package com.marcusprojetos.cinereview.repository.specs;

import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ListaSpecs {

    public static Specification<Lista> tituloListaLike(String tituloLista){
        return (root, query, cb)
                -> cb.like(cb.upper(root.get("titulo")), "%" + tituloLista.toUpperCase() + "%");
    }

    public static Specification<Lista> nomeUsuarioListaLike(String nomeUsuarioLista){
        return ((root, query, cb)
                -> {
            if (nomeUsuarioLista == null || nomeUsuarioLista.isBlank()) {
                return cb.conjunction();
            }

            Join<Lista, Usuario> usuario = root.join("usuario");
            return cb.like(cb.upper(usuario.get("login")), "%" + nomeUsuarioLista.toUpperCase() + "%");
        });
    }
}
