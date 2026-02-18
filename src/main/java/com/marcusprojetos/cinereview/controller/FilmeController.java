package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa.ResultadoPesquisaFilmeDTO;
import com.marcusprojetos.cinereview.controller.mappers.FilmeMapper;
import com.marcusprojetos.cinereview.entities.Filme;
import com.marcusprojetos.cinereview.entities.enums.GeneroFilme;
import com.marcusprojetos.cinereview.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("filmes")
@Tag(name = "Filmes")
public class FilmeController implements GenericController {

    private final FilmeService service;
    private final FilmeMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Salvar", description = "Cadastrar novo filme")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Filme cadastrado."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Filme já cadastrado.")
    })
    public ResponseEntity<Void> salvarFilme(@RequestBody @Valid FilmeDTO dto) {
            Filme filme = mapper.toEntity(dto);
            service.salvar(filme);
            URI location = gerarHeaderLocation(filme.getId());
            return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @Operation(summary = "Pesquisa(ID)", description = "Obter os dados de um filme usando o ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme encontrado."),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado.")
    })
    public ResponseEntity<ResultadoPesquisaFilmeDTO> obterDetalhes(@PathVariable("id") String id){
        var idFilme = UUID.fromString(id);

        return service
                .obterPorId(idFilme)
                .map(filme -> {
                    var dto = mapper.toDTO(filme);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @DeleteMapping("{id}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        @Operation(summary = "Deletar", description = "Deleta um filme cadastrado")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Filme deletado."),
                @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
        })
        public ResponseEntity<Object> deletarFilme(@PathVariable("id") String id){
            var idFilme = UUID.fromString(id);
            return service.obterPorId(idFilme)
                    .map(filme -> {
                        service.deletar(filme);
                        return ResponseEntity.noContent().build();
                    }
                    ).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
        @Operation(summary = "Pesquisa", description = "Pesquisa filmes cadastrados a partir de parâmetros")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Filme encontrado."),
        })
        public ResponseEntity<Page<ResultadoPesquisaFilmeDTO>> pesquisa(
                @RequestParam(value = "titulo", required = false) String titulo,
                @RequestParam(value = "generoFilme", required = false) GeneroFilme generoFilme,
                @RequestParam(value = "anoLancamento", required = false) Integer anoLancamento,
                @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                @RequestParam(value = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina){

            Page<Filme> paginaResultado = service.pesquisa(titulo, generoFilme, anoLancamento, pagina, tamanhoPagina);

            Page<ResultadoPesquisaFilmeDTO> resultado = paginaResultado.map(mapper::toDTO);

            return ResponseEntity.ok(resultado);
        }

        @PutMapping("{id}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        @Operation(summary = "Atualizar", description = "Atualiza as informações de um filme cadastrado")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Filme atualizado."),
                @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
                @ApiResponse(responseCode = "409", description = "Filme já cadastrado."),
                @ApiResponse(responseCode = "422", description = "Erro de validação.")
        })
        public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid FilmeDTO dto){
            return service.obterPorId(UUID.fromString(id))
                    .map(filme -> {
                        Filme entidadeAux = mapper.toEntity(dto);

                        filme.setTitulo(entidadeAux.getTitulo());
                        filme.setSinopse(entidadeAux.getSinopse());
                        filme.setGeneroFilme(entidadeAux.getGeneroFilme());
                        filme.setAnoLancamento(entidadeAux.getAnoLancamento());

                        service.atualizar(filme);

                        return ResponseEntity.noContent().build();
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
}
