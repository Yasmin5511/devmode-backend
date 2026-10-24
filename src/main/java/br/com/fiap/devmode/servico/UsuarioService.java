package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.UsuarioDAO;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.repositorio.RepositorioUsuario;

import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioService {

    public Usuario buscarPorId(String id) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            return dao.buscarPorId(id);
        } finally {
            dao.desconectar();
        }
    }

    public ArrayList<Usuario> listarTodos() {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            return dao.obterTodosUsuarios();
        } finally {
            dao.desconectar();
        }
    }

    public void cadastrar(Usuario usuario) {
        validarUsuario(usuario);

        if (usuario.getIdUsuario() == null ||
                usuario.getIdUsuario().isBlank()) {
            throw new IllegalArgumentException(
                    "O ID do usuário é obrigatório."
            );
        }

        if (usuario.getDataCriacao() == null) {
            usuario.setDataCriacao(LocalDate.now());
        }

        UsuarioDAO dao = new UsuarioDAO();

        try {
            dao.adicionar(usuario);
        } finally {
            dao.desconectar();
        }

        UsuarioAvatarService avatarService = new UsuarioAvatarService();
        avatarService.criarAvatar(usuario.getIdUsuario());
    }

    public boolean atualizar(String id, Usuario usuario) {
        validarUsuario(usuario);

        UsuarioDAO dao = new UsuarioDAO();

        try {
            Usuario usuarioExistente = dao.buscarPorId(id);

            if (usuarioExistente == null) {
                return false;
            }

            usuario.setIdUsuario(id);
            dao.atualizar(usuario);

            return true;
        } finally {
            dao.desconectar();
        }
    }

    public boolean remover(String id) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            Usuario usuarioExistente = dao.buscarPorId(id);

            if (usuarioExistente == null) {
                return false;
            }

            dao.remover(id);
            return true;
        } finally {
            dao.desconectar();
        }
    }

    public void adicionarCategoria(String idUsuario, String idCategoria) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            dao.adicionarCategoria(idUsuario, idCategoria);
        } finally {
            dao.desconectar();
        }
    }

    public ArrayList<Categoria> buscarCategorias(String idUsuario) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            return dao.buscarCategorias(idUsuario);
        } finally {
            dao.desconectar();
        }
    }

    public void removerCategoria(String idUsuario, String idCategoria) {
        UsuarioDAO dao = new UsuarioDAO();

        try {
            dao.removerCategoria(idUsuario, idCategoria);
        } finally {
            dao.desconectar();
        }
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Usuário não informado."
            );
        }

        if (usuario.getNome() == null ||
                usuario.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome é obrigatório."
            );
        }

        if (usuario.getSobrenome() == null ||
                usuario.getSobrenome().isBlank()) {
            throw new IllegalArgumentException(
                    "O sobrenome é obrigatório."
            );
        }

        if (usuario.getCpf() == null ||
                !usuario.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException(
                    "O CPF deve conter 11 números."
            );
        }

        if (usuario.getEmail() == null ||
                usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }
    }
}