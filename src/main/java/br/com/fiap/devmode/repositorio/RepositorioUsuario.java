package br.com.fiap.devmode.repositorio;

import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.Usuario;

import java.util.ArrayList;

public interface RepositorioUsuario {

    void adicionar(Usuario usuario);

    Usuario buscarPorId(Integer idUsuario);

    ArrayList<Usuario> obterTodosUsuarios();

    void atualizar(Usuario usuario);

    void remover(Integer idUsuario);

    void adicionarCategoria(Integer idUsuario, Integer idCategoria);

    ArrayList<Categoria> buscarCategorias(Integer idUsuario);

    void removerCategoria(Integer idUsuario, Integer idCategoria);

    void atualizarPontos(Usuario usuario);

    void desconectar();
}
