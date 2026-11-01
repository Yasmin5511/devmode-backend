package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.dao.CategoriaDAO;
import br.com.fiap.devmode.modelo.Categoria;

import java.util.ArrayList;

public class CategoriaService {

    public Categoria buscarPorId(Integer id) {
        CategoriaDAO dao = new CategoriaDAO();

        try {
            return dao.buscarPorId(id);
        } finally {
            dao.desconectar();
        }
    }

    public ArrayList<Categoria> listarTodas() {
        CategoriaDAO dao = new CategoriaDAO();

        try {
            return dao.obterTodasCategorias();
        } finally {
            dao.desconectar();
        }
    }

    public void cadastrar(Categoria categoria) {
        validarCategoria(categoria);

        CategoriaDAO dao = new CategoriaDAO();

        try {
            dao.adicionar(categoria);
        } finally {
            dao.desconectar();
        }
    }

    public boolean atualizar(Integer id, Categoria categoria) {
        validarCategoria(categoria);

        CategoriaDAO dao = new CategoriaDAO();

        try {
            if (dao.buscarPorId(id) == null) {
                return false;
            }

            categoria.setIdCategoria(id);
            dao.atualizar(categoria);

            return true;
        } finally {
            dao.desconectar();
        }
    }

    public boolean remover(Integer id) {
        CategoriaDAO dao = new CategoriaDAO();

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

    private void validarCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException(
                    "A categoria é obrigatória."
            );
        }

        if (categoria.getIdCategoria() == null) {
            throw new IllegalArgumentException(
                    "O ID da categoria é obrigatório."
            );
        }

        if (categoria.getCategoria() == null) {
            throw new IllegalArgumentException(
                    "O nome da categoria é obrigatório."
            );
        }
    }
}
