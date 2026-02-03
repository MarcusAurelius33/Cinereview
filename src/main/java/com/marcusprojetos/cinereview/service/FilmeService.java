package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import com.marcusprojetos.cinereview.repository.specs.FilmeSpecs;
import com.marcusprojetos.cinereview.validator.FilmeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    //public List<Filme> pesquisa(String titulo, Integer anoLancamento){
      //  if(titulo!=null && anoLancamento!=null){
        //    return filmeRepository.findByTituloAndAnoLancamento(titulo, anoLancamento);
        //}else if(titulo != null){
          //  return filmeRepository.findByTitulo(titulo);
        //}else if(anoLancamento != null){
         //   return filmeRepository.findByAnoLancamento(anoLancamento);
        //}
        //return filmeRepository.findAll();
    //}

    public Page<Filme> pesquisa(
            String titulo,
            GeneroFilme generoFilme,
            Integer anoLancamento,
            Integer pagina,
            Integer tamanhoPagina){

        Specification<Filme> specs = Specification.where((root, query, cb) ->
                cb.conjunction());

        if(titulo != null){
            specs = specs.and(FilmeSpecs.tituloFilmeLike(titulo));
        }

        if(generoFilme != null){
            specs = specs.and(FilmeSpecs.generoFilmeLike(generoFilme));
        }

        if(anoLancamento != null){
            specs = specs.and(FilmeSpecs.anoLancamentoFilmeEqual(anoLancamento));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return filmeRepository.findAll(specs, pageRequest);
    }
    }
