package br.com.fiap.devmode.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Integer idUsuario;
    private String cpf;
    private String email;
    private String nome;
    private String sobrenome;
    private int totalPontosSustentaveis;
    private LocalDate dataCriacao;

    private List<Categoria> categorias;

    public Usuario() {
        this.categorias = new ArrayList<>();
    }

    public Usuario(Integer idUsuario, String cpf, String email, String nome, String sobrenome, int totalPontosSustentaveis, LocalDate dataCriacao) {
        this.idUsuario = idUsuario;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.totalPontosSustentaveis = totalPontosSustentaveis;
        this.dataCriacao = dataCriacao;
        this.categorias = new ArrayList<>();
    }

    public Integer getIdUsuario() {return idUsuario;}
    public void setIdUsuario(Integer idUsuario) {this.idUsuario = idUsuario;}
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getSobrenome() {return sobrenome;}
    public void setSobrenome(String sobrenome) {this.sobrenome = sobrenome;}
    public int getTotalPontosSustentaveis() {return totalPontosSustentaveis;}
    public void setTotalPontosSustentaveis(int totalPontosSustentaveis) {this.totalPontosSustentaveis = totalPontosSustentaveis;}
    public LocalDate getDataCriacao() {return dataCriacao;}
    public void setDataCriacao(LocalDate dataCriacao) {this.dataCriacao = dataCriacao;}
    public List<Categoria> getCategorias() {return categorias;}

    public void escolherCategoria(Categoria categoria) {
        if(categoria != null && !categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    public void adicionarPontos(int pontos) {
        if (pontos > 0) {
            totalPontosSustentaveis += pontos;
        }
    }

    public boolean utilizarPontos(int pontos) {
        if (pontos > 0 && totalPontosSustentaveis >= pontos) {
            totalPontosSustentaveis -= pontos;
            return true;
        }

        return false;
    }

}
