package br.com.fiap.devmode.modelo;

import java.util.ArrayList;

public class UsuarioAvatar {
    private Usuario usuario;
    private NivelAvatar nivelAvatar;
    private String humorAvatar;

    public UsuarioAvatar() {
    }

    public UsuarioAvatar(Usuario usuario, NivelAvatar nivelAvatar, String humorAvatar) {
        this.usuario = usuario;
        this.nivelAvatar = nivelAvatar;
        this.humorAvatar = humorAvatar;
    }

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public NivelAvatar getNivelAvatar() {return nivelAvatar;}
    public void setNivelAvatar(NivelAvatar nivelAvatar) {this.nivelAvatar = nivelAvatar;}
    public String getHumorAvatar() {return humorAvatar;}
    public void setHumorAvatar(String humorAvatar) {this.humorAvatar = humorAvatar;}

    public boolean atualizarHumor(String novoHumor) {

        if (novoHumor == null) {
            return false;
        }

        switch (novoHumor) {

            case "NEUTRO":
            case "ANIMADO":
            case "TRISTE":
            case "CELEBRANDO":
            case "PREOCUPADO":
                humorAvatar = novoHumor;
                return true;

            default:
                return false;
        }
    }
    public void atualizarNivelPorPontos(int pontosUsuario, ArrayList<NivelAvatar> niveis) {

        NivelAvatar novoNivel = this.nivelAvatar;
        for (NivelAvatar nivel : niveis) {
            if (pontosUsuario >= nivel.getGotasNecessarias()) {
                if (novoNivel == null || nivel.getNivel() > novoNivel.getNivel()) {
                    novoNivel = nivel;
                }
            }
        }

        this.nivelAvatar = novoNivel;
    }

}
