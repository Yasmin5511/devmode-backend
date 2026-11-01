package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.AcaoSustentavelDAO;
import br.com.fiap.devmode.modelo.AcaoSustentavel;

import java.util.ArrayList;

public class AcaoSustentavelService {

    public AcaoSustentavel buscarPorId(Integer id) {
        AcaoSustentavelDAO dao = new AcaoSustentavelDAO();

        try {
            return dao.buscarPorId(id);
        } finally {
            dao.desconectar();
        }
    }

    public ArrayList<AcaoSustentavel> listarTodas() {
        AcaoSustentavelDAO dao = new AcaoSustentavelDAO();

        try {
            return dao.obterTodasAcoes();
        } finally {
            dao.desconectar();
        }
    }

    public void cadastrar(AcaoSustentavel acao) {
        validarAcao(acao);

        AcaoSustentavelDAO dao = new AcaoSustentavelDAO();

        try {
            dao.adicionar(acao);
        } finally {
            dao.desconectar();
        }
    }

    public boolean atualizar(Integer id, AcaoSustentavel acao) {
        validarAcao(acao);

        AcaoSustentavelDAO dao = new AcaoSustentavelDAO();

        try {
            if (dao.buscarPorId(id) == null) {
                return false;
            }

            acao.setIdAcao(id);
            dao.atualizar(acao);

            return true;
        } finally {
            dao.desconectar();
        }
    }

    public boolean remover(Integer id) {
        AcaoSustentavelDAO dao = new AcaoSustentavelDAO();

        try {
            if (dao.buscarPorId(id) == null) {
                return false;
            }

            dao.remover(id);
            return true;
        } finally {
            dao.desconectar();
        }
    }

    private void validarAcao(AcaoSustentavel acao) {
        if (acao == null) {
            throw new IllegalArgumentException("A ação é obrigatória.");
        }

        if (acao.getIdAcao() == null) {
            throw new IllegalArgumentException("O ID da ação é obrigatório.");
        }

        if (acao.getCategoria() == null ||
                acao.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }

        if (acao.getTitulo() == null || acao.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        if (acao.getPontosGanhos() < 0) {
            throw new IllegalArgumentException(
                    "Os pontos ganhos não podem ser negativos."
            );
        }

        if (acao.getCo2EvitadoKg() < 0) {
            throw new IllegalArgumentException(
                    "O CO2 evitado não pode ser negativo."
            );
        }
    }
}
