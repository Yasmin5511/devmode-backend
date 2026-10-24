package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.ConteudoSustentavel;
import br.com.fiap.devmode.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SistemaRecomendacao {
    private List<AcaoSustentavel> listaAcoes;
    private List<ConteudoSustentavel> listaConteudos;

    public SistemaRecomendacao() {
        listaAcoes = new ArrayList<>();
        listaConteudos = new ArrayList<>();
    }

    public void adicionarAcao(AcaoSustentavel acao) {
        listaAcoes.add(acao);
    }

    public void adicionarConteudo(ConteudoSustentavel conteudo) {
        listaConteudos.add(conteudo);
    }

    public List<AcaoSustentavel> recomendarAcoes(Usuario usuario) {

        List<AcaoSustentavel> recomendadas = new ArrayList<>();

        for (AcaoSustentavel acao : listaAcoes) {

            for (Categoria categoriaUsuario : usuario.getCategorias()) {

                if (acao.getCategoria().getIdCategoria().equals(categoriaUsuario.getIdCategoria())) {
                    recomendadas.add(acao);
                }
            }
        }

        return recomendadas;
    }

    public List<ConteudoSustentavel> recomendarConteudos(Usuario usuario) {

        List<ConteudoSustentavel> recomendados = new ArrayList<>();

        for (ConteudoSustentavel conteudo : listaConteudos) {

            for (Categoria categoriaUsuario : usuario.getCategorias()) {

                if (conteudo.getCategoria().getIdCategoria().equals(categoriaUsuario.getIdCategoria())) {

                    recomendados.add(conteudo);
                }
            }
        }

        return recomendados;
    }
}

