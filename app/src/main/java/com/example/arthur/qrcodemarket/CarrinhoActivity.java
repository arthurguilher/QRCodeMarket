package com.example.arthur.qrcodemarket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthur.qrcodemarket.adapters.CarrinhoAdapter;
import com.example.arthur.qrcodemarket.entidades.Carrinho;
import com.example.arthur.qrcodemarket.entidades.Produto;

import java.util.ArrayList;

public class CarrinhoActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static TextView textoTotal;
    private static double valorTotal = 0;
    private static ListView listview;
    private static ArrayList<Carrinho> listaProdutos;
    private static TextView textoCarrinhoVazio;
    private static TextView textoFrete;
    private static Button botaoComprar;
    private Context context = this;
    private CarrinhoAdapter carrinhoArrayAdapter;
    private Controlador controlador;

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //product barcode mode

    //
    public static void modificarValor(double valor, boolean operacao) {
        if (operacao) { // Se a operação for somar (adicionando produto à lista)
            valorTotal = valorTotal + valor;
        } else { // Se a operação for diminuir (remover produto da lista)
            valorTotal = valorTotal - valor;
        }
        textoTotal.setText("Sub-total: R$ " + String.valueOf(String.format("%.2f", valorTotal)).replace(".", ",")); // Formata o float para duas casas decimais e substitui o ponto pela vírgula
    }

    public static void excluirValor(double valor, int quantidade) {
        valorTotal = valorTotal - (valor * quantidade);
        textoTotal.setText("Sub-total: R$ " + String.valueOf(String.format("%.2f", valorTotal)).replace(".", ","));
        if (listaProdutos.isEmpty()) {
            textoTotal.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            botaoComprar.setVisibility(View.GONE);
            textoFrete.setVisibility(View.GONE);
            textoTotal.setVisibility(View.GONE);
            textoCarrinhoVazio.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        setTitle(R.string.botao_meu_carrinho);

        controlador = new Controlador(context, null, null, 1);

        listaProdutos = controlador.listarCarrinho();

        textoTotal = (TextView) findViewById(R.id.textoTotal);
        textoTotal.setText(getString(R.string.sub_total) + ": R$ " + String.valueOf(String.format("%.2f", valorTotal)).replace(".", ","));

        textoCarrinhoVazio = (TextView) findViewById(R.id.textCarrinhoVazio);

        textoFrete = (TextView) findViewById(R.id.textFrete);
        textoFrete.setText(getString(R.string.frete) + ": R$ " + getString(R.string.valor_frete).replace(".", ","));

        botaoComprar = (Button) findViewById(R.id.botaoComprar);

        carrinhoArrayAdapter = new CarrinhoAdapter(CarrinhoActivity.this, R.layout.list_item, listaProdutos, false);
        listview = (ListView) findViewById(R.id.listView);
        listview.setItemsCanFocus(false);
        listview.setAdapter(carrinhoArrayAdapter);

        if (listaProdutos.isEmpty()) {
            listview.setVisibility(View.GONE);
            botaoComprar.setVisibility(View.GONE);
            textoFrete.setVisibility(View.GONE);
            textoTotal.setVisibility(View.GONE);
        } else {
            textoCarrinhoVazio.setVisibility(View.GONE);
        }

        botaoComprar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (!listaProdutos.isEmpty()) {
                                                    if (MenuActivity.cliente == null) {
                                                        startActivity(new Intent(context, LoginActivity.class));
                                                    } else {
                                                        startActivity(new Intent(context, LocalEntregaActivity.class));
                                                    }
                                                } else {
                                                    Toast.makeText(context, getString(R.string.carrinho_vazio), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
        );

    }

    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(CarrinhoActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(CarrinhoActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String retornoQR[] = contents.split("\\?");
                //id_produto?nome_produto?descricao_produto?3.22?http://foto
                Carrinho carrinho = new Carrinho();
                Produto produto = new Produto();
                carrinho.setId(Integer.valueOf(retornoQR[0]));
                carrinho.setNome(retornoQR[1]);
                carrinho.setDescricao(retornoQR[2]);
                carrinho.setValor(Float.parseFloat(retornoQR[3]));
                carrinho.setFoto(retornoQR[4]);
                carrinho.setQuantidade(1);
                produto.setId(Integer.valueOf(retornoQR[0]));
                produto.setNome(retornoQR[1]);
                produto.setDescricao(retornoQR[2]);
                produto.setValor(Double.valueOf(retornoQR[3]));
                produto.setFoto(retornoQR[4]);
                listview.setVisibility(View.VISIBLE);
                botaoComprar.setVisibility(View.VISIBLE);
                textoFrete.setVisibility(View.VISIBLE);
                textoTotal.setVisibility(View.VISIBLE);
                textoCarrinhoVazio.setVisibility(View.GONE);
                if (listaProdutos.isEmpty()) {
                    controlador.cadastrarCarrinho(carrinho);
                    if (controlador.buscarProduto(produto.getId()) == null) {
                        controlador.cadastrarProdutos(produto);
                    }
                    listaProdutos.add(carrinho);
                    modificarValor(carrinho.getValor(), true);
                } else {
                    for (Carrinho carrinho1 : listaProdutos) {
                        if (carrinho1.getId() != carrinho.getId()) { // Verificar se o produto já está no carrinho.
                            System.out.println(1);
                            controlador.cadastrarCarrinho(carrinho);
                            if (controlador.buscarProduto(produto.getId()) == null) {
                                controlador.cadastrarProdutos(produto);
                            }
                            listaProdutos.add(carrinho);
                            modificarValor(carrinho.getValor(), true);
                        } else {
                            Toast.makeText(context, getString(R.string.produto_repetido), Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                }
            }
        }
    }
}
