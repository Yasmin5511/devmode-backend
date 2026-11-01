package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.modelo.ConteudoSustentavel;
import br.com.fiap.devmode.servico.ConteudoSustentavelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/conteudos")
public class ConteudoSustentavelController {

    private final ConteudoSustentavelService conteudoService =
            new ConteudoSustentavelService();

    @GetMapping
    public ResponseEntity<ArrayList<ConteudoSustentavel>> listarTodos() {
        return ResponseEntity.ok(conteudoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoSustentavel> buscarPorId(
            @PathVariable Integer id) {

        ConteudoSustentavel conteudo = conteudoService.buscarPorId(id);

        if (conteudo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(conteudo);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody ConteudoSustentavel conteudo) {

        conteudoService.cadastrar(conteudo);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody ConteudoSustentavel conteudo) {

        conteudo.setIdConteudo(id);

        boolean atualizado = conteudoService.atualizar(id, conteudo);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable Integer id) {

        boolean removido = conteudoService.remover(id);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
