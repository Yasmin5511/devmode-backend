package br.com.fiap.devmode.modelo;

public class AcaoSustentavel extends ItemSustentavel{
    private String idAcao;
    private String descricao;
    private double co2EvitadoKg;
    private String nivelDificuldade;

    public AcaoSustentavel() {
    }

    public AcaoSustentavel(
            String idAcao,
            String titulo,
            Categoria categoria,
            String descricao,
            String conteudoAvatar,
            int pontosGanhos,
            double co2EvitadoKg,
            String nivelDificuldade) {

        super(titulo, categoria, pontosGanhos, conteudoAvatar);

        this.idAcao = idAcao;
        this.descricao = descricao;
        this.co2EvitadoKg = co2EvitadoKg;
        this.nivelDificuldade = nivelDificuldade;
    }
    public String getIdAcao() {return idAcao;}
    public void setIdAcao(String idAcao) {this.idAcao = idAcao;}
    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public double getCo2EvitadoKg() {return co2EvitadoKg;}
    public void setCo2EvitadoKg(double co2EvitadoKg) {this.co2EvitadoKg = co2EvitadoKg;}
    public String getNivelDificuldade() {return nivelDificuldade;}
    public void setNivelDificuldade(String nivelDificuldade) {this.nivelDificuldade = nivelDificuldade;}
}
