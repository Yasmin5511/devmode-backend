package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.conexao.ConnectionFactory;
import br.com.fiap.devmode.dao.UsuarioAcaoStatusDAO;
import br.com.fiap.devmode.dao.UsuarioDAO;
import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.modelo.Usuario;
import br.com.fiap.devmode.modelo.UsuarioAcaoStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAcaoStatusService {

    public void iniciarMissao(String idUsuario, String idAcao, int progressoTotal) {

        UsuarioService usuarioService = new UsuarioService();
        AcaoSustentavelService acaoService = new AcaoSustentavelService();

        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        AcaoSustentavel acao = acaoService.buscarPorId(idAcao);

        if (usuario == null || acao == null) {
            throw new IllegalArgumentException(
                    "Usuário ou ação não encontrada."
            );
        }

        if (progressoTotal <= 0) {
            throw new IllegalArgumentException(
                    "O progresso total deve ser maior que zero."
            );
        }

        UsuarioAcaoStatusDAO statusDAO = new UsuarioAcaoStatusDAO();

        try {
            UsuarioAcaoStatus missao = statusDAO.buscarPorId(idAcao, idUsuario);

            if (missao != null) {
                throw new IllegalArgumentException(
                        "O usuário já possui essa missão."
                );
            }

            missao = new UsuarioAcaoStatus(usuario, acao, "DISPONIVEL", 0, progressoTotal, LocalDate.now());
            missao.iniciarMissao();
            statusDAO.adicionar(missao);

        } finally {
            statusDAO.desconectar();
        }
    }


    public UsuarioAcaoStatus buscarMissao(String idUsuario, String idAcao) {

        UsuarioAcaoStatusDAO statusDAO = new UsuarioAcaoStatusDAO();

        try {
            return statusDAO.buscarPorId(idAcao, idUsuario);
        } finally {
            statusDAO.desconectar();
        }
    }





    public void atualizarProgresso(String idUsuario, String idAcao, int quantidade) {

        UsuarioAcaoStatusDAO statusDAO = new UsuarioAcaoStatusDAO();

        try {
            UsuarioAcaoStatus missao = statusDAO.buscarPorId(idAcao, idUsuario);

            if (missao == null) {
                throw new IllegalArgumentException("Missão não encontrada.");
            }

            String statusAnterior = missao.getStatus();

            boolean atualizado = missao.atualizarProgresso(quantidade);

            if (!atualizado) {
                throw new IllegalArgumentException("Não foi possível atualizar o progresso.");
            }

            statusDAO.atualizar(missao);

            if (!"CONCLUIDA".equals(statusAnterior)
                    && "CONCLUIDA".equals(missao.getStatus())) {

                UsuarioDAO usuarioDAO = new UsuarioDAO();

                try {
                    Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

                    AcaoSustentavelService acaoService = new AcaoSustentavelService();
                    AcaoSustentavel acao = acaoService.buscarPorId(idAcao);

                    if (usuario == null || acao == null) {
                        throw new IllegalArgumentException("Usuário ou ação não encontrada.");
                    }

                    usuario.adicionarPontos(acao.getPontosGanhos());
                    usuarioDAO.atualizar(usuario);

                    UsuarioAvatarService avatarService = new UsuarioAvatarService();
                    avatarService.atualizarNivel(idUsuario);

                } finally {
                    usuarioDAO.desconectar();
                }
            }

            UsuarioAvatarService avatarService = new UsuarioAvatarService();

            if ("CONCLUIDA".equals(missao.getStatus())) {
                avatarService.atualizarHumor(idUsuario, "CELEBRANDO");
            } else {
                avatarService.atualizarHumor(idUsuario, "ANIMADO");
            }

        } finally {
            statusDAO.desconectar();
        }
    }





    public List<UsuarioAcaoStatus> listarMissoes(String idUsuario) {

        UsuarioAcaoStatusDAO statusDAO = new UsuarioAcaoStatusDAO();

        try {
            ArrayList<UsuarioAcaoStatus> todosStatus = statusDAO.obterTodosStatus();

            List<UsuarioAcaoStatus> missoesUsuario = new ArrayList<>();

            for (UsuarioAcaoStatus missao : todosStatus) {
                if (missao.getUsuario().getIdUsuario()
                        .equals(idUsuario)) {

                    missoesUsuario.add(missao);
                }
            }

            return missoesUsuario;

        } finally {
            statusDAO.desconectar();
        }
    }



}
