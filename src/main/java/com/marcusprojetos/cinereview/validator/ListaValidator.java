package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
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

    public void validarAdicao(UUID idLista, UUID idFilme){
        Lista lista = repository.findById(idLista)
                .orElseThrow(() -> new IllegalArgumentException("Lista não encontrada."));

        //segurança
        Usuario usuarioLogado = securityService.obterUsuarioLogado();
        if (!lista.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new OperacaoNaopermitidaException("Você não tem permissão para alterar esta lista.");
        }

        //Busca o filme
        Filme filme = filmeRepository.findById(idFilme)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado."));

        // filme duplicado
        if (lista.getFilmes().contains(filme)) {
            throw new RegistroDuplicadoException("Este filme já está na sua lista!");
        }

    }

    private boolean existeListaUsuario(Lista lista) {
        Optional<Lista> listaEncontrada = repository.findByTituloAndUsuario(
                lista.getTitulo(),
                lista.getUsuario());

        if (lista.getId() == null) {
            return listaEncontrada.isPresent();
        }
        return !lista.getId().equals(listaEncontrada.get().getId()) && listaEncontrada.isPresent();
        }
    }
