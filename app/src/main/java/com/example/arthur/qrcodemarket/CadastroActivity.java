package com.example.arthur.qrcodemarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.arthur.qrcodemarket.entidades.Cliente;
import com.example.arthur.qrcodemarket.util.Mask;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CadastroActivity extends AppCompatActivity {

    private final Context context = this;
    public Cliente cliente;
    private AutoCompleteTextView campoCPF;
    private AutoCompleteTextView campoNome;
    private AutoCompleteTextView campoEmail;
    private AutoCompleteTextView campoTelefone;
    private AutoCompleteTextView campoSenha1;
    private AutoCompleteTextView campoSenha2;
    private AutoCompleteTextView campoNascimento;
    private Spinner spinnerSexo;
    private int tipoEntrada;

    public static Date sqlToString(String data) {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(fmt.parse(data).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        tipoEntrada = getIntent().getIntExtra("tipoEntrada", 0);

        campoCPF = (AutoCompleteTextView) findViewById(R.id.campoCPF);
        campoCPF.addTextChangedListener(Mask.insert(Mask.CPF_MASK, campoCPF)); // Formatar o campo do CPF

        campoTelefone = (AutoCompleteTextView) findViewById(R.id.campoTelefone);
        campoTelefone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, campoTelefone)); // Formatar o campo do telefone

        campoNome = (AutoCompleteTextView) findViewById(R.id.campoNome);
        campoEmail = (AutoCompleteTextView) findViewById(R.id.campoEmail);
        campoSenha1 = (AutoCompleteTextView) findViewById(R.id.campoSenha);
        campoSenha2 = (AutoCompleteTextView) findViewById(R.id.campoSenha2);

        campoNascimento = (AutoCompleteTextView) findViewById(R.id.campoNascimento);
        campoNascimento.addTextChangedListener(Mask.insert(Mask.DATA_MASK, campoNascimento));


        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sexo, android.R.layout.simple_spinner_item); // Adapter para o spinner
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1); // Tipo de Layout para o spinner
        spinnerSexo.setAdapter(adapter); // Aplicando o adapter

        Button botaoProximo = (Button) findViewById(R.id.botaoCadastrar);
        botaoProximo.setOnClickListener(new View.OnClickListener() { // Ação do botão Proximo
                                            @Override
                                            public void onClick(View v) {
                                                if (verificaCampos()) {
                                                    cliente = new Cliente();
                                                    cliente.setNome(campoNome.getText().toString());
                                                    cliente.setCpf(campoCPF.getText().toString());
                                                    cliente.setEmail(campoEmail.getText().toString());
                                                    cliente.setTelefone(campoTelefone.getText().toString());
                                                    cliente.setSenha(campoSenha1.getText().toString());
                                                    cliente.setSexo(spinnerSexo.getSelectedItem().toString());
                                                    cliente.setDataNascimento(sqlToString(campoNascimento.getText().toString()));
                                                    cliente.setDataCadastro(new Date(new java.util.Date().getTime()));
                                                    if (tipoEntrada == 1) {
                                                        ClienteControlador clienteControlador = new ClienteControlador(context, null, null, 1);
                                                        clienteControlador.cadastrarCliente(cliente);
                                                        Intent intent = new Intent(context, MenuActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(intent);
                                                        //finish();
                                                    }
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

        if (TextUtils.isEmpty(campoNascimento.getText().toString())) {
            campoNascimento.setError(getString(R.string.error_field_required));
            focusView = campoNascimento;
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
