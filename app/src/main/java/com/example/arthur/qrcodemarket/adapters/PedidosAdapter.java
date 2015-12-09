package com.example.arthur.qrcodemarket.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.R;
import com.example.arthur.qrcodemarket.entidades.Compra;
import com.example.arthur.qrcodemarket.util.Util;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Arthur on 08/12/2015.
 */
public class PedidosAdapter extends ArrayAdapter<Compra> {


    private Context context;
    private List<Compra> listaCompra;
    private Compra compra;
    private int layoutResourceId;

    public PedidosAdapter(Context context, int layoutResourceId, List<Compra> listaCompra) {
        super(context, layoutResourceId, listaCompra);
        this.context = context;
        this.listaCompra = listaCompra;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        item = inflater.inflate(layoutResourceId, parent, false);

        Util util = new Util();
        compra = listaCompra.get(position);

        ((TextView) item.findViewById(R.id.codigoPedido)).setText(context.getString(R.string.pedido_numero, util.zeroFill(compra.getId(), 5)));
        ((TextView) item.findViewById(R.id.dataPedido)).setText(context.getString(R.string.data) + ": " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(compra.getData()));

        return item;
    }
}


