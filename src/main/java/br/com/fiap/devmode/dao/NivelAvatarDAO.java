package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.NivelAvatar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NivelAvatarDAO {
    private Connection conexao;

    public NivelAvatarDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(NivelAvatar nivelAvatar) {
        try {
            String sqlInsert = "INSERT INTO TB_NIVEL_AVATAR (nivel, nome_estagio, gotas_necessarias) VALUES (?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);
            comandoDeInsercao.setInt(1, nivelAvatar.getNivel());
            comandoDeInsercao.setString(2, nivelAvatar.getNomeEstagio());
            comandoDeInsercao.setInt(3, nivelAvatar.getGotasNecessarias());

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public NivelAvatar buscarPorId(int nivel) {

        NivelAvatar nivelAvatar = null;

        try {
            String sqlSelect = "SELECT * FROM TB_NIVEL_AVATAR WHERE nivel = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setInt(1, nivel);
            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                nivelAvatar = new NivelAvatar();
                nivelAvatar.setNivel(rs.getInt("nivel"));
                nivelAvatar.setNomeEstagio(rs.getString("nome_estagio"));
                nivelAvatar.setGotasNecessarias(rs.getInt("gotas_necessarias"));
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nivelAvatar;
    }

    public ArrayList<NivelAvatar> obterTodosNiveis() {

        ArrayList<NivelAvatar> niveis = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_NIVEL_AVATAR";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                NivelAvatar nivelAvatar = new NivelAvatar();
                nivelAvatar.setNivel(rs.getInt("nivel"));
                nivelAvatar.setNomeEstagio(rs.getString("nome_estagio"));
                nivelAvatar.setGotasNecessarias(rs.getInt("gotas_necessarias"));
                niveis.add(nivelAvatar);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return niveis;
    }

    public void atualizar(NivelAvatar nivelAvatar) {
        try {
            String sqlUpdate = "UPDATE TB_NIVEL_AVATAR SET nome_estagio = ?, gotas_necessarias = ? WHERE nivel = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);
            comandoDeAtualizacao.setString(1, nivelAvatar.getNomeEstagio());
            comandoDeAtualizacao.setInt(2, nivelAvatar.getGotasNecessarias());
            comandoDeAtualizacao.setInt(3, nivelAvatar.getNivel());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(int nivel) {
        try {
            String sqlDelete = "DELETE FROM TB_NIVEL_AVATAR WHERE nivel = ?";

            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setInt(1, nivel);

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
