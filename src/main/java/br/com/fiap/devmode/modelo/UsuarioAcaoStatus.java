package br.com.fiap.devmode.modelo;

import java.time.LocalDate;

public class UsuarioAcaoStatus {
    private Usuario usuario;
    private AcaoSustentavel acao;
    private String status;
    private int progressoAtual;
    private int progressoTotal;
    private LocalDate dataAtualizacao;

    public UsuarioAcaoStatus() {
    }
    public UsuarioAcaoStatus(Usuario usuario, AcaoSustentavel acao, String status, int progressoAtual, int progressoTotal, LocalDate dataAtualizacao) {

        this.usuario = usuario;
        this.acao = acao;
        this.status = status;
        this.progressoAtual = progressoAtual;
        this.progressoTotal = progressoTotal;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public AcaoSustentavel getAcao() {return acao;}
    public void setAcao(AcaoSustentavel acao) {this.acao = acao;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public int getProgressoAtual() {return progressoAtual;}
    public void setProgressoAtual(int progressoAtual) {this.progressoAtual = progressoAtual;}
    public int getProgressoTotal() {return progressoTotal;}
    public void setProgressoTotal(int progressoTotal) {this.progressoTotal = progressoTotal;}
    public LocalDate getDataAtualizacao() {return dataAtualizacao;}
    public void setDataAtualizacao(LocalDate dataAtualizacao) {this.dataAtualizacao = dataAtualizacao;}

    public boolean iniciarMissao() {
        if ("DISPONIVEL".equals(status)) {
            status = "EM_ANDAMENTO";
            dataAtualizacao = LocalDate.now();
            return true;
        }

        return false;
    }

    public boolean atualizarProgresso(int quantidade) {
        if (!"EM_ANDAMENTO".equals(status) || quantidade <= 0) {
            return false;
        }
        progressoAtual += quantidade;

        if (progressoAtual >= progressoTotal) {
            progressoAtual = progressoTotal;
            status = "CONCLUIDA";
        }

        dataAtualizacao = LocalDate.now();
        return true;
    }

}
