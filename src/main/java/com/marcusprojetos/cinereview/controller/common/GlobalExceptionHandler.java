package com.marcusprojetos.cinereview.controller.common;

import com.marcusprojetos.cinereview.controller.dto.ErroCampo;
import com.marcusprojetos.cinereview.controller.dto.ErroResposta;
import com.marcusprojetos.cinereview.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaerros = fieldErrors.stream()
                .map(fe -> new ErroCampo(fe
                        .getField(), fe
                        .getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_CONTENT.value(), "Erro de validação", listaerros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoExcepetion(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaopermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaopermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(LoginEmUsoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleLoginEmUsoException(LoginEmUsoException e) {
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAcessDeniedException(AccessDeniedException e) {
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "Acesso negado!", List.of());
    }

    @ExceptionHandler(FonteNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleFonteNaoEcontradaException(FonteNaoEncontradaException e) {
        return new ErroResposta(HttpStatus.NOT_FOUND.value(),e.getMessage(), List.of());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado, entre em contato com a administração.", List.of());
    }
}
