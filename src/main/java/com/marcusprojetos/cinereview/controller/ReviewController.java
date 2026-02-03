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
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<ResultadoPesquisaReviewDTO>> pesquisa(
            @RequestParam(value = "titulo-filme", required = false)
            String nomeFilme,
            @RequestParam(value = "nota-review", required = false)
            BigDecimal nota,
            @RequestParam(value = "ano-review", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
            )
    {
        Page<Review> paginaResultado = service.pesquisa(nomeFilme, nota, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoPesquisaReviewDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable ("id") String id, @RequestBody @Valid ReviewDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(review -> {
                    Review entidadeAux = mapper.toEntity(dto);

                    review.setTexto(entidadeAux.getTexto());
                    review.setNota(entidadeAux.getNota());

                    service.atualizar(review);

                    return ResponseEntity.noContent().build();

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
