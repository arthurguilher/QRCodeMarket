package com.example.arthur.qrcodemarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.arthur.qrcodemarket.util.Mask;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private AutoCompleteTextView campoCEP;
    private AutoCompleteTextView campoLogradouro;
    private AutoCompleteTextView campoNumeroResidencia;
    private AutoCompleteTextView campoBairro;
    private AutoCompleteTextView campoCidade;
    private Spinner campoUF;
    private AutoCompleteTextView campoComplemento;
    private final Context context = this;

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
                                                if (verificaCampos()) {
                                                    CadastroBasicoActivity.cliente.setLogradouro(campoLogradouro.getText().toString());
                                                    CadastroBasicoActivity.cliente.setNumeroCasa(Integer.parseInt(campoNumeroResidencia.getText().toString()));
                                                    CadastroBasicoActivity.cliente.setCidade(campoCidade.getText().toString());
                                                    CadastroBasicoActivity.cliente.setBairro(campoBairro.getText().toString());
                                                    CadastroBasicoActivity.cliente.setCep(campoCEP.getText().toString());
                                                    CadastroBasicoActivity.cliente.setComplemento(campoComplemento.getText().toString());
                                                    CadastroBasicoActivity.cliente.setEstado(campoUF.getSelectedItem().toString());
                                                    Intent intent = new Intent(context, CadastroPagamentoActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
        );

        Button botaoPular = (Button) findViewById(R.id.botaoPular);
        botaoPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationMessage(); // Ação do botão pular (mostrar mensagem de confirmação)
            }
        });


    }


    private void confirmationMessage() { // Método para mostrar dialog de confirmação para pular etapa
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Caso clique em sim
                        Intent intent = new Intent(context, CadastroPagamentoActivity.class);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE: // Caso clique em não
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se você pular esta etapa terá que preenchê-la posteriormente para que possa efetuar compras. Tem certeza que deseja pular?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
    }

    private boolean verificaCampos() { // Método para verificar se os campos estão vazios

        campoBairro.setError(null);
        campoCEP.setError(null);
        campoCidade.setError(null);
        campoLogradouro.setError(null);
        campoNumeroResidencia.setError(null);

        View focusView = null; // Foco de erro
        boolean aux = false; // Auxiliar para saber se algum campo é vazio

        if (TextUtils.isEmpty(campoBairro.getText().toString())) { // Verificando se o campo é vazio
            campoBairro.setError(getString(R.string.error_field_required)); // Setando mensagem de erro
            focusView = campoBairro; // Setando o foco no campo do Bairro
            aux = true;
        }

        if (TextUtils.isEmpty(campoNumeroResidencia.getText().toString())) {
            campoNumeroResidencia.setError(getString(R.string.error_field_required));
            focusView = campoNumeroResidencia;
            aux = true;
        }

        if (TextUtils.isEmpty(campoLogradouro.getText().toString())) {
            campoLogradouro.setError(getString(R.string.error_field_required));
            focusView = campoLogradouro;
            aux = true;
        }

        if (TextUtils.isEmpty(campoCidade.getText().toString())) {
            campoCidade.setError(getString(R.string.error_field_required));
            focusView = campoCidade;
            aux = true;
        }


        if (TextUtils.isEmpty(campoCEP.getText().toString())) {
            campoCEP.setError(getString(R.string.error_field_required));
            focusView = campoCEP;
            aux = true;
        }

        if (aux) { // Se tem algum campo vazio
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }


    }

}
