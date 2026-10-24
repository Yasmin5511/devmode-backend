package br.com.fiap.devmode.modelo;

public class Categoria {
    private String idCategoria;
    private String categoria;

    public Categoria() {
    }

    public Categoria(String idCategoria, String categoria) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
