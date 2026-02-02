package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ErroResposta;
import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisaReviewDTO;
import com.marcusprojetos.cinereview.controller.dto.ReviewDTO;
import com.marcusprojetos.cinereview.controller.mappers.ReviewMapper;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController implements GenericController {

    private final ReviewService service;
    private final ReviewMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid ReviewDTO dto){
        Review review = mapper.toEntity(dto);
        service.salvar(review);
        var url = gerarHeaderLocation(review.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaReviewDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(review -> {
                var dto = mapper.toDTO(review);
                return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(review -> {
                    service.deletar(review);
                    return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaReviewDTO>> pesquisa(
            @RequestParam(value = "titulo-filme", required = false)
            String nomeFilme,
            @RequestParam(value = "nota-review", required = false)
            BigDecimal nota,
            @RequestParam(value = "ano-review", required = false)
            Integer anoPublicacao
            )
    {
        var resultado = service.pesquisa(nomeFilme, nota, anoPublicacao);
        var lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
