package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.modelo.UsuarioAcaoStatus;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioAcaoStatusDAO {
    private Connection conexao;

    public UsuarioAcaoStatusDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(UsuarioAcaoStatus usuarioAcaoStatus) {
        try {
            String sqlInsert = "INSERT INTO TB_USUARIO_ACAO_STATUS (tb_acoes_sustentaveis_id_acao, tb_usuario_id_usuario, status, progresso_atual, progresso_total, dt_atualizacao) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);

            comandoDeInsercao.setString(1, usuarioAcaoStatus.getAcao().getIdAcao());
            comandoDeInsercao.setString(2, usuarioAcaoStatus.getUsuario().getIdUsuario());
            comandoDeInsercao.setString(3, usuarioAcaoStatus.getStatus());
            comandoDeInsercao.setInt(4, usuarioAcaoStatus.getProgressoAtual());
            comandoDeInsercao.setInt(5, usuarioAcaoStatus.getProgressoTotal());
            comandoDeInsercao.setDate(6, Date.valueOf(usuarioAcaoStatus.getDataAtualizacao()));

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UsuarioAcaoStatus buscarPorId(String idAcao, String idUsuario) {

        UsuarioAcaoStatus usuarioAcaoStatus = null;

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO_ACAO_STATUS WHERE tb_acoes_sustentaveis_id_acao = ? AND tb_usuario_id_usuario = ?";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);

            comandoDeSelecao.setString(1, idAcao);
            comandoDeSelecao.setString(2, idUsuario);

            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                AcaoSustentavel acao = new AcaoSustentavel();
                acao.setIdAcao(rs.getString("tb_acoes_sustentaveis_id_acao"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("tb_usuario_id_usuario"));

                usuarioAcaoStatus = new UsuarioAcaoStatus();
                usuarioAcaoStatus.setAcao(acao);
                usuarioAcaoStatus.setUsuario(usuario);
                usuarioAcaoStatus.setStatus(rs.getString("status"));
                usuarioAcaoStatus.setProgressoAtual(rs.getInt("progresso_atual"));
                usuarioAcaoStatus.setProgressoTotal(rs.getInt("progresso_total"));

                if (rs.getDate("dt_atualizacao") != null) {usuarioAcaoStatus.setDataAtualizacao(rs.getDate("dt_atualizacao").toLocalDate());
                }
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarioAcaoStatus;
    }

    public ArrayList<UsuarioAcaoStatus> obterTodosStatus() {

        ArrayList<UsuarioAcaoStatus> listaStatus = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO_ACAO_STATUS";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                AcaoSustentavel acao = new AcaoSustentavel();
                acao.setIdAcao(rs.getString("tb_acoes_sustentaveis_id_acao"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("tb_usuario_id_usuario"));

                UsuarioAcaoStatus usuarioAcaoStatus = new UsuarioAcaoStatus();
                usuarioAcaoStatus.setAcao(acao);
                usuarioAcaoStatus.setUsuario(usuario);
                usuarioAcaoStatus.setStatus(rs.getString("status"));
                usuarioAcaoStatus.setProgressoAtual(rs.getInt("progresso_atual"));
                usuarioAcaoStatus.setProgressoTotal(rs.getInt("progresso_total"));

                if (rs.getDate("dt_atualizacao") != null) {usuarioAcaoStatus.setDataAtualizacao(rs.getDate("dt_atualizacao").toLocalDate());
                }

                listaStatus.add(usuarioAcaoStatus);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaStatus;
    }

    public void atualizar(UsuarioAcaoStatus usuarioAcaoStatus) {
        try {
            String sqlUpdate = "UPDATE TB_USUARIO_ACAO_STATUS SET status = ?, progresso_atual = ?, progresso_total = ?, dt_atualizacao = ? WHERE tb_acoes_sustentaveis_id_acao = ? AND tb_usuario_id_usuario = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);
            comandoDeAtualizacao.setString(1, usuarioAcaoStatus.getStatus());
            comandoDeAtualizacao.setInt(2, usuarioAcaoStatus.getProgressoAtual());
            comandoDeAtualizacao.setInt(3, usuarioAcaoStatus.getProgressoTotal());
            comandoDeAtualizacao.setDate(4, Date.valueOf(usuarioAcaoStatus.getDataAtualizacao()));
            comandoDeAtualizacao.setString(5, usuarioAcaoStatus.getAcao().getIdAcao());
            comandoDeAtualizacao.setString(6, usuarioAcaoStatus.getUsuario().getIdUsuario());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(String idAcao, String idUsuario) {
        try {
            String sqlDelete = "DELETE FROM TB_USUARIO_ACAO_STATUS WHERE tb_acoes_sustentaveis_id_acao = ? AND tb_usuario_id_usuario = ?";

            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setString(1, idAcao);
            comandoDeRemocao.setString(2, idUsuario);

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
