package com.marcusprojetos.cinereview.controller.mappers;

import ch.qos.logback.core.model.ComponentModel;
import com.marcusprojetos.cinereview.controller.dto.ReviewDTO;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.repository.FilmeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ReviewMapper {

    @Autowired
    FilmeRepository filmeRepository;

    @Mapping(target = "filme", expression = "java( filmeRepository.findById(dto.id_filme()).orElse(null) )")
    public abstract Review toEntity(ReviewDTO dto);
}
