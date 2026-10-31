package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAO {
    private Connection conexao;

    public CategoriaDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(Categoria categoria) {
        try {
            String sqlInsert = "INSERT INTO TB_CATEGORIAS (id_categoria, categoria) VALUES (?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setString(1, categoria.getIdCategoria());
            comandoDeInsercao.setString(2, categoria.getCategoria());
            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Categoria buscarPorId(String idCategoria) {

        Categoria categoria = null;

        try {
            String sqlSelect = "SELECT * FROM TB_CATEGORIAS WHERE id_categoria = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setString(1, idCategoria);

            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rs.getString("id_categoria"));
                categoria.setCategoria(rs.getString("categoria"));
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoria;
    }

    public ArrayList<Categoria> obterTodasCategorias() {

        ArrayList<Categoria> categorias = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_CATEGORIAS";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
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

    public void atualizar(Categoria categoria) {

        try {
            String sqlUpdate = "UPDATE TB_CATEGORIAS SET categoria = ? WHERE id_categoria = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);

            comandoDeAtualizacao.setString(1, categoria.getCategoria());
            comandoDeAtualizacao.setString(2, categoria.getIdCategoria());

            int linhasAtualizadas = comandoDeAtualizacao.executeUpdate();

            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(String idCategoria) {
        try {
            String sqlDelete = "DELETE FROM TB_CATEGORIAS WHERE id_categoria = ?";

            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setString(1, idCategoria);
            comandoDeRemocao.execute();
            comandoDeRemocao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
