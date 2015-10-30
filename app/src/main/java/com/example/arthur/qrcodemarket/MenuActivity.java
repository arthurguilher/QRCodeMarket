package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FloatingActionButton botaoNovaCompra = (FloatingActionButton) findViewById(R.id.botao_nova_compra);
        botaoNovaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarrinhoActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton botaoEditarInfo = (FloatingActionButton) findViewById(R.id.botao_editar_info);
        botaoEditarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfigInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
