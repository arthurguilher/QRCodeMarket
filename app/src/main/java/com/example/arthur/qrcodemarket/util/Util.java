package com.example.arthur.qrcodemarket.util;

/**
 * Created by Arthur on 07/12/2015.
 */
public class Util {

    public String zeroFill(Object valor, int zeros) {
        String sValor = String.valueOf(valor);

        if (sValor.length() > zeros) {
            return sValor;
        }

        int restantes = zeros - sValor.length();
        StringBuilder zadd = new StringBuilder();
        for (int i = 0; i < restantes; i++) {
            zadd.append("0");
        }

        return zadd.append(sValor).toString();
    }

}
