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
    public List<Filme> pesquisa(String titulo, Double nota){
        if(titulo!=null && nota!=null){
            return filmeRepository.findByTituloAndNota(titulo, nota);
        }else if(titulo != null){
            return filmeRepository.findByTitulo(titulo);
        }else if(nota != null){
            return filmeRepository.findByNota(nota);
        }
        return filmeRepository.findAll();
    }

    public List<Filme> pesquisaByExample(String titulo, Double nota){
        var filme = new Filme();
        filme.setTitulo(titulo);
        filme.setNota(nota);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "sinopse", "generoFilme")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Filme> filmeExample = Example.of(filme, matcher);
        return filmeRepository.findAll(filmeExample);
    }
}
