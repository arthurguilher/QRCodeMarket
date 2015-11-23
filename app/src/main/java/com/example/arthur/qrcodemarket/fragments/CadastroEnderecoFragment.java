package com.example.arthur.qrcodemarket.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arthur.qrcodemarket.R;

/**
 * Created by Arthur on 29/10/2015.
 */
public class CadastroEnderecoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_endereco, container, false);
        return rootView;
    }
}
