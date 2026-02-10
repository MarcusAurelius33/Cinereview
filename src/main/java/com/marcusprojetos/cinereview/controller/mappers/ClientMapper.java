package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.ClientDTO;
import com.marcusprojetos.cinereview.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {
    public abstract Client toEntity(ClientDTO clientDTO);

}
