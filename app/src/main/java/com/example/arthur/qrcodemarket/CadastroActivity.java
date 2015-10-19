package com.example.arthur.qrcodemarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("CadastroActivity - Etapa 1 de 3");

        AutoCompleteTextView campoCPF = (AutoCompleteTextView) findViewById(R.id.campoCPF); // Campo CPF
        campoCPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK, campoCPF)); // Formatar o campo do CPF

        AutoCompleteTextView campoTelefone = (AutoCompleteTextView) findViewById(R.id.campoTelefone); // Campo telefone
        campoTelefone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, campoTelefone)); // Formatar o campo do telefone
    }
}
