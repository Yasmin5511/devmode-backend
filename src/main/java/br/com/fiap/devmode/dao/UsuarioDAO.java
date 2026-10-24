package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.repositorio.RepositorioUsuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO implements RepositorioUsuario {
    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    @Override
    public void adicionar(Usuario usuario) {
        try {
            String sqlInsert = "INSERT INTO TB_USUARIO (id_usuario, cpf, email, nome, sobrenome, total_pontos_sustentaveis, dt_criacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setString(1, usuario.getIdUsuario());
            comandoDeInsercao.setString(2, usuario.getCpf());
            comandoDeInsercao.setString(3, usuario.getEmail());
            comandoDeInsercao.setString(4, usuario.getNome());
            comandoDeInsercao.setString(5, usuario.getSobrenome());
            comandoDeInsercao.setInt(6, usuario.getTotalPontosSustentaveis());
            comandoDeInsercao.setDate(7, Date.valueOf(usuario.getDataCriacao()));

            comandoDeInsercao.execute();
            comandoDeInsercao.close();


        } catch (SQLException e) {

            if (e.getErrorCode() == 1) {
                throw new IllegalStateException(
                        "Já existe um usuário com esses dados.", e);
            }

            throw new RuntimeException(e);
        }

    }

    @Override
    public void remover(String idUsuario) {
        try {
            conexao.setAutoCommit(false);

            String[] comandos = {
                    """
            DELETE FROM TB_POSTAGEM_HASHTAG
            WHERE tb_postagem_id_postagem IN (
                SELECT id_postagem FROM TB_POSTAGEM
                WHERE tb_usuario_id_usuario = ?
            )
            """,
                    "DELETE FROM TB_POSTAGEM WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_USUARIO_AVATAR WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_USUARIO_ACAO_STATUS WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_USUARIO_CATEGORIA WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_USUARIO_COMUNIDADE WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_HISTORICO_ACAO WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_HISTORICO_CONTEUDO WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_REGISTROS_AVATAR WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_RESGATE_CARTEIRA WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_LOGS_ACESSO WHERE tb_usuario_id_usuario = ?",
                    "DELETE FROM TB_USUARIO WHERE id_usuario = ?"
            };

            for (String sql : comandos) {
                try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                    comando.setString(1, idUsuario);
                    comando.executeUpdate();
                }
            }

            conexao.commit();

        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException erroRollback) {
                e.addSuppressed(erroRollback);
            }

            throw new RuntimeException(e);

        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Usuario buscarPorId(String idUsuario) {

        Usuario usuario = null;

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO WHERE id_usuario = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setString(1, idUsuario);

            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setTotalPontosSustentaveis(rs.getInt("total_pontos_sustentaveis"));
                usuario.setDataCriacao(rs.getDate("dt_criacao").toLocalDate());
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    @Override
    public ArrayList<Usuario> obterTodosUsuarios() {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("id_usuario"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSobrenome(rs.getString("sobrenome"));
                usuario.setTotalPontosSustentaveis(rs.getInt("total_pontos_sustentaveis"));
                usuario.setDataCriacao(rs.getDate("dt_criacao").toLocalDate());

                usuarios.add(usuario);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    @Override
    public void atualizar(Usuario usuario) {
        try {
            String sqlUpdate = "UPDATE TB_USUARIO SET cpf = ?, email = ?, nome = ?, sobrenome = ?, total_pontos_sustentaveis = ? WHERE id_usuario = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);

            comandoDeAtualizacao.setString(1, usuario.getCpf());
            comandoDeAtualizacao.setString(2, usuario.getEmail());
            comandoDeAtualizacao.setString(3, usuario.getNome());
            comandoDeAtualizacao.setString(4, usuario.getSobrenome());
            comandoDeAtualizacao.setInt(5, usuario.getTotalPontosSustentaveis());
            comandoDeAtualizacao.setString(6, usuario.getIdUsuario());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void desconectar() {
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void adicionarCategoria(String idUsuario, String idCategoria) {
        try {
            String sqlInsert = "INSERT INTO TB_USUARIO_CATEGORIA (tb_usuario_id_usuario, tb_categorias_id_categoria, preferencia, dt_selecao) VALUES (?, ?, ?, SYSDATE)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setString(1, idUsuario);
            comandoDeInsercao.setString(2, idCategoria);
            comandoDeInsercao.setString(3, "S");

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removerCategoria(String idUsuario, String idCategoria) {
        try {
            String sqlDelete = "DELETE FROM TB_USUARIO_CATEGORIA WHERE tb_usuario_id_usuario = ? AND tb_categorias_id_categoria = ?";

            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);

            comandoDeRemocao.setString(1, idUsuario);
            comandoDeRemocao.setString(2, idCategoria);

            comandoDeRemocao.execute();
            comandoDeRemocao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Categoria> buscarCategorias(String idUsuario) {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            String sqlSelect = """
                SELECT c.id_categoria, c.categoria
                FROM TB_CATEGORIAS c
                INNER JOIN TB_USUARIO_CATEGORIA uc
                    ON c.id_categoria = uc.tb_categorias_id_categoria
                WHERE uc.tb_usuario_id_usuario = ?
                """;

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);

            comandoDeSelecao.setString(1, idUsuario);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getString("id_categoria"));
                categoria.setCategoria(rs.getString("categoria"));
                categorias.add(categoria);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categorias;
    }


    @Override
    public void atualizarPontos(Usuario usuario) {
        String sql = """
            UPDATE TB_USUARIO
            SET total_pontos_sustentaveis = ?
            WHERE id_usuario = ?
            """;

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, usuario.getTotalPontosSustentaveis());
            comando.setString(2, usuario.getIdUsuario());
            comando.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
