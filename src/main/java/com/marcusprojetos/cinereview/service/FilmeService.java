package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import com.marcusprojetos.cinereview.validator.FilmeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final FilmeValidator validator;

    public Filme salvar(Filme filme){
        validator.validar(filme);
        return filmeRepository.save(filme);
    }

    public void atualizar(Filme filme) {
        if(filme.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o filme já tenha sido salvo!");
        }
        validator.validar(filme);
        filmeRepository.save(filme);
    }

    public Optional<Filme> obterPorId(UUID id){
        return filmeRepository.findById(id);
    }

    public void deletar(Filme filme){
        filmeRepository.delete(filme);
    }
    public List<Filme> pesquisa(String titulo, Integer anoLancamento){
        if(titulo!=null && anoLancamento!=null){
            return filmeRepository.findByTituloAndAnoLancamento(titulo, anoLancamento);
        }else if(titulo != null){
            return filmeRepository.findByTitulo(titulo);
        }else if(anoLancamento != null){
            return filmeRepository.findByAnoLancamento(anoLancamento);
        }
        return filmeRepository.findAll();
    }

    public List<Filme> pesquisaByExample(String titulo, Integer anoLancamento){
        var filme = new Filme();
        filme.setTitulo(titulo);
        filme.setAnoLancamento(anoLancamento);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "sinopse", "generoFilme")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Filme> filmeExample = Example.of(filme, matcher);
        return filmeRepository.findAll(filmeExample);
    }
}
