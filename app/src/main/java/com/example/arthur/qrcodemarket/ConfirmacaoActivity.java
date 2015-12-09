package com.example.arthur.qrcodemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.adapters.CarrinhoAdapter;
import com.example.arthur.qrcodemarket.entidades.Carrinho;
import com.example.arthur.qrcodemarket.entidades.Compra;
import com.example.arthur.qrcodemarket.entidades.Produto;
import com.example.arthur.qrcodemarket.entidades.ProdutosCompra;
import com.example.arthur.qrcodemarket.enumerations.FormaPagamentoEnum;
import com.example.arthur.qrcodemarket.util.Mask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfirmacaoActivity extends AppCompatActivity {

    private Context context = this;
    private Controlador controlador;
    private Spinner formaPagamento;
    private Compra compra;
    private ArrayList<Carrinho> carrinho;
    private List<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        controlador = new Controlador(context, null, null, 1);

        compra = (Compra) getIntent().getSerializableExtra("Compra");

        carrinho = controlador.listarCarrinho();
        produtos = new ArrayList<>();

        for (Carrinho carrinho1 : carrinho) {
            produtos.add(new Produto(carrinho1.getId()));
        }


        ((TextView) findViewById(R.id.textLogradouro)).setText(getString(R.string.logradouro) + ": " + compra.getLogradouro());
        ((TextView) findViewById(R.id.textNumero)).setText(getString(R.string.numero) + ": " + compra.getNumeroCasa());
        ((TextView) findViewById(R.id.textBairro)).setText(getString(R.string.bairro) + ": " + compra.getBairro());
        ((TextView) findViewById(R.id.textCidade)).setText(getString(R.string.cidade) + ": " + compra.getCidade());
        ((TextView) findViewById(R.id.textEstado)).setText(getString(R.string.uf) + ": " + compra.getEstado());
        ((TextView) findViewById(R.id.textComplemento)).setText(getString(R.string.complemento) + ": " + compra.getComplemento());
        ((TextView) findViewById(R.id.textCep)).setText(getString(R.string.cep) + ": " + Mask.mask(Mask.CEP_MASK, String.valueOf(compra.getCep())));
        ((TextView) findViewById(R.id.textFrete)).setText(getString(R.string.frete) + ": R$ " + getString(R.string.valor_frete).replace(".", ","));
        ((TextView) findViewById(R.id.textTotal)).setText(getString(R.string.total) + ": R$ " + (String.format("%.2f", getValorTotal() + Double.valueOf(getString(R.string.valor_frete))).replace(".", ",")));


        List<String> listaEnum = new ArrayList<>();
        for (int i = 0; i < FormaPagamentoEnum.values().length; i++) {
            listaEnum.add(FormaPagamentoEnum.valueOf(i).getNomeApresentacao());
        }

        formaPagamento = (Spinner) findViewById(R.id.textTipoPagamento);
        formaPagamento.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaEnum));

        findViewById(R.id.botaoComprar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compra.setFormaPagamentoEnum(FormaPagamentoEnum.valueOf(formaPagamento.getSelectedItemPosition()));
                compra.setData(new Date());
                long idPedido = controlador.cadastrarCompra(compra);

                for (Produto produto : produtos) {
                    ProdutosCompra produtosCompra = new ProdutosCompra();
                    produtosCompra.setCompra(compra);
                    produtosCompra.setProduto(produto);
                    controlador.cadastrarProdutosCompra(produtosCompra);
                }

                controlador.limparCarrinho();
                Intent intent = new Intent(context, CompraFinalizadaAcitivity.class);
                intent.putExtra("idPedido", idPedido);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.meuCarrinho).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.botao_meu_carrinho);
        dialog.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.list_pedido_produto, null);

        ListView listaProdutos = ((ListView) view.findViewById(R.id.lista_produtos));

        listaProdutos.setAdapter(new CarrinhoAdapter(context, R.layout.list_item, carrinho, true));

        dialog.setView(view);
        dialog.show();
    }


    private double getValorTotal() {
        double valor = 0;
        for (Carrinho carrinho1 : carrinho) {
            valor = valor + (carrinho1.getValor() * carrinho1.getQuantidade());
        }
        return valor;
    }


}
