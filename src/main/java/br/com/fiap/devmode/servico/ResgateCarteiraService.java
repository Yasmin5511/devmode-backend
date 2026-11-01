package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.ResgateCarteiraDAO;
import br.com.fiap.devmode.dao.UsuarioDAO;
import br.com.fiap.devmode.modelo.ResgateCarteira;
import br.com.fiap.devmode.modelo.Usuario;

import java.util.ArrayList;

public class ResgateCarteiraService {

    public void solicitarResgate(Integer idResgate, Integer idUsuario, int pontos) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ResgateCarteiraDAO resgateDAO = new ResgateCarteiraDAO();

        try {
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);

            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado.");
            }

            if (idResgate == null) {
                throw new IllegalArgumentException("ID do resgate é obrigatório.");
            }

            if (resgateDAO.buscarPorId(idResgate) != null) {
                throw new IllegalArgumentException("Já existe um resgate com esse ID.");
            }

            ResgateCarteira resgate = new ResgateCarteira();
            resgate.setIdResgate(idResgate);
            resgate.setUsuario(usuario);
            resgate.setPontosUtilizados(pontos);

            if (!resgate.solicitarResgate()) {
                throw new IllegalArgumentException("Pontos inválidos ou saldo insuficiente.");
            }

            resgateDAO.adicionar(resgate);
            usuarioDAO.atualizar(usuario);

        } finally {
            resgateDAO.desconectar();
            usuarioDAO.desconectar();
        }
    }

    public ResgateCarteira buscarPorId(Integer idResgate) {
        ResgateCarteiraDAO resgateDAO = new ResgateCarteiraDAO();

        try {
            return resgateDAO.buscarPorId(idResgate);
        } finally {
            resgateDAO.desconectar();
        }
    }

    public ArrayList<ResgateCarteira> listarTodos() {
        ResgateCarteiraDAO resgateDAO = new ResgateCarteiraDAO();

        try {
            return resgateDAO.obterTodosResgates();
        } finally {
            resgateDAO.desconectar();
        }
    }
}
