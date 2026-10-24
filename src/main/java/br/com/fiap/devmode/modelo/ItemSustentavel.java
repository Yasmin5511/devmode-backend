package br.com.fiap.devmode.modelo;

import br.com.fiap.devmode.interfaces.Recomendavel;

public abstract class ItemSustentavel implements Recomendavel {
    private String titulo;
    private Categoria categoria;
    private int pontosGanhos;
    private String conteudoAvatar;

    public ItemSustentavel() {
    }

    public ItemSustentavel(
            String titulo,
            Categoria categoria,
            int pontosGanhos,
            String conteudoAvatar) {

        this.titulo = titulo;
        this.categoria = categoria;
        this.pontosGanhos = pontosGanhos;
        this.conteudoAvatar = conteudoAvatar;
    }

    @Override
    public Categoria getCategoria() {return categoria;}
    public void setCategoria(Categoria categoria) {this.categoria = categoria;}
    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}
    public int getPontosGanhos() {return pontosGanhos;}
    public void setPontosGanhos(int pontosGanhos) {this.pontosGanhos = pontosGanhos;}
    public String getConteudoAvatar() {return conteudoAvatar;}
    public void setConteudoAvatar(String conteudoAvatar) {this.conteudoAvatar = conteudoAvatar;}

}
