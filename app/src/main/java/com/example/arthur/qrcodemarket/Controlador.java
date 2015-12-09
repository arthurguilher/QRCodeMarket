package com.example.arthur.qrcodemarket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arthur.qrcodemarket.entidades.Carrinho;
import com.example.arthur.qrcodemarket.entidades.Cliente;
import com.example.arthur.qrcodemarket.entidades.Compra;
import com.example.arthur.qrcodemarket.entidades.Produto;
import com.example.arthur.qrcodemarket.entidades.ProdutosCompra;
import com.example.arthur.qrcodemarket.enumerations.FormaPagamentoEnum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 24/10/2015.
 */
public class Controlador extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String NOME_BANCO = "qrcodemarket.db";
    private static final String TABELA_CLIENTE = "clientes";
    private static final String TABELA_CARRINHO = "carrinho";
    private static final String TABELA_COMPRA = "compras";
    private static final String TABELA_PRODUTOS_COMPRA = "produtos_compra";
    private static final String TABELA_PRODUTOS = "produtos";

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

    public Controlador(Context context, String name,
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
        String query = "CREATE TABLE " + TABELA_CARRINHO + " (id INTEGER PRIMARY KEY, nome TEXT, valor DOUBLE, descricao TEXT, foto TEXT, quantidade INTEGER)";
        db.execSQL(query);
        String query2 = "CREATE TABLE " + TABELA_COMPRA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, cliente_id INTEGER, data DATETIME DEFAULT CURRENT_TIMESTAMP, valor DOUBLE, " +
                "frete DOUBLE, forma_pagamento INTEGER, estado TEXT, cidade TEXT, bairro TEXT, logradouro TEXT, complemento TEXT, cep TEXT, numero_casa TEXT)";
        db.execSQL(query2);
        String query3 = "CREATE TABLE " + TABELA_PRODUTOS_COMPRA + " (id INTEGER PRIMARY KEY AUTOINCREMENT, produto_id INTEGER, compra_id INTEGER, FOREIGN KEY (produto_id) REFERENCES produto(id) ,FOREIGN KEY (compra_id) REFERENCES compras(id))";
        db.execSQL(query3);
        String query4 = "CREATE TABLE " + TABELA_PRODUTOS + " (id INTEGER NOT NULL, nome TEXT, descricao TEXT, foto TEXT, valor DOUBLE, PRIMARY KEY (id))";
        db.execSQL(query4);
    }

    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CARRINHO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_COMPRA);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTOS_COMPRA);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTOS);
        onCreate(db);
    }

    public void cadastrarCarrinho(Carrinho carrinho) {
        ContentValues values = new ContentValues();
        values.put("nome", carrinho.getNome());
        values.put("descricao", carrinho.getDescricao());
        values.put("foto", carrinho.getFoto());
        values.put("valor", carrinho.getValor());
        values.put("quantidade", carrinho.getQuantidade());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELA_CARRINHO, null, values);
        db.close();
    }

    public void atualizarCarrinho(Carrinho carrinho) {
        ContentValues values = new ContentValues();
        values.put("nome", carrinho.getNome());
        values.put("descricao", carrinho.getDescricao());
        values.put("foto", carrinho.getFoto());
        values.put("valor", carrinho.getValor());
        values.put("quantidade", carrinho.getQuantidade());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABELA_CARRINHO, values, "id = " + carrinho.getId(), null);
        db.close();
    }

    public void deletarCarrinho(Carrinho carrinho) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CARRINHO, "id = " + carrinho.getId(), null);
        db.close();
    }

    public void cadastrarProdutosCompra(ProdutosCompra produto) {
        ContentValues values = new ContentValues();
        values.put("compra_id", produto.getCompra().getId());
        values.put("produto_id", produto.getProduto().getId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELA_PRODUTOS_COMPRA, null, values);
        db.close();
    }

    public void cadastrarProdutos(Produto produto) {
        ContentValues values = new ContentValues();
        values.put("id", produto.getId());
        values.put("nome", produto.getNome());
        values.put("descricao", produto.getDescricao());
        values.put("foto", produto.getFoto());
        values.put("valor", produto.getValor());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELA_PRODUTOS, null, values);
        db.close();
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

    public void atualizarCliente(Cliente cliente) {
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
        db.update(TABELA_CLIENTE, values, "id = " + cliente.getId(), null);
        db.close();
    }

    public Produto buscarProduto(int id) {
        String query = "SELECT * FROM " + TABELA_PRODUTOS + " WHERE id = '" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Produto produto = new Produto();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                produto.setId(Integer.parseInt(cursor.getString(0)));
                produto.setNome(cursor.getString(1));
                produto.setDescricao(cursor.getString(2));
                produto.setFoto(cursor.getString(3));
                produto.setValor(cursor.getDouble(4));
                cursor.moveToNext();
            }
        }
        return produto;
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

    public void limparCarrinho() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CARRINHO, null, null);
        db.close();
    }

    public void limparCompras() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_COMPRA, null, null);
        db.close();
    }

    public ArrayList<Carrinho> listarCarrinho() {
        ArrayList<Carrinho> listaCarrinho = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_CARRINHO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Carrinho carrinho = new Carrinho();
                carrinho.setId(cursor.getInt(0));
                carrinho.setNome(cursor.getString(1));
                carrinho.setValor(cursor.getDouble(2));
                carrinho.setDescricao(cursor.getString(3));
                carrinho.setFoto(cursor.getString(4));
                carrinho.setQuantidade(cursor.getInt(5));
                listaCarrinho.add(carrinho);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listaCarrinho;
    }


    public long cadastrarCompra(Compra compra) {
        long id = 0;
        ContentValues values = new ContentValues();
        values.put("data", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(compra.getData()));
        values.put("cliente_id", compra.getCliente().getId());
        values.put("valor", compra.getValor());
        values.put("frete", compra.getFrete());
        values.put("forma_pagamento", compra.getFormaPagamentoEnum().ordinal());
        values.put("estado", compra.getEstado());
        values.put("cidade", compra.getCidade());
        values.put("bairro", compra.getBairro());
        values.put("logradouro", compra.getLogradouro());
        values.put("complemento", compra.getComplemento());
        values.put("cep", compra.getCep());
        values.put("numero_casa", compra.getNumeroCasa());
        SQLiteDatabase db = this.getWritableDatabase();
        id = db.insert(TABELA_COMPRA, null, values);
        db.close();
        return id;
    }

    public ArrayList<Compra> listarCompras(Cliente cliente) {
        ArrayList<Compra> listaCompra = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_COMPRA + " WHERE cliente_id = " + cliente.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Compra compra = new Compra();
                compra.setId(cursor.getInt(0));
                compra.setCliente(cliente);
                try {
                    compra.setData(df.parse(cursor.getString(2)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                compra.setValor(cursor.getDouble(3));
                compra.setFrete(cursor.getDouble(4));
                compra.setFormaPagamentoEnum(FormaPagamentoEnum.valueOf(cursor.getInt(5)));
                compra.setEstado(cursor.getString(6));
                compra.setCidade(cursor.getString(7));
                compra.setBairro(cursor.getString(8));
                compra.setLogradouro(cursor.getString(9));
                compra.setComplemento(cursor.getString(10));
                compra.setCep(cursor.getString(11));
                compra.setNumeroCasa(cursor.getInt(12));
                listaCompra.add(compra);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listaCompra;
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
