package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.entidades.Cliente;

public class MenuActivity extends AppCompatActivity {

    public static Cliente cliente;
    private Context context = this;
    private Controlador controlador;
    private TextView textoNomeCliente;
    private TextView textoEmailCliente;
    private Button botaoEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //context.deleteDatabase("qrcodemarket.db");
        controlador = new Controlador(context, null, null, 1);
        controlador.limparCarrinho();
        //controlador.limparCompras();

        textoNomeCliente = (TextView) findViewById(R.id.nomeCliente);
        textoEmailCliente = (TextView) findViewById(R.id.emailCliente);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrar);

        if (!controlador.listarClientes().isEmpty()) {
            cliente = controlador.listarClientes().get(0);
            textoNomeCliente.setText(cliente.getNome());
            textoEmailCliente.setText(cliente.getEmail());
            botaoEntrar.setVisibility(View.GONE);
        }

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("tipoEntrada", 1);
                startActivity(intent);
            }
        });

        findViewById(R.id.botaoCarrinho).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CarrinhoActivity.class));
            }
        });

        findViewById(R.id.botaoCompras).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ComprasActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        if (!controlador.listarClientes().isEmpty()) {
            cliente = controlador.listarClientes().get(0);
            textoNomeCliente.setText(cliente.getNome());
            textoEmailCliente.setText(cliente.getEmail());
            botaoEntrar.setVisibility(View.GONE);
        }
        super.onResume();
    }
}
