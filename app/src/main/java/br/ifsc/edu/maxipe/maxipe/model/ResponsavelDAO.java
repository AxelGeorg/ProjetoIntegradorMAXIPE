package br.ifsc.edu.maxipe.maxipe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ResponsavelDAO extends DB {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    public ResponsavelDAO(@Nullable Context context) {
        super(context);
        this.createTableRes();

    }

    private void createTableRes() {
        this.db.execSQL("CREATE TABLE IF NOT EXISTS responsaveis "
                + "(res_id INTEGER PRIMARY KEY, "
                + "res_nome TEXT NOT NULL, "
                + "res_senha TEXT NOT NULL, "
                + "res_email TEXT NOT NULL, "
                + "res_telefone TEXT(14) NOT NULL, "
                + "res_codigoGerado TEXT);");
    }

    public int inserir(Responsaveis resp) {
        Cursor cursor;
        String sql = "SELECT res_id FROM responsaveis WHERE res_id = '"+resp.getRes_id()+"';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int resultado = cursor.getCount();
        int novaId = resp.getRes_id();
        while (resultado > 0){
            novaId = novaId + 1;

            sql = "SELECT res_id FROM responsaveis WHERE res_id = '"+novaId+"';";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            resultado = cursor.getCount();
        }

        ContentValues valores = new ContentValues();
        valores.put("res_id", novaId);
        valores.put("res_nome", resp.getRes_nome());
        valores.put("res_senha", resp.getRes_senha());
        valores.put("res_email", resp.getRes_email());
        valores.put("res_telefone", resp.getRes_telefone());
        int a = (int) db.insert("responsaveis", null, valores);

        return a;
    }

    public int inserirFireBase(Responsaveis resp){
        ContentValues valores = new ContentValues();
        valores.put("res_id", resp.getRes_id());
        valores.put("res_senha", resp.getRes_senha());
        valores.put("res_email", resp.getRes_email());
        valores.put("res_telefone", resp.getRes_telefone());
        valores.put("res_nome", resp.getRes_nome());
        int a = (int) db.insert("responsaveis", null, valores);
        return a;

    }

    public int compararIdFirebase(int id){
        Cursor cursor;
        String sql = "SELECT res_id FROM responsaveis WHERE res_id = '"+id+"';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int retorno = cursor.getCount();
        return retorno;
    }
    public String recuperarEmail(Responsaveis resp, String email) {
        Cursor cursor;
        String sql = "SELECT res_email FROM responsaveis;";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String emailVerifica = cursor.getString(cursor.getColumnIndex("res_email"));
            if (email.equals(emailVerifica)) {
                return email;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }


    public String recuperarSenha(String senha, String email) {
        Cursor cursor;
        String sql = "SELECT res_senha FROM responsaveis WHERE res_email = '"+ email + "';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String senhaVerifica = cursor.getString(cursor.getColumnIndex("res_senha"));
            if (senha.equals(senhaVerifica)) {
                return senha;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    public Integer recuperarId(Responsaveis resp, String email) {
        Cursor cursor;
        String sql = "SELECT res_id FROM responsaveis WHERE res_email = '"+ email +"';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String numero = cursor.getString(cursor.getColumnIndex("res_id"));
        Integer id = Integer.parseInt(numero);
        cursor.close();
        return id;
    }

    public boolean emailExistente(String email) {
        Cursor cursor;
        String sql = "SELECT res_email FROM responsaveis";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String emailVerifica = cursor.getString(cursor.getColumnIndex("res_email"));
            if (email.equals(emailVerifica)) {
                return true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

    public Responsaveis retornaResp(int idResp) {
        Cursor cursor;
        String sql = "SELECT * FROM responsaveis WHERE res_id = '" + idResp + "';";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("res_id")));
        String nome = cursor.getString(cursor.getColumnIndex("res_nome"));
        String email = cursor.getString(cursor.getColumnIndex("res_email"));
        String telefone = cursor.getString(cursor.getColumnIndex("res_telefone"));
        String senha = cursor.getString(cursor.getColumnIndex("res_senha"));
        Responsaveis resp = new Responsaveis(id, nome, email, telefone, senha);
        cursor.close();
        return resp;
    }

    public void inserirCodigoResp(String codigo, int id) {
        db.execSQL("UPDATE responsaveis SET res_codigoGerado = '" + codigo + "' WHERE res_id=" + id + ";");
    }

    public boolean telefoneExistente(String telefone) {
        Cursor cursor;
        String sql = "SELECT res_telefone FROM responsaveis;";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String telefoneVerifica = cursor.getString(cursor.getColumnIndex("res_telefone"));
            if (telefone.equals(telefoneVerifica)) {
                return true;
            }
            cursor.moveToNext();
        }
        return false;
    }

    public int validaCodigo(String codigo) {
        Cursor cursor;
        String sql = "SELECT res_codigoGerado FROM responsaveis WHERE res_codigoGerado = '" + codigo + "';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int validacao = cursor.getCount();
        return validacao;
    }

    public String retornarCodigoPorId (int id){
        Cursor cursor;
        String sql= "SELECT res_codigoGerado FROM responsaveis WHERE res_id = '" +id+ "';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String codigo = cursor.getString(cursor.getColumnIndex("res_codigoGerado"));
        return codigo;
    }

    public Responsaveis retornaRespPorTelefone(String telefone) {
        Cursor cursor;
        String sql = "SELECT * FROM responsaveis WHERE res_telefone = '" + telefone + "';";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("res_id"));
        String nome = cursor.getString(cursor.getColumnIndex("res_nome"));
        String contato = cursor.getString(cursor.getColumnIndex("res_telefone"));
        String email = cursor.getString(cursor.getColumnIndex("res_email"));
        String codigo = cursor.getString(cursor.getColumnIndex("res_codigoGerado"));
        String senha = cursor.getString(cursor.getColumnIndex("res_senha"));
        Responsaveis responsaveis = new Responsaveis(id,nome,email,contato,senha,codigo);
        cursor.close();
        return responsaveis;
    }
}
