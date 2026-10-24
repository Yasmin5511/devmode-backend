package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AcaoSustentavelDAO {
    private Connection conexao;

    public AcaoSustentavelDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(AcaoSustentavel acao) {
        try {
            String sqlInsert = "INSERT INTO TB_ACOES_SUSTENTAVEIS (id_acao, tb_categorias_id_categoria, titulo, descricao, conteudo_avatar, pontos_ganhos, co2_evitado_kg, nivel_dificuldade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setString(1, acao.getIdAcao());
            comandoDeInsercao.setString(2, acao.getCategoria().getIdCategoria());
            comandoDeInsercao.setString(3, acao.getTitulo());
            comandoDeInsercao.setString(4, acao.getDescricao());
            comandoDeInsercao.setString(5, acao.getConteudoAvatar());
            comandoDeInsercao.setInt(6, acao.getPontosGanhos());
            comandoDeInsercao.setDouble(7, acao.getCo2EvitadoKg());
            comandoDeInsercao.setString(8, acao.getNivelDificuldade());

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AcaoSustentavel buscarPorId(String idAcao) {

        AcaoSustentavel acao = null;

        try {
            String sqlSelect = "SELECT * FROM TB_ACOES_SUSTENTAVEIS WHERE id_acao = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setString(1, idAcao);
            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getString("tb_categorias_id_categoria"));

                acao = new AcaoSustentavel();

                acao.setIdAcao(rs.getString("id_acao"));
                acao.setCategoria(categoria);
                acao.setTitulo(rs.getString("titulo"));
                acao.setDescricao(rs.getString("descricao"));
                acao.setConteudoAvatar(rs.getString("conteudo_avatar"));
                acao.setPontosGanhos(rs.getInt("pontos_ganhos"));
                acao.setCo2EvitadoKg(rs.getDouble("co2_evitado_kg"));
                acao.setNivelDificuldade(rs.getString("nivel_dificuldade"));
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return acao;
    }

    public ArrayList<AcaoSustentavel> obterTodasAcoes() {

        ArrayList<AcaoSustentavel> acoes = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_ACOES_SUSTENTAVEIS";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();
            while (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getString("tb_categorias_id_categoria"));

                AcaoSustentavel acao = new AcaoSustentavel();
                acao.setIdAcao(rs.getString("id_acao"));
                acao.setCategoria(categoria);
                acao.setTitulo(rs.getString("titulo"));
                acao.setDescricao(rs.getString("descricao"));
                acao.setConteudoAvatar(rs.getString("conteudo_avatar"));
                acao.setPontosGanhos(rs.getInt("pontos_ganhos"));
                acao.setCo2EvitadoKg(rs.getDouble("co2_evitado_kg"));
                acao.setNivelDificuldade(rs.getString("nivel_dificuldade"));

                acoes.add(acao);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return acoes;
    }

    public void atualizar(AcaoSustentavel acao) {
        try {
            String sqlUpdate = "UPDATE TB_ACOES_SUSTENTAVEIS SET tb_categorias_id_categoria = ?, titulo = ?, descricao = ?, conteudo_avatar = ?, pontos_ganhos = ?, co2_evitado_kg = ?, nivel_dificuldade = ? WHERE id_acao = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);

            comandoDeAtualizacao.setString(1, acao.getCategoria().getIdCategoria());
            comandoDeAtualizacao.setString(2, acao.getTitulo());
            comandoDeAtualizacao.setString(3, acao.getDescricao());
            comandoDeAtualizacao.setString(4, acao.getConteudoAvatar());
            comandoDeAtualizacao.setInt(5, acao.getPontosGanhos());
            comandoDeAtualizacao.setDouble(6, acao.getCo2EvitadoKg());
            comandoDeAtualizacao.setString(7, acao.getNivelDificuldade());
            comandoDeAtualizacao.setString(8, acao.getIdAcao());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(String idAcao) {
        try {
            String sqlDelete = "DELETE FROM TB_ACOES_SUSTENTAVEIS WHERE id_acao = ?";

            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);

            comandoDeRemocao.setString(1, idAcao);
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
