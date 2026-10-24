package br.com.fiap.devmode.repositorio;

import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.Usuario;

import java.util.ArrayList;

public interface RepositorioUsuario {

    void adicionar(Usuario usuario);

    Usuario buscarPorId(String idUsuario);

    ArrayList<Usuario> obterTodosUsuarios();

    void atualizar(Usuario usuario);

    void remover(String idUsuario);

    void adicionarCategoria(String idUsuario, String idCategoria);

    ArrayList<Categoria> buscarCategorias(String idUsuario);

    void removerCategoria(String idUsuario, String idCategoria);

    void atualizarPontos(Usuario usuario);

    void desconectar();
}
