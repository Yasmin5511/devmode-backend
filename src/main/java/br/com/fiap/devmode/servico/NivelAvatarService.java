package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.NivelAvatarDAO;
import br.com.fiap.devmode.modelo.NivelAvatar;

import java.util.ArrayList;

public class NivelAvatarService {

    public ArrayList<NivelAvatar> listarNiveis() {

        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            return nivelDAO.obterTodosNiveis();
        } finally {
            nivelDAO.desconectar();
        }
    }

    public NivelAvatar buscarPorId(int nivel) {

        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            return nivelDAO.buscarPorId(nivel);
        } finally {
            nivelDAO.desconectar();
        }
    }

    public void cadastrar(NivelAvatar nivelAvatar) {

        validarNivel(nivelAvatar);

        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            nivelDAO.adicionar(nivelAvatar);
        } finally {
            nivelDAO.desconectar();
        }
    }

    public boolean atualizar(int nivel, NivelAvatar nivelAvatar) {

        nivelAvatar.setNivel(nivel);
        validarNivel(nivelAvatar);

        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            if (nivelDAO.buscarPorId(nivel) == null) {
                return false;
            }

            nivelDAO.atualizar(nivelAvatar);
            return true;

        } finally {
            nivelDAO.desconectar();
        }
    }

    public boolean remover(int nivel) {

        NivelAvatarDAO nivelDAO = new NivelAvatarDAO();

        try {
            if (nivelDAO.buscarPorId(nivel) == null) {
                return false;
            }

            nivelDAO.remover(nivel);
            return true;

        } finally {
            nivelDAO.desconectar();
        }
    }

    private void validarNivel(NivelAvatar nivelAvatar) {

        if (nivelAvatar == null) {
            throw new IllegalArgumentException(
                    "O nível do avatar é obrigatório.");
        }

        if (nivelAvatar.getNivel() <= 0) {
            throw new IllegalArgumentException(
                    "O nível deve ser maior que zero.");
        }

        if (nivelAvatar.getNomeEstagio() == null ||
                nivelAvatar.getNomeEstagio().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do estágio é obrigatório.");
        }

        if (nivelAvatar.getGotasNecessarias() < 0) {
            throw new IllegalArgumentException(
                    "As gotas necessárias não podem ser negativas.");
        }
    }
}
