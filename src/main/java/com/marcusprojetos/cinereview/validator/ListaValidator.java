package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.FonteNaoEncontradaException;
import com.marcusprojetos.cinereview.exceptions.OperacaoNaopermitidaException;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import com.marcusprojetos.cinereview.repository.ListaRepository;
import com.marcusprojetos.cinereview.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ListaValidator {

    private final ListaRepository repository;
    private final FilmeRepository filmeRepository;
    private final SecurityService securityService;


    public void validarLista(Lista lista){
        if (existeListaUsuario(lista)){
            throw new RegistroDuplicadoException("O usuário já possui uma lista com esse nome!");
        }
    }

    public Lista buscarEValidarPosse(UUID idLista){
        //Buscar lista
        Lista lista = repository.findById(idLista)
                .orElseThrow(() -> new FonteNaoEncontradaException("Lista não encontrada."));

        //seguranca
        Usuario usuarioLogado = securityService.obterUsuarioLogado();


        boolean isDono = lista.getUsuario().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getRoles().contains("ADMIN");
        if(!isDono && !isAdmin){
            throw  new OperacaoNaopermitidaException("Você não tem permissão para alterar essa lista.");
        }

        return lista;
    }

    public Filme buscarEValidarFilme(UUID idFilme) {
        return filmeRepository.findById(idFilme).orElseThrow(()
                -> new FonteNaoEncontradaException("Filme não encontrado."));
    }

    public void filmePresente(Lista lista, Filme filme){
        if (repository.existsByIdAndFilmes_id(lista.getId(), filme.getId())){
            throw new RegistroDuplicadoException("Filme já presente na lista");
        }
    }

    public void filmeNaoPresente(Lista lista, Filme filme){
        if (!repository.existsByIdAndFilmes_id(lista.getId(), filme.getId())){
            throw new FonteNaoEncontradaException("Filme não existe na lista");
        }
    }


    public void validarExclusaoLista(Lista lista){
        Usuario usuarioLogado = securityService.obterUsuarioLogado();
        boolean isDono = lista.getUsuario().getId().equals(usuarioLogado.getId());
        boolean isAdmin = usuarioLogado.getRoles().contains("ADMIN");

        if(!isDono && !isAdmin){
            throw  new OperacaoNaopermitidaException("Você não tem permissão para alterar essa lista.");
        }

        if(!existeLista(lista)){
            throw new FonteNaoEncontradaException("Lista não encontrada.");
        }
    }


    private boolean existeLista(Lista lista){
        Optional<Lista> listaAux = repository.findById(lista.getId());
        return listaAux.isPresent();
    }


    private boolean existeListaUsuario(Lista lista) {
        Optional<Lista> listaEncontrada = repository.findByTituloAndUsuario(
                lista.getTitulo(),
                lista.getUsuario());

        if (lista.getId() == null) {
            return listaEncontrada.isPresent();
        }
        return listaEncontrada.isPresent() && !lista.getId().equals(listaEncontrada.get().getId());
        }
    }
