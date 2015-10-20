package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class CadastroActivity extends AppCompatActivity {

    private final Context context = this;
    private AutoCompleteTextView campoCPF;
    private AutoCompleteTextView campoNome;
    private AutoCompleteTextView campoEmail;
    private AutoCompleteTextView campoTelefone;
    private AutoCompleteTextView campoSenha1;
    private AutoCompleteTextView campoSenha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro - Etapa 1 de 3");

        campoCPF = (AutoCompleteTextView) findViewById(R.id.campoCPF);
        campoCPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK, campoCPF)); // Formatar o campo do CPF

        campoTelefone = (AutoCompleteTextView) findViewById(R.id.campoTelefone);
        campoTelefone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, campoTelefone)); // Formatar o campo do telefone

        campoNome = (AutoCompleteTextView) findViewById(R.id.campoNome);
        campoEmail = (AutoCompleteTextView) findViewById(R.id.campoEmail);
        campoSenha1 = (AutoCompleteTextView) findViewById(R.id.campoSenha);
        campoSenha2 = (AutoCompleteTextView) findViewById(R.id.campoSenha2);

        Button botaoProximo = (Button) findViewById(R.id.botaoProximo);
        botaoProximo.setOnClickListener(new View.OnClickListener() { // Ação do botão Proximo
                                            @Override
                                            public void onClick(View v) {
                                                if (verificaCampos()){
                                                    Intent intent = new Intent(context, CadastroEndereco.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }
        );
    }


    private boolean verificaCampos() { // Método para verificar se os campos estão vazios

        campoCPF.setError(null);
        campoTelefone.setError(null);
        campoSenha1.setError(null);
        campoSenha2.setError(null);
        campoNome.setError(null);

        View focusView = null; // Foco de erro
        boolean aux = false; // Auxiliar para saber se algum campo é vazio

        if (TextUtils.isEmpty(campoCPF.getText().toString())) { // Verificando se o campo é vazio
            campoCPF.setError(getString(R.string.error_field_required)); // Setando mensagem de erro
            focusView = campoCPF; // Setando o foco no campo do CPF
            aux = true;
        }

        if (TextUtils.isEmpty(campoNome.getText().toString())) {
            campoNome.setError(getString(R.string.error_field_required));
            focusView = campoNome;
            aux = true;
        }

        if (TextUtils.isEmpty(campoTelefone.getText().toString())) {
            campoTelefone.setError(getString(R.string.error_field_required));
            focusView = campoTelefone;
            aux = true;
        }

        if (!campoSenha1.getText().toString().equals(campoSenha2.getText().toString())) { // Verificando se o campo das senhas coincidem
            campoSenha2.setError("As senhas não coincidem");
            focusView = campoSenha2;
            aux = true;
        }

        if (campoSenha1.getText().toString().length() < 4) { // Verificando tamanho da senha
            campoSenha1.setError("Senha muito curta");
            focusView = campoSenha1;
            aux = true;
        }

        if (campoSenha1.getText().toString().length() > 10) { // Verificando tamanho da senha
            campoSenha1.setError("Senha muito grande");
            focusView = campoSenha1;
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
