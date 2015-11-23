package com.example.arthur.qrcodemarket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthur.qrcodemarket.adapters.CarrinhoAdapter;
import com.example.arthur.qrcodemarket.entidades.Produto;

import java.util.ArrayList;

public class CarrinhoActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private ListView listview;
    private Context context = this;
    private CarrinhoAdapter carrinhoArrayAdapter;
    private ArrayList<Produto> listaProdutos = new ArrayList<Produto>();
    private int aux = 0;
    private static TextView textoTotal;
    private static double valorTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        setTitle("Nova Compra");

        textoTotal = (TextView) findViewById(R.id.textoTotal);
        carrinhoArrayAdapter = new CarrinhoAdapter(CarrinhoActivity.this, R.layout.list_item, listaProdutos);
        listview = (ListView) findViewById(R.id.listView);
        listview.setItemsCanFocus(false);
        listview.setAdapter(carrinhoArrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Toast.makeText(CarrinhoActivity.this,
                        "List Item Clicked:" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }


    //product barcode mode
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

    //alert dialog for downloadDialog
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

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String retornoQR[] = contents.split("\\?");
                // nome_produto?descricao_produto?3.22?http://foto
                listaProdutos.add(new Produto(retornoQR[0], retornoQR[1], Float.parseFloat(retornoQR[2]), 1, retornoQR[3]));
                modificarValor(Float.parseFloat(retornoQR[2]), true);
                //Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                //toast.show();

            }
        }
    }
//
    public static void modificarValor(double valor, boolean operacao){
        if (operacao) { // Se a operação for somar (adicionando produto à lista)
            valorTotal = valorTotal + valor;
        } else { // Se a operação for diminuir (remover produto da lista)
            valorTotal = valorTotal - valor;
        }
        textoTotal.setText("Total: R$: " + String.valueOf(String.format("%.2f", valorTotal)).replace(".", ",")); // Formata o float para duas casas decimais e substitui o ponto pela vírgula
    }

    public static void excluirValor(double valor, int quantidade){
        valorTotal = valorTotal - (valor * quantidade);
        textoTotal.setText("Total: R$: " + String.valueOf(String.format("%.2f", valorTotal)).replace(".", ","));
    }

    @Override
    public void onBackPressed(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Caso clique em sim
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // Caso clique em não
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Tem certeza que deseja desistir desta compra?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
    }

}
