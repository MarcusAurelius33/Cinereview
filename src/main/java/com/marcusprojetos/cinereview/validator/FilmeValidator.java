package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.exceptions.CampoInvalidoException;
import com.marcusprojetos.cinereview.exceptions.FonteNaoEncontradaException;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilmeValidator {

    private final FilmeRepository repository;

    public void validar(Filme filme){
        if (existeFilme(filme)){
            throw new RegistroDuplicadoException("filme já cadastrado!");
        }

        if (isAnoLancamentoFuturo(filme)) {
            throw new CampoInvalidoException("anoLancamento", "Filmes devem ter data de lançamento inferior a data de cadastro.");
        }
    }

    public Filme validarExclusao(Filme filme){
        Filme filmeAux = repository.findById(filme.getId())
                .orElseThrow(() -> new FonteNaoEncontradaException("Filme não encontrado!"));
        return filmeAux;
    }


    private boolean isAnoLancamentoFuturo(Filme filme) {
        if (filme.getAnoLancamento() == null) {
            return false;
        }

        Integer anoReferencia = (filme.getDataCadastro() != null)
                ? filme.getDataCadastro().getYear()
                : LocalDate.now().getYear();

        return filme.getAnoLancamento() > anoReferencia;
    }

    private boolean existeFilme(Filme filme){
        Optional<Filme> filmeEncontrado = repository.findByTituloAndSinopseAndGeneroFilmeAndAnoLancamento(
                filme.getTitulo(),
                filme.getSinopse(),
                filme.getGeneroFilme(),
                filme.getAnoLancamento());

        if (filme.getId() == null){
            return filmeEncontrado.isPresent();
        }
        return !filme.getId().equals(filmeEncontrado.get().getId()) && filmeEncontrado.isPresent();
    }

    public void validarAtualizacao(Filme filme){
        Optional<Filme> filmeEncontrado = repository.findByTituloAndSinopseAndGeneroFilmeAndAnoLancamento(
                filme.getTitulo(),
                filme.getSinopse(),
                filme.getGeneroFilme(),
                filme.getAnoLancamento());
        if (filmeEncontrado.isPresent()){
            throw new RegistroDuplicadoException("filme já cadastrado!");
        }

        if(isAnoLancamentoFuturo(filme)){
            throw new CampoInvalidoException("anoLancamento", "Filmes devem ter data de lançamento inferior a data de atualização.");
        }
    }

}
