package com.example.arthur.qrcodemarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arthur.qrcodemarket.entidades.Cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 24/10/2015.
 */
public class ClienteControlador extends SQLiteOpenHelper {

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

    public ClienteControlador(Context context, String name,
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
                + COLUNA_DATA_NASCIMENTO + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
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

    public void cadastrarCliente(Cliente cliente) {
        final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_CPF, cliente.getCpf());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_SENHA, cliente.getSenha());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_SEXO, cliente.getSexo());
        values.put(COLUNA_ESTADO, cliente.getEstado());
        values.put(COLUNA_CIDADE, cliente.getCidade());
        values.put(COLUNA_BAIRRO, cliente.getBairro());
        values.put(COLUNA_LOGRADOURO, cliente.getLogradouro());
        values.put(COLUNA_COMPLEMENTO, cliente.getComplemento());
        values.put(COLUNA_CEP, cliente.getCep());
        values.put(COLUNA_NUMERO_CASA, cliente.getNumeroCasa());
        values.put(COLUNA_DATA_NASCIMENTO, parser.format(cliente.getDataNascimento()));
        values.put(COLUNA_DATA_CADASTRO, parser.format(cliente.getDataCadastro()));
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELA_CLIENTE, null, values);
        db.close();
    }

    public Cliente loginCliente(String cpf, String senha) {
        String query = "SELECT * FROM " + TABELA_CLIENTE + " WHERE cpf = '" + cpf + "' AND senha = '" + senha + "'";
        Cliente cliente = new Cliente();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                cliente.setId(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                cliente.setSenha(cursor.getString(4));
                cliente.setTelefone(cursor.getString(5));
                cliente.setSexo(cursor.getString(6));
                cliente.setEstado(cursor.getString(7));
                cliente.setCidade(cursor.getString(8));
                cliente.setBairro(cursor.getString(9));
                cliente.setLogradouro(cursor.getString(10));
                cliente.setComplemento(cursor.getString(11));
                cliente.setCep(cursor.getString(12));
                cliente.setNumeroCasa(Integer.parseInt(cursor.getString(13)));
                cliente.setDataNascimento(CadastroActivity.sqlToString(cursor.getString(14)));
                cliente.setDataCadastro(CadastroActivity.sqlToString(cursor.getString(15)));
                cursor.moveToNext();
            }
        }
        return cliente;
    }

    public Cliente buscarCliente(String cpf) {
        String query = "SELECT * FROM " + TABELA_CLIENTE + " WHERE cpf = '" + cpf + "'";
        Cliente cliente = new Cliente();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                cliente.setId(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                cliente.setSenha(cursor.getString(4));
                cliente.setTelefone(cursor.getString(5));
                cliente.setSexo(cursor.getString(6));
                cliente.setEstado(cursor.getString(7));
                cliente.setCidade(cursor.getString(8));
                cliente.setBairro(cursor.getString(9));
                cliente.setLogradouro(cursor.getString(10));
                cliente.setComplemento(cursor.getString(11));
                cliente.setCep(cursor.getString(12));
                cliente.setNumeroCasa(Integer.parseInt(cursor.getString(13)));
                cliente.setDataNascimento(CadastroActivity.sqlToString(cursor.getString(14)));
                cliente.setDataCadastro(CadastroActivity.sqlToString(cursor.getString(15)));
                cursor.moveToNext();
            }
        }
        return cliente;
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_CLIENTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Cliente cliente = new Cliente();
                cliente.setId(Integer.parseInt(cursor.getString(0)));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                cliente.setSenha(cursor.getString(4));
                cliente.setTelefone(cursor.getString(5));
                cliente.setSexo(cursor.getString(6));
                cliente.setEstado(cursor.getString(7));
                cliente.setCidade(cursor.getString(8));
                cliente.setBairro(cursor.getString(9));
                cliente.setLogradouro(cursor.getString(10));
                cliente.setComplemento(cursor.getString(11));
                cliente.setCep(cursor.getString(12));
                cliente.setNumeroCasa(Integer.parseInt(cursor.getString(13)));
                cliente.setDataNascimento(CadastroActivity.sqlToString(cursor.getString(14)));
                cliente.setDataCadastro(CadastroActivity.sqlToString(cursor.getString(15)));
                clientes.add(cliente);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return clientes;
    }
}
