package com.example.arthur.qrcodemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CadastroPagamentoActivity extends AppCompatActivity {

    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pagamento);
        setTitle("Cadastro - Etapa 3 de 3");

        Button botaoFinalizar = (Button) findViewById(R.id.botaoFinalizar);
        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteControlador db = new ClienteControlador(context, null, null, 1);
                db.cadastrarCliente(CadastroBasicoActivity.cliente);
                Toast.makeText(context, "Cadastro realizado com sucesso. Agora você já pode entrar!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CadastroPagamentoActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button botaoPular = (Button) findViewById(R.id.botaoPular);
        botaoPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationMessage();
            }
        });
    }

    private void confirmationMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ClienteControlador db = new ClienteControlador(context, null, null, 1);
                        db.cadastrarCliente(CadastroBasicoActivity.cliente);
                        Toast.makeText(context, "Cadastro realizado com sucesso. Agora você já pode entrar!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CadastroPagamentoActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // "Limpa" todas as Activity criadas e volta para a principal
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se você pular esta etapa terá que preenchê-la posteriormente para que possa efetuar compras. Tem certeza que deseja pular?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
    }
}
