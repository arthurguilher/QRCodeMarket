package com.example.arthur.qrcodemarket.enumerations;

/**
 * Created by Arthur on 05/12/2015.
 */
public enum FormaPagamentoEnum {


    DINHEIRO("Dinheiro", "D"),
    CARTAO("Cartão de crédito", "C"),
    ONLINE("Online", "O");

    private final String nomeApresentacao;
    private final String sigla;

    private FormaPagamentoEnum(String nomeApresentacao, String sigla) {
        this.nomeApresentacao = nomeApresentacao;
        this.sigla = sigla;
    }

    public static FormaPagamentoEnum valueOf(int ordinal) {
        for (FormaPagamentoEnum t : values()) {
            if (t.ordinal() == ordinal) {
                return t;
            }
        }
        return null;
    }

    public String getNomeApresentacao() {
        return nomeApresentacao;
    }

    public String getSigla() {
        return sigla;
    }

}
