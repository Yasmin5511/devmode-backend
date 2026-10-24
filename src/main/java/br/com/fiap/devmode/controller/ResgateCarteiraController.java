package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.modelo.ResgateCarteira;
import br.com.fiap.devmode.servico.ResgateCarteiraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/resgates")
public class ResgateCarteiraController {

    private final ResgateCarteiraService resgateService = new ResgateCarteiraService();

    @PostMapping
    public ResponseEntity<Void> solicitarResgate(
            @RequestParam String idResgate,
            @RequestParam String idUsuario,
            @RequestParam int pontos) {

        resgateService.solicitarResgate(idResgate, idUsuario, pontos);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResgateCarteira> buscarPorId(
            @PathVariable String id) {

        ResgateCarteira resgate = resgateService.buscarPorId(id);

        if (resgate == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resgate);
    }

    @GetMapping
    public ResponseEntity<ArrayList<ResgateCarteira>> listarTodos() {
        return ResponseEntity.ok(resgateService.listarTodos());
    }
}
