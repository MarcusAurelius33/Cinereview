package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ListaDTO;
import com.marcusprojetos.cinereview.controller.mappers.ListaMapper;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.service.ListaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("listas")
public class ListaController implements GenericController {

    private final ListaService service;
    private final ListaMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Object> criar(@RequestBody @Valid ListaDTO dto){
        Lista lista = mapper.toEntity(dto);
        service.salvar(lista);
        var url = gerarHeaderLocation(lista.getId());
        return ResponseEntity.created(url).build();
    }

}
