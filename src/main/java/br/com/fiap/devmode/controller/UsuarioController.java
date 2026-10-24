package br.com.fiap.devmode.controller;

import br.com.fiap.devmode.dao.UsuarioDAO;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.Usuario;

import br.com.fiap.devmode.modelo.UsuarioAcaoStatus;
import br.com.fiap.devmode.modelo.UsuarioAvatar;
import br.com.fiap.devmode.servico.RecomendacaoService;
import br.com.fiap.devmode.servico.UsuarioAcaoStatusService;
import br.com.fiap.devmode.servico.UsuarioAvatarService;
import br.com.fiap.devmode.servico.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable String id) {

            Usuario usuario = usuarioService.buscarPorId(id);

            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Usuario>> listarTodos() {

            ArrayList<Usuario> usuarios = usuarioService.listarTodos();

            return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Usuario usuario) {

        usuarioService.cadastrar(usuario);

        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id,
            @RequestBody Usuario usuario) {

        boolean atualizado = usuarioService.atualizar(id, usuario);

        if (!atualizado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable String id) {

        boolean removido = usuarioService.remover(id);

        if (!removido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/categorias/{idCategoria}")
    public ResponseEntity<Void> adicionarCategoria(
            @PathVariable String id,
            @PathVariable String idCategoria) {

        usuarioService.adicionarCategoria(id, idCategoria);

        return ResponseEntity.status(201).build();
    }


    @GetMapping("/{id}/categorias")
    public ResponseEntity<ArrayList<Categoria>> buscarCategorias(
            @PathVariable String id) {

        return ResponseEntity.ok(usuarioService.buscarCategorias(id));
    }


    @DeleteMapping("/{id}/categorias/{idCategoria}")
    public ResponseEntity<Void> removerCategoria(
            @PathVariable String id,
            @PathVariable String idCategoria) {

        usuarioService.removerCategoria(id, idCategoria);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recomendacoes")
    public ResponseEntity<Map<String, Object>> recomendar(@PathVariable String id) {

        RecomendacaoService recomendacaoService = new RecomendacaoService();
        Map<String, Object> recomendacoes = recomendacaoService.recomendar(id);

        if (recomendacoes == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recomendacoes);
    }


    @PostMapping("/{id}/missoes/{idAcao}")
    public ResponseEntity<Void> iniciarMissao(
            @PathVariable String id,
            @PathVariable String idAcao,
            @RequestParam int progressoTotal) {

        UsuarioAcaoStatusService missaoService = new UsuarioAcaoStatusService();

        missaoService.iniciarMissao(id, idAcao, progressoTotal);

        return ResponseEntity.status(201).build();
    }


    @GetMapping("/{id}/missoes/{idAcao}")
    public ResponseEntity<UsuarioAcaoStatus> buscarMissao(
            @PathVariable String id,
            @PathVariable String idAcao) {

        UsuarioAcaoStatusService missaoService = new UsuarioAcaoStatusService();

        UsuarioAcaoStatus missao = missaoService.buscarMissao(id, idAcao);

        if (missao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missao);
    }


    @PatchMapping("/{id}/missoes/{idAcao}/progresso")
    public ResponseEntity<Void> atualizarProgresso(
            @PathVariable String id,
            @PathVariable String idAcao,
            @RequestParam int quantidade) {

        UsuarioAcaoStatusService missaoService = new UsuarioAcaoStatusService();

        missaoService.atualizarProgresso(id, idAcao, quantidade);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/missoes")
    public ResponseEntity<List<UsuarioAcaoStatus>> listarMissoes(
            @PathVariable String id) {

        UsuarioAcaoStatusService missaoService = new UsuarioAcaoStatusService();
        List<UsuarioAcaoStatus> missoes = missaoService.listarMissoes(id);

        return ResponseEntity.ok(missoes);
    }


    @GetMapping("/{id}/avatar")
    public ResponseEntity<UsuarioAvatar> buscarAvatar(
            @PathVariable String id) {

        UsuarioAvatarService avatarService = new UsuarioAvatarService();

        UsuarioAvatar avatar = avatarService.buscarPorUsuario(id);

        if (avatar == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(avatar);
    }


    @PostMapping("/{id}/avatar")
    public ResponseEntity<Void> criarAvatar(
            @PathVariable String id) {

        UsuarioAvatarService avatarService = new UsuarioAvatarService();
        avatarService.criarAvatar(id);

        return ResponseEntity.status(201).build();
    }


    @PatchMapping("/{id}/avatar/nivel")
    public ResponseEntity<Void> atualizarNivelAvatar(
            @PathVariable String id) {

        UsuarioAvatarService avatarService = new UsuarioAvatarService();

        avatarService.atualizarNivel(id);

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/avatar/humor")
    public ResponseEntity<Void> atualizarHumorAvatar(
            @PathVariable String id,
            @RequestBody Map<String, String> dados) {

        UsuarioAvatarService avatarService = new UsuarioAvatarService();

        avatarService.atualizarHumor(id, dados.get("humor"));

        return ResponseEntity.noContent().build();
    }



}
