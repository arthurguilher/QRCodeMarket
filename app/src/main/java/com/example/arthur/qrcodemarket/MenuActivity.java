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
    private ClienteControlador clienteControlador;
    private TextView textoNomeCliente;
    private TextView textoEmailCliente;
    private Button botaoEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //context.deleteDatabase("qrcodemarket.db");

        textoNomeCliente = (TextView) findViewById(R.id.nomeCliente);
        textoEmailCliente = (TextView) findViewById(R.id.emailCliente);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrar);
        clienteControlador = new ClienteControlador(context, null, null, 1);

        System.out.println("ON CREATE: " + clienteControlador.buscarCliente("093.827.014-19").getId());

        if (!clienteControlador.listarClientes().isEmpty()) {
            cliente = clienteControlador.listarClientes().get(0);
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
    }

    @Override
    protected void onResume() {
        System.out.println("ON RESUME: " + clienteControlador.listarClientes().size());
        if (!clienteControlador.listarClientes().isEmpty()) {
            cliente = clienteControlador.listarClientes().get(0);
            textoNomeCliente.setText(cliente.getNome());
            textoEmailCliente.setText(cliente.getEmail());
            botaoEntrar.setVisibility(View.GONE);
        }
        super.onResume();
    }
}
