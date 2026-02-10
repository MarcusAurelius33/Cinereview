package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ClientDTO;
import com.marcusprojetos.cinereview.controller.mappers.ClientMapper;
import com.marcusprojetos.cinereview.entities.Client;
import com.marcusprojetos.cinereview.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("clients")
public class ClientController implements GenericController {

    private final ClientMapper mapper;
    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid ClientDTO dto){
        Client client = mapper.toEntity(dto);
        service.salvar(client);

        var url = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(url).build();
    }
}
