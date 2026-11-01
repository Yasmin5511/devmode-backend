package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.ConteudoSustentavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConteudoSustentavelDAO {
    private Connection conexao;

    public ConteudoSustentavelDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(ConteudoSustentavel conteudo) {
        try {
            String sqlInsert = "INSERT INTO TB_CONTEUDOS_SUSTENTAVEIS (id_conteudo, tb_categorias_id_categoria, titulo, conteudo, conteudo_avatar, link_redirecionamento, pontos_ganhos) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setInt(1, conteudo.getIdConteudo());
            comandoDeInsercao.setInt(2, conteudo.getCategoria().getIdCategoria());
            comandoDeInsercao.setString(3, conteudo.getTitulo());
            comandoDeInsercao.setString(4, conteudo.getConteudo());
            comandoDeInsercao.setString(5, conteudo.getConteudoAvatar());
            comandoDeInsercao.setString(6, conteudo.getLinkRedirecionamento());
            comandoDeInsercao.setInt(7, conteudo.getPontosGanhos());

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ConteudoSustentavel buscarPorId(Integer idConteudo) {

        ConteudoSustentavel conteudo = null;

        try {
            String sqlSelect = "SELECT * FROM TB_CONTEUDOS_SUSTENTAVEIS WHERE id_conteudo = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setInt(1, idConteudo);
            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("tb_categorias_id_categoria"));

                conteudo = new ConteudoSustentavel();

                conteudo.setIdConteudo(rs.getInt("id_conteudo"));
                conteudo.setCategoria(categoria);
                conteudo.setTitulo(rs.getString("titulo"));
                conteudo.setConteudo(rs.getString("conteudo"));
                conteudo.setConteudoAvatar(rs.getString("conteudo_avatar"));
                conteudo.setLinkRedirecionamento(rs.getString("link_redirecionamento"));
                conteudo.setPontosGanhos(rs.getInt("pontos_ganhos"));
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conteudo;
    }

    public ArrayList<ConteudoSustentavel> obterTodosConteudos() {

        ArrayList<ConteudoSustentavel> conteudos = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_CONTEUDOS_SUSTENTAVEIS";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("tb_categorias_id_categoria"));

                ConteudoSustentavel conteudo = new ConteudoSustentavel();

                conteudo.setIdConteudo(rs.getInt("id_conteudo"));
                conteudo.setCategoria(categoria);
                conteudo.setTitulo(rs.getString("titulo"));
                conteudo.setConteudo(rs.getString("conteudo"));
                conteudo.setConteudoAvatar(rs.getString("conteudo_avatar"));
                conteudo.setLinkRedirecionamento(rs.getString("link_redirecionamento"));
                conteudo.setPontosGanhos(rs.getInt("pontos_ganhos"));

                conteudos.add(conteudo);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conteudos;
    }

    public void atualizar(ConteudoSustentavel conteudo) {
        try {
            String sqlUpdate = "UPDATE TB_CONTEUDOS_SUSTENTAVEIS SET tb_categorias_id_categoria = ?, titulo = ?, conteudo = ?, conteudo_avatar = ?, link_redirecionamento = ?, pontos_ganhos = ? WHERE id_conteudo = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);

            comandoDeAtualizacao.setInt(1, conteudo.getCategoria().getIdCategoria());
            comandoDeAtualizacao.setString(2, conteudo.getTitulo());
            comandoDeAtualizacao.setString(3, conteudo.getConteudo());
            comandoDeAtualizacao.setString(4, conteudo.getConteudoAvatar());
            comandoDeAtualizacao.setString(5, conteudo.getLinkRedirecionamento());
            comandoDeAtualizacao.setInt(6, conteudo.getPontosGanhos());
            comandoDeAtualizacao.setInt(7, conteudo.getIdConteudo());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(Integer idConteudo) {
        try {
            String sqlDelete = "DELETE FROM TB_CONTEUDOS_SUSTENTAVEIS WHERE id_conteudo = ?";
            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setInt(1, idConteudo);

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
