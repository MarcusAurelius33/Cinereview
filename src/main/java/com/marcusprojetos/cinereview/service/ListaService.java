package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.OperacaoNaopermitidaException;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.ListaRepository;
import com.marcusprojetos.cinereview.security.SecurityService;
import com.marcusprojetos.cinereview.validator.ListaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ListaService {

    private final FilmeService filmeService;
    private final ListaRepository repository;
    private final SecurityService securityService;
    private final ListaValidator validator;

    public void salvar(Lista lista) {
        Usuario usuario = securityService.obterUsuarioLogado();
        lista.setUsuario(usuario);

        validator.validarLista(lista);

        repository.save(lista);
    }

    public void deletar(Lista lista){
        repository.delete(lista);
    }

    public Optional<Lista> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void adicionarFilme(UUID idLista, UUID idFilme) {

        validator.validarAdicao(idLista, idFilme);

        Optional<Lista> lista = obterPorId(idLista);
        Optional<Filme> filme = filmeService.obterPorId(idFilme);

        lista.get().getFilmes().add(filme.get());
        lista.get().setDataModificacao(LocalDateTime.now());

        repository.save(lista.get());
    }
}
