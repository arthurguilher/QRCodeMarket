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
import com.example.arthur.qrcodemarket.Controlador;
import com.example.arthur.qrcodemarket.R;
import com.example.arthur.qrcodemarket.entidades.Carrinho;

import java.io.InputStream;
import java.util.ArrayList;

public class CarrinhoAdapter extends ArrayAdapter<Carrinho> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Carrinho> listaProdutos;
    private boolean isExibicao;
    private Carrinho carrinho;
    private Controlador controlador;

    public CarrinhoAdapter(Context context, int layoutResourceId, ArrayList<Carrinho> listaProdutos, boolean isExibicao) {
        super(context, layoutResourceId, listaProdutos);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listaProdutos = listaProdutos;
        this.isExibicao = isExibicao;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View item = inflater.inflate(layoutResourceId, parent, false);

        controlador = new Controlador(context, null, null, 1);

        TextView name = (TextView) item.findViewById(R.id.carrinho_nome_produto);
        TextView descricao = (TextView) item.findViewById(R.id.carrinho_subdescricao_produto);
        TextView valor = (TextView) item.findViewById(R.id.carrinho_valor_produto);
        final TextView quantidade = (TextView) item.findViewById(R.id.carrinho_quantidade_produto);
        ImageView foto = (ImageView) item.findViewById(R.id.carrinho_foto_produto);
        ImageView botao_aumentar = (ImageView) item.findViewById(R.id.carrinho_botao_aumentar);
        ImageView botao_diminuir = (ImageView) item.findViewById(R.id.carrinho_botao_diminuir);
        ImageView botao_deletar = (ImageView) item.findViewById(R.id.carrinho_botao_deletar);
        if (isExibicao) {
            botao_aumentar.setVisibility(View.GONE);
            botao_deletar.setVisibility(View.GONE);
            botao_diminuir.setVisibility(View.GONE);
        }

        carrinho = listaProdutos.get(position);

        name.setText(carrinho.getNome());
        descricao.setText(carrinho.getDescricao());
        valor.setText("R$: " + String.valueOf(String.format("%.2f", carrinho.getValor())).replace(".", ","));
        quantidade.setText(String.valueOf(carrinho.getQuantidade()));

        if (carrinho.getFoto() != null || !carrinho.getFoto().equals("")) {
            foto.setBackgroundColor(Color.TRANSPARENT);
            new DownloadImageTask((ImageView) item.findViewById(R.id.carrinho_foto_produto))
                    .execute(carrinho.getFoto());
        }

        botao_aumentar.setTag(position);
        botao_aumentar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                carrinho = listaProdutos.get(index);
                quantidade.setText(String.valueOf(carrinho.getQuantidade() + 1));
                carrinho.setQuantidade(carrinho.getQuantidade() + 1);
                controlador.atualizarCarrinho(carrinho);
                CarrinhoActivity.modificarValor(carrinho.getValor(), true);
            }
        });

        botao_diminuir.setTag(position);
        botao_diminuir.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                carrinho = listaProdutos.get(index);
                if (carrinho.getQuantidade() > 1) {
                    quantidade.setText(String.valueOf(carrinho.getQuantidade() - 1));
                    carrinho.setQuantidade(carrinho.getQuantidade() - 1);
                    controlador.atualizarCarrinho(carrinho);
                    CarrinhoActivity.modificarValor(carrinho.getValor(), false);
                }
            }
        });

        botao_deletar.setTag(position);
        botao_deletar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {  // Deletar produto da lista
                Integer index = (Integer) v.getTag();
                carrinho = listaProdutos.get(index);
                controlador.deletarCarrinho(carrinho);
                confirmationMessage(index, carrinho); // Confirmação de exclusão de carrinho
            }
        });

        return item;

    }

    private void confirmationMessage(final int id, final Carrinho carrinho) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Caso clique em sim
                        listaProdutos.remove(id);
                        CarrinhoActivity.excluirValor(carrinho.getValor(), carrinho.getQuantidade());
                        notifyDataSetChanged();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // Caso clique em não
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.remover_produto_carrinho).setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
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