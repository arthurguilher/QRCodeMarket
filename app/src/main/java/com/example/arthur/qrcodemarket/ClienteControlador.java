package com.example.arthur.qrcodemarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arthur on 24/10/2015.
 */
public class ClienteControlador  extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "qrcodemarket.db";
    private static final String TABELA_CLIENTE = "clientes";

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_CPF = "cpf";
    private static final String COLUNA_EMAIL = "email";
    private static final String COLUNA_SENHA = "senha";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_SEXO = "sexo";
    private static final String COLUNA_ESTADO = "estado";
    private static final String COLUNA_CIDADE = "cidade";
    private static final String COLUNA_BAIRRO = "bairro";
    private static final String COLUNA_LOGRADOURO = "logradouro";
    private static final String COLUNA_COMPLEMENTO = "complemento";
    private static final String COLUNA_CEP = "cep";
    private static final String COLUNA_NUMERO_CASA = "numero_casa";
    private static final String COLUNA_DATA_NASCIMENTO = "data_nascimento";
    private static final String COLUNA_DATA_CADASTRO = "data_cadastro";

    public ClienteControlador (Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NOME_BANCO, factory, VERSAO_BANCO);
    }

    public void onCreate(SQLiteDatabase db) {
        String CRIAR_TABELA_CLIENTES = "CREATE TABLE " +
                TABELA_CLIENTE + "("
                + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUNA_NOME + " TEXT,"
                + COLUNA_CPF + " TEXT UNIQUE,"
                + COLUNA_EMAIL + " TEXT UNIQUE,"
                + COLUNA_SENHA + " TEXT,"
                + COLUNA_TELEFONE + " TEXT UNIQUE,"
                + COLUNA_SEXO + " TEXT UNIQUE,"
                + COLUNA_ESTADO + " TEXT,"
                + COLUNA_CIDADE + " TEXT,"
                + COLUNA_BAIRRO + " TEXT,"
                + COLUNA_LOGRADOURO + " TEXT,"
                + COLUNA_COMPLEMENTO + " TEXT,"
                + COLUNA_CEP + " TEXT,"
                + COLUNA_NUMERO_CASA + " INTEGER,"
                + COLUNA_DATA_NASCIMENTO + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + COLUNA_DATA_CADASTRO + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CRIAR_TABELA_CLIENTES);
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE);
        onCreate(db);
    }

    public void cadasastrarCliente(Cliente cliente) {

        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_CPF, cliente.getCpf());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELA_CLIENTE, null, values);
        db.close();
    }
}
