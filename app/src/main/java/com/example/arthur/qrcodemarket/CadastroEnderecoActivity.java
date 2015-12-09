package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.entidades.Cliente;
import com.example.arthur.qrcodemarket.util.Mask;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private final Context context = this;
    private AutoCompleteTextView campoCEP;
    private AutoCompleteTextView campoLogradouro;
    private AutoCompleteTextView campoNumeroResidencia;
    private AutoCompleteTextView campoBairro;
    private AutoCompleteTextView campoCidade;
    private TextView campoUF;
    private AutoCompleteTextView campoComplemento;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);
        setTitle("Cadastro - Etapa 2 de 3");

        cliente = (Cliente) getIntent().getSerializableExtra("Cliente");

        campoLogradouro = (AutoCompleteTextView) findViewById(R.id.campoLogradouro);
        campoNumeroResidencia = (AutoCompleteTextView) findViewById(R.id.campoNumeroResidencia);
        campoBairro = (AutoCompleteTextView) findViewById(R.id.campoBairro);
        campoCidade = (AutoCompleteTextView) findViewById(R.id.campoCidade);
        campoUF = (TextView) findViewById(R.id.textUf);
        campoComplemento = (AutoCompleteTextView) findViewById(R.id.campoComplemento);
        campoCEP = (AutoCompleteTextView) findViewById(R.id.campoCEP);
        campoCEP.addTextChangedListener(Mask.insert(Mask.CEP_MASK, campoCEP)); // Formatar campo do CEP

        findViewById(R.id.botaoCadastrar).setOnClickListener(new View.OnClickListener() { // Ação do botão Proximo
                                                                 @Override
                                                                 public void onClick(View v) {
                                                                     if (verificaCampos()) {
                                                                         cliente.setLogradouro(campoLogradouro.getText().toString());
                                                                         cliente.setNumeroCasa(Integer.parseInt(campoNumeroResidencia.getText().toString()));
                                                                         cliente.setCidade(campoCidade.getText().toString());
                                                                         cliente.setBairro(campoBairro.getText().toString());
                                                                         cliente.setCep(campoCEP.getText().toString());
                                                                         cliente.setComplemento(campoComplemento.getText().toString());
                                                                         cliente.setEstado(campoUF.getText().toString());
                                                                         Controlador controlador = new Controlador(context, null, null, 1);
                                                                         controlador.cadastrarCliente(cliente);
                                                                         Intent intent = new Intent(context, MenuActivity.class);
                                                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                         startActivity(intent);
                                                                     }
                                                                 }
                                                             }
        );
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
