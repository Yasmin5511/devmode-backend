package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.modelo.NivelAvatar;
import br.com.fiap.devmode.servico.NivelAvatarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/niveis-avatar")
public class NivelAvatarController {

    private final NivelAvatarService nivelService =
            new NivelAvatarService();

    @GetMapping
    public ResponseEntity<ArrayList<NivelAvatar>> listarNiveis() {
        return ResponseEntity.ok(nivelService.listarNiveis());
    }

    @GetMapping("/{nivel}")
    public ResponseEntity<NivelAvatar> buscarPorId(
            @PathVariable int nivel) {

        NivelAvatar nivelAvatar = nivelService.buscarPorId(nivel);

        if (nivelAvatar == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(nivelAvatar);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody NivelAvatar nivelAvatar) {

        nivelService.cadastrar(nivelAvatar);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{nivel}")
    public ResponseEntity<Void> atualizar(
            @PathVariable int nivel,
            @RequestBody NivelAvatar nivelAvatar) {

        boolean atualizado = nivelService.atualizar(nivel, nivelAvatar);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{nivel}")
    public ResponseEntity<Void> remover(
            @PathVariable int nivel) {

        boolean removido = nivelService.remover(nivel);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
