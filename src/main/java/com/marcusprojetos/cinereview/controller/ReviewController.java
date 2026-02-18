package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa.ResultadoPesquisaReviewDTO;
import com.marcusprojetos.cinereview.controller.dto.ReviewDTO;
import com.marcusprojetos.cinereview.controller.mappers.ReviewMapper;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
@Tag(name = "Reviews")
public class ReviewController implements GenericController {

    private final ReviewService service;
    private final ReviewMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Cadastrar", description = "Cadastrar novo review")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review cadastrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "O usuário já possui um review desse filme!")
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid ReviewDTO dto){
        Review review = mapper.toEntity(dto);
        service.salvar(review);
        var url = gerarHeaderLocation(review.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Pesquisa(ID)", description = "Obter os dados de um review usando o ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review encontrado."),
            @ApiResponse(responseCode = "404", description = "Review não encontrada.")
    })
    public ResponseEntity<ResultadoPesquisaReviewDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(review -> {
                var dto = mapper.toDTO(review);
                return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta um review cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deletado."),
            @ApiResponse(responseCode = "400", description = "Você não tem permissão para excluir esta review."),
            @ApiResponse(responseCode = "400", description = "Review não encontrado.")
    })
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(review -> {
                    service.deletar(review);
                    return ResponseEntity.noContent().build();
                    }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Pesquisa", description = "Pesquisa reviews cadastrados a partir de parâmetros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review encontrado."),
    })
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
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Atualizar", description = "Atualiza as informações de um review cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review atualizado."),
            @ApiResponse(responseCode = "400", description = "Você não tem permissão para alterar esta review."),
            @ApiResponse(responseCode = "404", description = "Review não encontrado."),
    })
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
