package br.com.fiap.devmode.modelo;

public class ConteudoSustentavel extends ItemSustentavel{
    private String idConteudo;
    private String conteudo;
    private String linkRedirecionamento;

    public ConteudoSustentavel() {
    }

    public ConteudoSustentavel(String idConteudo, String titulo, Categoria categoria, String conteudo, String conteudoAvatar, String linkRedirecionamento, int pontosGanhos) {
        super(titulo, categoria, pontosGanhos, conteudoAvatar);
        this.idConteudo = idConteudo;
        this.conteudo = conteudo;
        this.linkRedirecionamento = linkRedirecionamento;
    }

    public String getIdConteudo() {return idConteudo;}
    public void setIdConteudo(String idConteudo) {this.idConteudo = idConteudo;}
    public String getConteudo() {return conteudo;}
    public void setConteudo(String conteudo) {this.conteudo = conteudo;}
    public String getLinkRedirecionamento() {return linkRedirecionamento;}
    public void setLinkRedirecionamento(String linkRedirecionamento) {this.linkRedirecionamento = linkRedirecionamento;}
}
