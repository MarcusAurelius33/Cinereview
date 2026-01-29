package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository){
        this.filmeRepository = filmeRepository;
    }

    public Filme salvar(Filme filme){
        return filmeRepository.save(filme);
    }

    public void atualizar(Filme filme) {
        if(filme.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o filme já tenha sido salvo");
        }
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
}
