package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.adapters.PedidosAdapter;
import com.example.arthur.qrcodemarket.entidades.Compra;

import java.util.List;

public class ComprasActivity extends AppCompatActivity {

    private Context context = this;
    private List<Compra> compras;
    private Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        setTitle(R.string.botao_minhas_compras);

        controlador = new Controlador(context, null, null, 1);

        TextView textListaVazia = (TextView) findViewById(R.id.textListaVazia);
        Button meuCarrinho = (Button) findViewById(R.id.botaoCarrinho);
        ListView listView = (ListView) findViewById(R.id.listaPedidos);

        if (MenuActivity.cliente == null) {
            startActivity(new Intent(context, LoginActivity.class));
        } else {
            compras = controlador.listarCompras(MenuActivity.cliente);
            if (compras.isEmpty()) {
                listView.setVisibility(View.GONE);
            } else {
                textListaVazia.setVisibility(View.GONE);
                meuCarrinho.setVisibility(View.GONE);
                listView.setAdapter(new PedidosAdapter(context, R.layout.compras_item, compras));
            }
        }


        meuCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CarrinhoActivity.class));
                finish();
            }
        });
    }
}
