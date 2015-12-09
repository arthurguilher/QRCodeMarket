package com.example.arthur.qrcodemarket.entidades;

import com.example.arthur.qrcodemarket.enumerations.FormaPagamentoEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Arthur on 05/12/2015.
 */
public class Compra implements Serializable {

    private int id, numeroCasa;
    private Date data;
    private double valor, frete;
    private Cliente cliente;
    private String estado, cidade, bairro, logradouro, complemento, cep;
    private FormaPagamentoEnum formaPagamentoEnum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public FormaPagamentoEnum getFormaPagamentoEnum() {
        return formaPagamentoEnum;
    }

    public void setFormaPagamentoEnum(FormaPagamentoEnum formaPagamentoEnum) {
        this.formaPagamentoEnum = formaPagamentoEnum;
    }
}
