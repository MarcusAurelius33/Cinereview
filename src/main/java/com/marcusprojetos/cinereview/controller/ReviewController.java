package com.marcusprojetos.cinereview.controller;

import com.marcusprojetos.cinereview.controller.dto.ErroResposta;
import com.marcusprojetos.cinereview.controller.dto.FilmeDTO;
import com.marcusprojetos.cinereview.controller.dto.ReviewDTO;
import com.marcusprojetos.cinereview.controller.mappers.ReviewMapper;
import com.marcusprojetos.cinereview.entities.Review;
import com.marcusprojetos.cinereview.exceptions.RegistroDuplicadoException;
import com.marcusprojetos.cinereview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("reviews")
public class ReviewController implements GenericController {

    private final ReviewService service;
    private final ReviewMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid ReviewDTO dto){
        Review review = mapper.toEntity(dto);
        service.salvar(review);
        var url = gerarHeaderLocation(review.getId());
        return ResponseEntity.created(url).build();
    }
}
