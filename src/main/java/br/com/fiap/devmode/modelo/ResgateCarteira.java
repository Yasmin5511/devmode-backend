package br.com.fiap.devmode.modelo;

import java.time.LocalDate;

public class ResgateCarteira {
    private Integer idResgate;
    private Usuario usuario;
    private int pontosUtilizados;
    private double valorPix;
    private String status;
    private LocalDate dataResgate;

    public ResgateCarteira() {
    }

    public ResgateCarteira(Integer idResgate, Usuario usuario, int pontosUtilizados, double valorPix, String status, LocalDate dataResgate) {
        this.idResgate = idResgate;
        this.usuario = usuario;
        this.pontosUtilizados = pontosUtilizados;
        this.valorPix = valorPix;
        this.status = status;
        this.dataResgate = dataResgate;
    }

    public Integer getIdResgate() {return idResgate;}
    public void setIdResgate(Integer idResgate) {this.idResgate = idResgate;}
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public int getPontosUtilizados() {return pontosUtilizados;}
    public void setPontosUtilizados(int pontosUtilizados) {this.pontosUtilizados = pontosUtilizados;}
    public double getValorPix() {return valorPix;}
    public void setValorPix(double valorPix) {this.valorPix = valorPix;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public LocalDate getDataResgate() {return dataResgate;}
    public void setDataResgate(LocalDate dataResgate) {this.dataResgate = dataResgate;}

    public boolean solicitarResgate() {

        if (usuario == null) {
            return false;
        }

        if (pontosUtilizados <= 0) {
            return false;
        }

        if (!usuario.utilizarPontos(pontosUtilizados)) {
            return false;
        }

        valorPix = converterPontosParaPix();
        status = "PENDENTE";
        dataResgate = LocalDate.now();

        return true;
    }

    public double converterPontosParaPix() {
        return pontosUtilizados * 0.01;
    }
}
