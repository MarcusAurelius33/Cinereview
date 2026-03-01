package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ClientDTO;
import com.marcusprojetos.cinereview.controller.mappers.ClientMapper;
import com.marcusprojetos.cinereview.entities.Client;
import com.marcusprojetos.cinereview.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("clients")
@Slf4j
public class ClientController implements GenericController {

    private final ClientMapper mapper;
    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Cadastrar", description = "Cadastrar Client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client cadastrado"),
    })
    public ResponseEntity<Object> salvar(@RequestBody @Valid ClientDTO dto){
        log.info("Registrando novo Client: {} com scope: {}", dto.clientId(), dto.scope());

        Client client = mapper.toEntity(dto);
        service.salvar(client);

        var url = gerarHeaderLocation(client.getId());
        return ResponseEntity.created(url).build();
    }
}
