package com.marcusprojetos.cinereview.controller.mappers;

import ch.qos.logback.core.model.ComponentModel;
import com.marcusprojetos.cinereview.controller.dto.ResultadoPesquisaReviewDTO;
import com.marcusprojetos.cinereview.controller.dto.ReviewDTO;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import com.marcusprojetos.cinereview.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = FilmeMapper.class)
public abstract class ReviewMapper {

    @Autowired
    FilmeRepository filmeRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Mapping(target = "filme", expression = "java( filmeRepository.findById(dto.id_filme()).orElse(null) )")
    public abstract Review toEntity(ReviewDTO dto);

    @Mapping(target = "nomeUsuario", source = "usuario.login")
    public abstract ResultadoPesquisaReviewDTO toDTO(Review review);
}
