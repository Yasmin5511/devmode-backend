package br.com.fiap.devmode.dao;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.modelo.NivelAvatar;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.modelo.UsuarioAvatar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioAvatarDAO {
    private Connection conexao;

    public UsuarioAvatarDAO() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(UsuarioAvatar usuarioAvatar) {
        try {
            String sqlInsert = "INSERT INTO TB_USUARIO_AVATAR (tb_usuario_id_usuario, tb_nivel_avatar_nivel, humor_avatar) VALUES (?, ?, ?)";

            PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);
            comandoDeInsercao.setString(1, usuarioAvatar.getUsuario().getIdUsuario());
            comandoDeInsercao.setInt(2, usuarioAvatar.getNivelAvatar().getNivel());
            comandoDeInsercao.setString(3, usuarioAvatar.getHumorAvatar());

            comandoDeInsercao.execute();
            comandoDeInsercao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UsuarioAvatar buscarPorId(String idUsuario) {

        UsuarioAvatar usuarioAvatar = null;

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO_AVATAR WHERE tb_usuario_id_usuario = ?";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            comandoDeSelecao.setString(1, idUsuario);

            ResultSet rs = comandoDeSelecao.executeQuery();

            if (rs.next()) {

                String idUsuarioBanco = rs.getString("tb_usuario_id_usuario");
                int nivelBanco = rs.getInt("tb_nivel_avatar_nivel");

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

                try {
                    Usuario usuario = usuarioDAO.buscarPorId(idUsuarioBanco);
                    NivelAvatar nivelAvatar = nivelDAO.buscarPorId(nivelBanco);

                    usuarioAvatar = new UsuarioAvatar();
                    usuarioAvatar.setUsuario(usuario);
                    usuarioAvatar.setNivelAvatar(nivelAvatar);
                    usuarioAvatar.setHumorAvatar(rs.getString("humor_avatar"));

                } finally {
                    usuarioDAO.desconectar();
                    nivelDAO.desconectar();
                }
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarioAvatar;
    }


    public ArrayList<UsuarioAvatar> obterTodosAvatares() {

        ArrayList<UsuarioAvatar> avatares = new ArrayList<>();

        try {
            String sqlSelect = "SELECT * FROM TB_USUARIO_AVATAR";
            PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
            ResultSet rs = comandoDeSelecao.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getString("tb_usuario_id_usuario"));

                NivelAvatar nivelAvatar = new NivelAvatar();
                nivelAvatar.setNivel(rs.getInt("tb_nivel_avatar_nivel"));

                UsuarioAvatar usuarioAvatar = new UsuarioAvatar();
                usuarioAvatar.setUsuario(usuario);
                usuarioAvatar.setNivelAvatar(nivelAvatar);
                usuarioAvatar.setHumorAvatar(rs.getString("humor_avatar"));

                avatares.add(usuarioAvatar);
            }

            rs.close();
            comandoDeSelecao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return avatares;
    }

    public void atualizar(UsuarioAvatar usuarioAvatar) {
        try {
            String sqlUpdate = "UPDATE TB_USUARIO_AVATAR SET tb_nivel_avatar_nivel = ?, humor_avatar = ? WHERE tb_usuario_id_usuario = ?";

            PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);
            comandoDeAtualizacao.setInt(1, usuarioAvatar.getNivelAvatar().getNivel());
            comandoDeAtualizacao.setString(2, usuarioAvatar.getHumorAvatar());
            comandoDeAtualizacao.setString(3, usuarioAvatar.getUsuario().getIdUsuario());

            comandoDeAtualizacao.execute();
            comandoDeAtualizacao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remover(String idUsuario) {
        try {
            String sqlDelete = "DELETE FROM TB_USUARIO_AVATAR WHERE tb_usuario_id_usuario = ?";
            PreparedStatement comandoDeRemocao = conexao.prepareStatement(sqlDelete);
            comandoDeRemocao.setString(1, idUsuario);

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
