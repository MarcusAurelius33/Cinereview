package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.UsuarioDTO;
import com.marcusprojetos.cinereview.controller.mappers.UsuarioMapper;
import com.marcusprojetos.cinereview.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
