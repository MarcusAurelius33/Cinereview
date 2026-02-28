package com.marcusprojetos.cinereview.controller.mappers;

import com.marcusprojetos.cinereview.controller.dto.ListaDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoDetalhes.ListaDetalhesDTO;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisa.ResultadoPesquisaListaDTO;
import com.marcusprojetos.cinereview.entities.Lista;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = FilmeMapper.class)
public abstract class ListaMapper {
    @Autowired
    FilmeRepository filmeRepository;


    @Mapping(target = "filmes", expression = "java( dto.id_filmes() == null ? new java.util.ArrayList<>() : filmeRepository.findAllById(dto.id_filmes()) )")
    public abstract Lista toEntity(ListaDTO dto);

    @Mapping(target = "nomeUsuario", source = "usuario.login")
    public abstract ResultadoPesquisaListaDTO toDTO(Lista lista);

    @Mapping(target = "nomeUsuario", source = "usuario.login")
    public abstract ListaDetalhesDTO toDetalhesDTO(Lista lista);
    }
