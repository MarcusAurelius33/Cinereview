package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.entities.Filme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmeMapper {
    Filme toEntity(FilmeDTO dto);

    FilmeDTO toDTO(Filme filme);


}
