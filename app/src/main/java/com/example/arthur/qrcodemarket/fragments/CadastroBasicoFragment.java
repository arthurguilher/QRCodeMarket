package com.example.arthur.qrcodemarket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.arthur.qrcodemarket.R;

/**
 * Created by Arthur on 29/10/2015.
 */
public class CadastroBasicoFragment extends Fragment {

    private AutoCompleteTextView campoNome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cadastro_basico, container, false);
        campoNome = (AutoCompleteTextView) rootView.findViewById(R.id.campoNome);

        Button botaoProximo = (Button) rootView.findViewById(R.id.botaoProximo);

        botaoProximo.setOnClickListener(new View.OnClickListener() { // Ação do botão Proximo
                                            @Override
                                            public void onClick(View v) {
                                                verificaCampos();
                                            }
                                        }
        );
        return rootView;
    }

    private void verificaCampos() {
        campoNome.setError(null);
        View focusView = null;

        campoNome.setError("Testando erro");
        focusView = campoNome;

    }
}
