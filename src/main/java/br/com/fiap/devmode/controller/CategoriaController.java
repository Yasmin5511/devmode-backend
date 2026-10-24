package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.servico.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService =
            new CategoriaService();

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(
            @PathVariable String id) {

        Categoria categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Categoria>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody Categoria categoria) {

        categoriaService.cadastrar(categoria);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id,
            @RequestBody Categoria categoria) {

        categoria.setIdCategoria(id);

        boolean atualizado = categoriaService.atualizar(id, categoria);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable String id) {

        boolean removido = categoriaService.remover(id);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
