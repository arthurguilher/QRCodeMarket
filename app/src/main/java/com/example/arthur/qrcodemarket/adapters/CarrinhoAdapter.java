package com.example.arthur.qrcodemarket.adapters;

/**
 * Created by Arthur on 26/10/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.CarrinhoActivity;
import com.example.arthur.qrcodemarket.R;
import com.example.arthur.qrcodemarket.entidades.Produto;

import java.io.InputStream;
import java.util.ArrayList;

public class CarrinhoAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

    public CarrinhoAdapter(Context context, int layoutResourceId,
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
            ProdutoWrapper.name = (TextView) item.findViewById(R.id.carrinho_nome_produto);
            ProdutoWrapper.descricao = (TextView) item.findViewById(R.id.carrinho_subdescricao_produto);
            ProdutoWrapper.valor = (TextView) item.findViewById(R.id.carrinho_valor_produto);
            ProdutoWrapper.quantidade = (TextView) item.findViewById(R.id.carrinho_quantidade_produto);
            ProdutoWrapper.foto = (ImageView) item.findViewById(R.id.carrinho_foto_produto);
            ProdutoWrapper.botao_aumentar = (ImageView) item.findViewById(R.id.carrinho_botao_aumentar);
            ProdutoWrapper.botao_diminuir = (ImageView) item.findViewById(R.id.carrinho_botao_diminuir);
            ProdutoWrapper.botao_deletar = (ImageView) item.findViewById(R.id.carrinho_botao_deletar);
            item.setTag(ProdutoWrapper);
        } else {
            ProdutoWrapper = (ProdutoWrapper) item.getTag();
        }

        final Produto produto = listaProdutos.get(position);
        ProdutoWrapper.name.setText(produto.getName());
        ProdutoWrapper.descricao.setText(produto.getDescricao());
        ProdutoWrapper.valor.setText("R$: " + String.valueOf(String.format("%.2f", produto.getValor())).replace(".", ","));
        ProdutoWrapper.quantidade.setText(String.valueOf(produto.getQuantidade()));
        if (produto.getFoto() != null || !produto.getFoto().equals("")) {
            ProdutoWrapper.foto.setBackgroundColor(Color.TRANSPARENT);
            new DownloadImageTask((ImageView) item.findViewById(R.id.carrinho_foto_produto))
                    .execute(produto.getFoto());
        }
        final CarrinhoAdapter.ProdutoWrapper finalProdutoWrapper = ProdutoWrapper;

        ProdutoWrapper.botao_aumentar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                finalProdutoWrapper.quantidade.setText(String.valueOf(produto.getQuantidade() + 1));
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

    static class ProdutoWrapper {
        TextView name;
        TextView valor;
        TextView quantidade;
        TextView descricao;
        ImageView foto;
        ImageView botao_aumentar;
        ImageView botao_diminuir;
        ImageView botao_deletar;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}