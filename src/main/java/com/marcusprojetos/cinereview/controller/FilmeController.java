package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.service.FilmeService;
import jakarta.servlet.Servlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("filmes")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvarFilme(@RequestBody FilmeDTO filme){
        Filme filmeEntidade = filme.mapearParaFilme();
        service.salvar(filmeEntidade);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filmeEntidade.getId())
                .toUri();

        System.out.println(filmeEntidade.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<FilmeDTO> obterDetalhes(@PathVariable("id") String id){
        var idFilme = UUID.fromString(id);

        Optional<Filme> filme = service.obterPorId(idFilme);
        if(filme.isPresent()){
            Filme entidade = filme.get();
            FilmeDTO dto = new FilmeDTO(entidade.getId(), entidade.getTitulo(), entidade.getSinopse(), entidade.getGeneroFilme(), entidade.getNota());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
        }

        @DeleteMapping("{id}")
        public ResponseEntity<Void> deletarFilme(@PathVariable("id") String id){
            var idFilme = UUID.fromString(id);
            Optional<Filme> filme = service.obterPorId(idFilme);

            if (filme.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            service.deletar(filme.get());

            return ResponseEntity.noContent().build();
        }

        @GetMapping
        public ResponseEntity<List<FilmeDTO>> pesquisar(
                @RequestParam(value = "titulo", required = false) String titulo,
                @RequestParam(value = "nota", required = false) Double nota){
        List<Filme> resultado = service.pesquisa(titulo, nota);
        List<FilmeDTO> lista =  resultado.stream().map(filme -> new FilmeDTO(filme.getId(),
                filme.getTitulo(),
                filme.getSinopse(),
                filme.getGeneroFilme(),
                filme.getNota())).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
        }

        @PutMapping("{id}")
        public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody FilmeDTO dto){
            var idFilme = UUID.fromString(id);
            Optional<Filme> filmeOptional = service.obterPorId(idFilme);

            if(filmeOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            var filme = filmeOptional.get();
            filme.setTitulo(dto.titulo());
            filme.setSinopse(dto.sinopse());
            filme.setGeneroFilme(dto.genero());
            filme.setNota(dto.nota());

            service.atualizar(filme);

            return ResponseEntity.noContent().build();
        }
    }
