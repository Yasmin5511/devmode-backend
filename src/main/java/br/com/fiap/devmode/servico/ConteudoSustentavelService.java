package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.ConteudoSustentavelDAO;
import br.com.fiap.devmode.modelo.ConteudoSustentavel;

import java.util.ArrayList;

public class ConteudoSustentavelService {

    public ArrayList<ConteudoSustentavel> listarTodos() {

        ConteudoSustentavelDAO conteudoDAO = new ConteudoSustentavelDAO();

        try {
            return conteudoDAO.obterTodosConteudos();
        } finally {
            conteudoDAO.desconectar();
        }
    }

    public ConteudoSustentavel buscarPorId(String id) {

        ConteudoSustentavelDAO conteudoDAO = new ConteudoSustentavelDAO();

        try {
            return conteudoDAO.buscarPorId(id);
        } finally {
            conteudoDAO.desconectar();
        }
    }

    public void cadastrar(ConteudoSustentavel conteudo) {

        validarConteudo(conteudo);

        ConteudoSustentavelDAO conteudoDAO = new ConteudoSustentavelDAO();

        try {
            conteudoDAO.adicionar(conteudo);
        } finally {
            conteudoDAO.desconectar();
        }
    }

    public boolean atualizar(String id, ConteudoSustentavel conteudo) {

        validarConteudo(conteudo);

        ConteudoSustentavelDAO conteudoDAO = new ConteudoSustentavelDAO();

        try {
            if (conteudoDAO.buscarPorId(id) == null) {
                return false;
            }

            conteudo.setIdConteudo(id);
            conteudoDAO.atualizar(conteudo);

            return true;
        } finally {
            conteudoDAO.desconectar();
        }
    }

    public boolean remover(String id) {

        ConteudoSustentavelDAO conteudoDAO = new ConteudoSustentavelDAO();

        try {
            if (conteudoDAO.buscarPorId(id) == null) {
                return false;
            }

            conteudoDAO.remover(id);

            return true;
        } finally {
            conteudoDAO.desconectar();
        }
    }

    private void validarConteudo(ConteudoSustentavel conteudo) {

        if (conteudo == null) {
            throw new IllegalArgumentException(
                    "O conteúdo sustentável é obrigatório.");
        }

        if (conteudo.getIdConteudo() == null ||
                conteudo.getIdConteudo().isBlank()) {
            throw new IllegalArgumentException(
                    "O ID do conteúdo é obrigatório.");
        }

        if (conteudo.getCategoria() == null ||
                conteudo.getCategoria().getIdCategoria() == null ||
                conteudo.getCategoria().getIdCategoria().isBlank()) {
            throw new IllegalArgumentException(
                    "A categoria do conteúdo é obrigatória.");
        }

        if (conteudo.getTitulo() == null ||
                conteudo.getTitulo().isBlank()) {
            throw new IllegalArgumentException(
                    "O título do conteúdo é obrigatório.");
        }

        if (conteudo.getConteudo() == null ||
                conteudo.getConteudo().isBlank()) {
            throw new IllegalArgumentException(
                    "O texto do conteúdo é obrigatório.");
        }

        if (conteudo.getPontosGanhos() < 0) {
            throw new IllegalArgumentException(
                    "Os pontos ganhos não podem ser negativos.");
        }
    }
}
