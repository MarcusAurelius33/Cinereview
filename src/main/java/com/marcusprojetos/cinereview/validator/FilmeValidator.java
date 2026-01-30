package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FilmeValidator {
    private FilmeRepository repository;

    public FilmeValidator(FilmeRepository repository){
        this.repository = repository;
    }

    public void validar(Filme filme){
        if (existeFilme(filme)){
            throw new RegistroDuplicadoException("filme já cadastrado!");
        }
    }

    private boolean existeFilme(Filme filme){
        Optional<Filme> filmeEncontrado = repository.findByTituloAndSinopseAndGeneroFilmeAndNota(
                filme.getTitulo(),
                filme.getSinopse(),
                filme.getGeneroFilme(),
                filme.getNota());

        if (filme.getId() == null){
            return filmeEncontrado.isPresent();
        }
        return !filme.getId().equals(filmeEncontrado.get().getId()) && filmeEncontrado.isPresent();
    }
}
