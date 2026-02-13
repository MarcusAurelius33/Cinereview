package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.UsuarioDTO;
import com.marcusprojetos.cinereview.controller.mappers.UsuarioMapper;
import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController implements GenericController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid UsuarioDTO dto){
        Usuario usuario = mapper.toEntity(dto);
        service.salvar(usuario);
        var url = gerarHeaderLocation(usuario.getId());
        return ResponseEntity.created(url).build();

    }
}
