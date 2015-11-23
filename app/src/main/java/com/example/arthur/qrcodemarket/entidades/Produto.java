package com.example.arthur.qrcodemarket.entidades;

import java.io.Serializable;

/**
 * Created by Arthur on 26/10/2015.
 */
public class Produto {

    private String name;
    private double valor;
    private int quantidade;
    private String foto;
    private String descricao;

    public Produto(String name, String descricao, double valor, int quantidade, String foto) {
        this.name = name;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
