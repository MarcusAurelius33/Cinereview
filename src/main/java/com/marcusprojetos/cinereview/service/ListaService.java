package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.repository.ListaRepository;
import com.marcusprojetos.cinereview.repository.specs.ListaSpecs;
import com.marcusprojetos.cinereview.security.SecurityService;
import com.marcusprojetos.cinereview.validator.ListaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ListaService {

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
        validator.validarExclusaoLista(lista);
        repository.delete(lista);
    }


    public Optional<Lista> findById(UUID id){
        return repository.findById(id);
    }


    public Lista obterDetalhes(UUID id){
       return validator.validarObterDetalhes(id);
    }


    public void adicionarFilme(UUID idLista, UUID idFilme) {
       Lista lista = validator.buscarEValidarPosse(idLista);
       Filme filme = validator.buscarEValidarFilme(idFilme);

       validator.filmePresente(lista, filme);

       lista.getFilmes().add(filme);
       lista.setDataModificacao(LocalDateTime.now());
        repository.save(lista);
    }


    public void excluirFilme(UUID idLista, UUID idFilme) {
        Lista lista = validator.buscarEValidarPosse(idLista);
        Filme filme = validator.buscarEValidarFilme(idFilme);

        validator.filmeNaoPresente(lista, filme);

        lista.getFilmes().remove(filme);
        lista.setDataModificacao(LocalDateTime.now());
        repository.save(lista);
    }

    public Page<Lista> pesquisa(
            String titulo,
            String nomeUsuario,
            Integer pagina,
            Integer tamanhoPagina){

        Specification<Lista> specs = Specification.where((root, query, cb) ->
                cb.conjunction());

        if(titulo != null){
            specs = specs.and(ListaSpecs.tituloListaLike(titulo));
        }

        if(nomeUsuario != null){
            specs = specs.and(ListaSpecs.nomeUsuarioListaLike(nomeUsuario));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }
}
