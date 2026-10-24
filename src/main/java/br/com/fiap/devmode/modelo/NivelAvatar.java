package br.com.fiap.devmode.modelo;

public class NivelAvatar {
    private int nivel;
    private String nomeEstagio;
    private int gotasNecessarias;

    public NivelAvatar() {
    }

    public NivelAvatar(int nivel, String nomeEstagio, int gotasNecessarias) {
        this.nivel = nivel;
        this.nomeEstagio = nomeEstagio;
        this.gotasNecessarias = gotasNecessarias;
    }

    public int getNivel() {return nivel;}
    public void setNivel(int nivel) {this.nivel = nivel;}
    public String getNomeEstagio() {return nomeEstagio;}
    public void setNomeEstagio(String nomeEstagio) {this.nomeEstagio = nomeEstagio;}
    public int getGotasNecessarias() {return gotasNecessarias;}
    public void setGotasNecessarias(int gotasNecessarias) {this.gotasNecessarias = gotasNecessarias;}
}
