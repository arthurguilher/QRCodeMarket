package com.example.arthur.qrcodemarket;

/**
 * Created by Arthur on 26/10/2015.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CarrinhoArrayAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

    public CarrinhoArrayAdapter(Context context, int layoutResourceId,
                          ArrayList<Produto> listaProdutos) {
        super(context, layoutResourceId, listaProdutos);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listaProdutos = listaProdutos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ProdutoWrapper ProdutoWrapper = null;

        View linha = null;

        if (item == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            item = inflater.inflate(layoutResourceId, parent, false);

            ProdutoWrapper = new ProdutoWrapper();
            ProdutoWrapper.name = (TextView) item.findViewById(R.id.descricao_produto);
            ProdutoWrapper.valor = (TextView) item.findViewById(R.id.valor);
            ProdutoWrapper.quantidade = (TextView) item.findViewById(R.id.campoQuantidade);
            ProdutoWrapper.botao_aumentar = (Button) item.findViewById(R.id.botao_aumentar);
            ProdutoWrapper.botao_diminuir = (Button) item.findViewById(R.id.botao_diminuir);
            ProdutoWrapper.botao_deletar = (Button) item.findViewById(R.id.botao_deletar);
            item.setTag(ProdutoWrapper);
        } else {
            ProdutoWrapper = (ProdutoWrapper) item.getTag();
        }

        final Produto produto = listaProdutos.get(position);
        ProdutoWrapper.name.setText(produto.getName());
        ProdutoWrapper.valor.setText("Valor (unidade): R$: " + String.valueOf(produto.getValor()));
        ProdutoWrapper.quantidade.setText(String.valueOf(produto.getQuantidade()));
        final CarrinhoArrayAdapter.ProdutoWrapper finalProdutoWrapper = ProdutoWrapper;

        ProdutoWrapper.botao_aumentar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                finalProdutoWrapper.quantidade.setText(String.valueOf(produto.getQuantidade()+1));
                produto.setQuantidade(produto.getQuantidade() + 1);
                CarrinhoActivity.modificarValor(produto.getValor(), true);
            }
        });

        ProdutoWrapper.botao_diminuir.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                if (produto.getQuantidade() > 1) {
                    finalProdutoWrapper.quantidade.setText(String.valueOf(produto.getQuantidade() - 1));
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    CarrinhoActivity.modificarValor(produto.getValor(), false);
                }
            }
        });

        ProdutoWrapper.botao_deletar.setTag(position);
        ProdutoWrapper.botao_deletar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {  // Deletar produto da lista
                Integer index = (Integer) v.getTag();
                confirmationMessage(index, produto); // Confirmação de exclusão de produto
            }
        });

        return item;

    }

    static class ProdutoWrapper {
        TextView name;
        TextView valor;
        TextView quantidade;
        Button botao_aumentar;
        Button botao_diminuir;
        Button botao_deletar;
    }


    private void confirmationMessage(final int id, final Produto produto) { // Método para mostrar dialog de confirmação para pular etapa
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Caso clique em sim
                        listaProdutos.remove(id);
                        notifyDataSetChanged();
                        CarrinhoActivity.excluirValor(produto.getValor(), produto.getQuantidade());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // Caso clique em não
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Tem certeza que deseja remover este produto do seu carrinho?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
    }

}