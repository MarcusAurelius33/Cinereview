package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa.ResultadoPesquisaUsuarioDTO;
import com.marcusprojetos.cinereview.controller.dto.UsuarioDTO;
import com.marcusprojetos.cinereview.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {
    public abstract Usuario toEntity(UsuarioDTO dto);

    public abstract ResultadoPesquisaUsuarioDTO toDTO(ResultadoPesquisaUsuarioDTO resultado);


}
