package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.servico.AcaoSustentavelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/acoes")
public class AcaoSustentavelController {

    private final AcaoSustentavelService acaoService =
            new AcaoSustentavelService();

    @GetMapping
    public ResponseEntity<ArrayList<AcaoSustentavel>> listarTodas() {
        return ResponseEntity.ok(acaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoSustentavel> buscarPorId(
            @PathVariable Integer id) {

        AcaoSustentavel acao = acaoService.buscarPorId(id);

        if (acao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(acao);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody AcaoSustentavel acao) {

        acaoService.cadastrar(acao);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody AcaoSustentavel acao) {

        acao.setIdAcao(id);

        boolean atualizado = acaoService.atualizar(id, acao);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable Integer id) {

        boolean removido = acaoService.remover(id);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
