package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ListaDTO;
import com.marcusprojetos.cinereview.controller.mappers.ListaMapper;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.service.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("listas")
@Tag(name = "Listas")
public class ListaController implements GenericController {

    private final ListaService service;
    private final ListaMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Criar", description = "Criar uma lista de filmes cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lista criada."),
            @ApiResponse(responseCode = "409", description = "O usuário já possui uma lista com esse nome!"),
            @ApiResponse(responseCode = "422", description = "Erro de validação!"),
    })
    public ResponseEntity<Object> criar(@RequestBody @Valid ListaDTO dto){
        Lista lista = mapper.toEntity(dto);
        service.salvar(lista);
        var url = gerarHeaderLocation(lista.getId());
        return ResponseEntity.created(url).build();
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Deletar", description = "Deleta uma lista de filmes")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lista deletada."),
            @ApiResponse(responseCode = "404", description = "Lista não encontrada!"),
    })
    public ResponseEntity<Object> deletar(@RequestParam ("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(lista -> {
                    service.deletar(lista);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{idLista}/filmes/{idFilme}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Adicionar Filme", description = "Adiciona um filme específico a uma lista existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Filme adicionado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Lista ou Filme não encontrado."),
            @ApiResponse(responseCode = "400", description = "Operação não permitida."),
            @ApiResponse(responseCode = "409", description = "Filme já existe na lista.")
    })
    public ResponseEntity<Object> adicionarFilme(
            @PathVariable("idLista") String idLista,
            @PathVariable("idFilme") String idFilme)
    {
        service.adicionarFilme(UUID.fromString(idLista), UUID.fromString(idFilme));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{idLista}/filmes/{idFilme}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Excluir Filme", description = "Exclui um filme específico de uma lista existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Filme excluido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Lista ou Filme não encontrado."),
            @ApiResponse(responseCode = "400", description = "Operação não permitida."),
            @ApiResponse(responseCode = "409", description = "Filme não existe na lista.")
    })
    public ResponseEntity<Object> excluirFilme(
            @PathVariable("idLista") String idLista,
            @PathVariable("idFilme") String idFilme)
    {
        service.excluirFilme(UUID.fromString(idLista), UUID.fromString(idFilme));
        return ResponseEntity.noContent().build();
    }
}
