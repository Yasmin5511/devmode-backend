package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.NivelAvatarDAO;
import br.com.fiap.devmode.dao.UsuarioAvatarDAO;
import br.com.fiap.devmode.dao.UsuarioDAO;
import br.com.fiap.devmode.modelo.NivelAvatar;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.modelo.UsuarioAvatar;

import java.util.ArrayList;

public class UsuarioAvatarService {

    public UsuarioAvatar buscarPorUsuario(Integer idUsuario) {

        UsuarioAvatarDAO avatarDAO = new UsuarioAvatarDAO();

        try {
            return avatarDAO.buscarPorId(idUsuario);

        } finally {
            avatarDAO.desconectar();
        }
    }


    public void criarAvatar(Integer idUsuario) {

        UsuarioAvatarDAO avatarDAO = new UsuarioAvatarDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }

            if (avatarDAO.buscarPorId(idUsuario) != null) {
                throw new IllegalArgumentException("Usuário já possui um avatar.");
            }

            NivelAvatar nivelInicial = nivelDAO.buscarPorId(1);

            if (nivelInicial == null) {
                throw new IllegalArgumentException("Nível inicial não encontrado.");
            }

            UsuarioAvatar avatar = new UsuarioAvatar();

            avatar.setUsuario(usuario);
            avatar.setNivelAvatar(nivelInicial);
            avatar.setHumorAvatar("NEUTRO");

            avatarDAO.adicionar(avatar);

        } finally {
            avatarDAO.desconectar();
            usuarioDAO.desconectar();
            nivelDAO.desconectar();
        }
    }


    public void atualizarNivel(Integer idUsuario) {

        UsuarioAvatarDAO avatarDAO = new UsuarioAvatarDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            UsuarioAvatar avatar = avatarDAO.buscarPorId(idUsuario);

            if (avatar == null) {
                throw new IllegalArgumentException("Avatar não encontrado.");
            }

            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }

            ArrayList<NivelAvatar> niveis = nivelDAO.obterTodosNiveis();

            avatar.atualizarNivelPorPontos(usuario.getTotalPontosSustentaveis(), niveis);
            avatarDAO.atualizar(avatar);

        } finally {
            avatarDAO.desconectar();
            usuarioDAO.desconectar();
            nivelDAO.desconectar();
        }
    }

    public void atualizarHumor(Integer idUsuario, String novoHumor) {

        UsuarioAvatarDAO avatarDAO = new UsuarioAvatarDAO();

        try {
            UsuarioAvatar avatar = avatarDAO.buscarPorId(idUsuario);

            if (avatar == null) {
                throw new IllegalArgumentException("Avatar não encontrado.");
            }

            boolean atualizado = avatar.atualizarHumor(novoHumor);

            if (!atualizado) {
                throw new IllegalArgumentException("Humor inválido.");
            }

            avatarDAO.atualizar(avatar);

        } finally {
            avatarDAO.desconectar();
        }

    }
}
