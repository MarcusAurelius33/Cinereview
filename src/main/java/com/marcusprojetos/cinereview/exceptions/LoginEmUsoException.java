package com.marcusprojetos.cinereview.exceptions;

public class LoginEmUsoException extends RuntimeException {
    public LoginEmUsoException(String message) {
        super(message);
    }
}
