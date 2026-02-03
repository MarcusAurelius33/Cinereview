package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisaFilmeDTO;
import com.marcusprojetos.cinereview.entities.Filme;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class FilmeMapper {
    public abstract Filme toEntity(FilmeDTO dto);

    public abstract ResultadoPesquisaFilmeDTO toDTO(Filme filme);


}
