package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.ResgateCarteira;
import br.com.fiap.devmode.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class ResgateCarteiraDAO {
    private Connection conexao;

    public ResgateCarteiraDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(ResgateCarteira resgate) {
        try {
            String sqlInsert = "INSERT INTO TB_RESGATE_CARTEIRA (id_resgate, tb_usuario_id_usuario, pontos_utilizados, valor_pix, status, dt_resgate) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);
            comandoDeInsercao.setInt(1, resgate.getIdResgate());
            comandoDeInsercao.setInt(2, resgate.getUsuario().getIdUsuario());
            comandoDeInsercao.setInt(3, resgate.getPontosUtilizados());
            comandoDeInsercao.setDouble(4, resgate.getValorPix());
            comandoDeInsercao.setString(5, resgate.getStatus());
            comandoDeInsercao.setDate(6, Date.valueOf(resgate.getDataResgate()));

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResgateCarteira buscarPorId(Integer idResgate) {

        ResgateCarteira resgate = null;

        try {
            String sqlSelect = "SELECT * FROM TB_RESGATE_CARTEIRA WHERE id_resgate = ?";

            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setInt(1, idResgate);
            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("tb_usuario_id_usuario"));

                resgate = new ResgateCarteira();
                resgate.setIdResgate(rs.getInt("id_resgate"));
                resgate.setUsuario(usuario);
                resgate.setPontosUtilizados(rs.getInt("pontos_utilizados"));
                resgate.setValorPix(rs.getDouble("valor_pix"));
                resgate.setStatus(rs.getString("status"));

                if (rs.getDate("dt_resgate") != null) {
                    resgate.setDataResgate(rs.getDate("dt_resgate").toLocalDate());
                }
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resgate;
    }

    public ArrayList<ResgateCarteira> obterTodosResgates() {

        ArrayList<ResgateCarteira> resgates = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_RESGATE_CARTEIRA";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("tb_usuario_id_usuario"));

                ResgateCarteira resgate = new ResgateCarteira();
                resgate.setIdResgate(rs.getInt("id_resgate"));
                resgate.setUsuario(usuario);
                resgate.setPontosUtilizados(rs.getInt("pontos_utilizados"));
                resgate.setValorPix(rs.getDouble("valor_pix"));
                resgate.setStatus(rs.getString("status"));
                if (rs.getDate("dt_resgate") != null) {
                    resgate.setDataResgate(rs.getDate("dt_resgate").toLocalDate()
                    );
                }

                resgates.add(resgate);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resgates;
    }

    public void atualizar(ResgateCarteira resgate) {
        try {
            String sqlUpdate = "UPDATE TB_RESGATE_CARTEIRA SET tb_usuario_id_usuario = ?, pontos_utilizados = ?, valor_pix = ?, status = ?, dt_resgate = ? WHERE id_resgate = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);
            comandoDeAtualizacao.setInt(1, resgate.getUsuario().getIdUsuario());
            comandoDeAtualizacao.setInt(2, resgate.getPontosUtilizados());
            comandoDeAtualizacao.setDouble(3, resgate.getValorPix());
            comandoDeAtualizacao.setString(4, resgate.getStatus());
            comandoDeAtualizacao.setDate(5, Date.valueOf(resgate.getDataResgate()));
            comandoDeAtualizacao.setInt(6, resgate.getIdResgate());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(Integer idResgate) {
        try {
            String sqlDelete = "DELETE FROM TB_RESGATE_CARTEIRA WHERE id_resgate = ?";
            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setInt(1, idResgate);

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
