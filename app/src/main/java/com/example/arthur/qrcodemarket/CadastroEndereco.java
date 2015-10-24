package com.example.arthur.qrcodemarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class CadastroEndereco extends AppCompatActivity {

    private AutoCompleteTextView campoCEP;
    private AutoCompleteTextView campoLogradouro;
    private AutoCompleteTextView campoNumeroResidencia;
    private AutoCompleteTextView campoBairro;
    private AutoCompleteTextView campoCidade;
    private Spinner campoUF;
    private AutoCompleteTextView campoComplemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);
        setTitle("Cadastro - Etapa 2 de 3");

        campoLogradouro = (AutoCompleteTextView) findViewById(R.id.campoLogradouro);
        campoNumeroResidencia = (AutoCompleteTextView) findViewById(R.id.campoNumeroResidencia);
        campoBairro = (AutoCompleteTextView) findViewById(R.id.campoBairro);
        campoCidade = (AutoCompleteTextView) findViewById(R.id.campoCidade);
        campoUF = (Spinner) findViewById(R.id.spinnerUF);
        campoComplemento = (AutoCompleteTextView) findViewById(R.id.campoComplemento);
        campoCEP = (AutoCompleteTextView) findViewById(R.id.campoCEP);
        campoCEP.addTextChangedListener(Mask.insert(Mask.CEP_MASK, campoCEP)); // Formatar campo do CEP

        Spinner spinner = (Spinner) findViewById(R.id.spinnerUF);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_uf, android.R.layout.simple_spinner_item); // Adapter para o spinner
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1); // Tipo de Layout para o spinner
        spinner.setAdapter(adapter); // Aplicando o adapter


        Button botaoProximo = (Button) findViewById(R.id.botaoProximo);
        botaoProximo.setOnClickListener(new View.OnClickListener() { // Ação do botão Proximo
                                            @Override
                                            public void onClick(View v) {
                                                CadastroActivity.cliente.setLogradouro(campoLogradouro.getText().toString());
                                                CadastroActivity.cliente.setNumeroCasa(Integer.parseInt(campoNumeroResidencia.getText().toString()));
                                                CadastroActivity.cliente.setCidade(campoCidade.getText().toString());
                                                CadastroActivity.cliente.setBairro(campoBairro.getText().toString());
                                                CadastroActivity.cliente.setCep(campoCEP.getText().toString());
                                                CadastroActivity.cliente.setComplemento(campoComplemento.getText().toString());
                                                CadastroActivity.cliente.setEstado(campoUF.getSelectedItem().toString());
                                            }
                                        }
        );


    }

}
