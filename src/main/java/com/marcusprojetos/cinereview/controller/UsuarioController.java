package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.UsuarioDTO;
import com.marcusprojetos.cinereview.controller.mappers.UsuarioMapper;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
@Tag(name = "Usuários")
public class UsuarioController implements GenericController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @Operation(summary = "Cadastrar", description = "Cadastrar um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado."),
            @ApiResponse(responseCode = "409", description = "Credenciais inválidas."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid UsuarioDTO dto){
        Usuario usuario = mapper.toEntity(dto);
        service.salvar(usuario);
        var url = gerarHeaderLocation(usuario.getId());
        return ResponseEntity.created(url).build();

    }
}
