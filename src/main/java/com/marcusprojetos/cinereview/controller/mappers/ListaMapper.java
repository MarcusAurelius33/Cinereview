package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.ListaDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa.ResultadoPesquisaListaDTO;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import com.marcusprojetos.cinereview.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ListaMapper {
    @Autowired
    FilmeRepository filmeRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Mapping(target = "filmes", expression = "java( filmeRepository.findAllById(dto.id_filmes()) )")
    public abstract Lista toEntity(ListaDTO dto);


    //public abstract ResultadoPesquisaListaDTO toDTO(Lista lista);


}
