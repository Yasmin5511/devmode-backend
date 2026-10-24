package br.com.fiap.devmode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorDeExcecoes {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErroValidacao(
            IllegalArgumentException erro) {

        return ResponseEntity.badRequest().body(erro.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> tratarConflito(
            IllegalStateException erro) {

        return ResponseEntity.status(409).body(erro.getMessage());
    }

}
