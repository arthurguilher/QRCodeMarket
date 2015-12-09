package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.arthur.qrcodemarket.entidades.Cliente;
import com.example.arthur.qrcodemarket.entidades.Compra;
import com.example.arthur.qrcodemarket.util.Mask;

public class LocalEntregaActivity extends AppCompatActivity {

    private final Context context = this;
    private Cliente cliente;
    private AutoCompleteTextView campoCEP;
    private AutoCompleteTextView campoLogradouro;
    private AutoCompleteTextView campoNumeroResidencia;
    private AutoCompleteTextView campoBairro;
    private AutoCompleteTextView campoCidade;
    private TextView campoUF;
    private AutoCompleteTextView campoComplemento;
    private Controlador controlador;
    private Compra compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_entrega);
        setTitle(R.string.local_entrega);

        cliente = MenuActivity.cliente;
        controlador = new Controlador(context, null, null, 1);

        campoLogradouro = (AutoCompleteTextView) findViewById(R.id.campoLogradouro);
        campoNumeroResidencia = (AutoCompleteTextView) findViewById(R.id.campoNumeroResidencia);
        campoBairro = (AutoCompleteTextView) findViewById(R.id.campoBairro);
        campoCidade = (AutoCompleteTextView) findViewById(R.id.campoCidade);
        campoUF = (TextView) findViewById(R.id.textUf);
        campoComplemento = (AutoCompleteTextView) findViewById(R.id.campoComplemento);
        campoCEP = (AutoCompleteTextView) findViewById(R.id.campoCEP);
        campoCEP.addTextChangedListener(Mask.insert(Mask.CEP_MASK, campoCEP)); // Formatar campo do CEP

        TextView textLogradouro = (TextView) findViewById(R.id.textLogradouro);
        textLogradouro.setText(getString(R.string.logradouro) + ": " + cliente.getLogradouro());
        TextView textNumero = (TextView) findViewById(R.id.textNumero);
        textNumero.setText(getString(R.string.numero) + ": " + cliente.getNumeroCasa());
        TextView textBairro = (TextView) findViewById(R.id.textBairro);
        textBairro.setText(getString(R.string.bairro) + ": " + cliente.getBairro());
        TextView textCidade = (TextView) findViewById(R.id.textCidade);
        textCidade.setText(getString(R.string.cidade) + ": " + cliente.getCidade());
        TextView textEstado = (TextView) findViewById(R.id.textEstado);
        textEstado.setText(getString(R.string.uf) + ": " + cliente.getEstado());
        TextView textComplemento = (TextView) findViewById(R.id.textComplemento);
        textComplemento.setText(getString(R.string.complemento) + ": " + cliente.getComplemento());
        TextView textCep = (TextView) findViewById(R.id.textCep);
        textCep.setText(getString(R.string.cep) + ": " + Mask.mask(Mask.CEP_MASK, String.valueOf(cliente.getCep())));

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupEntrega);
        final RadioButton radioMeuEndereco = (RadioButton) findViewById(R.id.radioMeuEndereco);
        final RadioButton radioOutroEndereco = (RadioButton) findViewById(R.id.radioNovoEndereco);
        final CardView cardEndereco = (CardView) findViewById(R.id.cardDescricao);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.form_endereco);
        linearLayout.setVisibility(View.GONE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMeuEndereco) {
                    cardEndereco.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                } else if (checkedId == R.id.radioNovoEndereco) {
                    linearLayout.setVisibility(View.VISIBLE);
                    cardEndereco.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.botaoContinuar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                compra = new Compra();

                // find which radioButton is checked by id
                if (selectedId == radioMeuEndereco.getId()) {
                    compra.setBairro(cliente.getBairro());
                    compra.setCidade(cliente.getCidade());
                    compra.setCep(cliente.getCep());
                    compra.setLogradouro(cliente.getLogradouro());
                    compra.setNumeroCasa(cliente.getNumeroCasa());
                    compra.setComplemento(cliente.getComplemento());
                    compra.setEstado(cliente.getEstado());
                } else if (selectedId == radioOutroEndereco.getId()) {
                    if (verificaCampos()) {
                        compra.setEstado(campoUF.getText().toString());
                        compra.setCidade(campoCidade.getText().toString());
                        compra.setCep(campoCEP.getText().toString());
                        compra.setLogradouro(campoLogradouro.getText().toString());
                        compra.setNumeroCasa(Integer.valueOf(campoNumeroResidencia.getText().toString()));
                        compra.setComplemento(campoComplemento.getText().toString());
                        compra.setBairro(campoBairro.getText().toString());
                    }
                }
                compra.setCliente(cliente);
                compra.setFrete(Double.valueOf(getString(R.string.valor_frete)));
                Intent intent = new Intent(context, ConfirmacaoActivity.class);
                intent.putExtra("Compra", compra);
                startActivity(intent);

            }
        });
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
