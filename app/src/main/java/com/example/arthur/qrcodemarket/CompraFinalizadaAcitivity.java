package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.util.Util;

public class CompraFinalizadaAcitivity extends AppCompatActivity {

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_finalizada);

        long id = getIntent().getLongExtra("idPedido", 0);
        System.out.println("Numero pedido:" + id);

        Util util = new Util();

        setTitle(R.string.pedido);

        ((TextView) findViewById(R.id.numero_pedido)).setText(getString(R.string.pedido_numero, util.zeroFill(id, 5)));

        findViewById(R.id.botaoVoltarMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
