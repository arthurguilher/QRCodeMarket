package com.example.arthur.qrcodemarket.entidades;

/**
 * Created by Arthur on 05/12/2015.
 */
public class ProdutosCompra {

    private int id;
    private Compra compra;
    private Produto produto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
