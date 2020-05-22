package br.ifsc.edu.maxipe.maxipe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.ifsc.edu.maxipe.maxipe.controller.AuxiliadoController;

public class AuxiliadoDAO extends DB {


    public AuxiliadoDAO(Context context) {
        super(context);
        createTableAux();
    }

    private void createTableAux() {
        this.db.execSQL("CREATE TABLE IF NOT EXISTS auxiliados ("
                + "aux_id INTEGER PRIMARY KEY,"
                + "aux_fkRes INTEGER NOT NULL,"
                + "aux_codigo TEXT,"
                + "aux_nome TEXT NOT NULL,"
                + "aux_contato TEXT NOT NULL,"
                + "aux_email TEXT NOT NULL);");
    }

    public int inserirAux(Auxiliados auxiliados) {
        Cursor cursor;
        String sql = "SELECT aux_id FROM auxiliados WHERE aux_id = '"+auxiliados.getAux_id()+"';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int resultado = cursor.getCount();
        int novaId = auxiliados.getAux_id();
        while (resultado > 0){
            novaId = novaId + 1;

            sql = "SELECT aux_id FROM auxiliados WHERE aux_id = '"+novaId+"';";
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            resultado = cursor.getCount();
        }

        ContentValues valores = new ContentValues();
        valores.put("aux_id", novaId);
        valores.put("aux_nome", auxiliados.getAux_nome());
        valores.put("aux_email", auxiliados.getAux_email());
        valores.put("aux_fkRes", auxiliados.getAux_fkRes());
        valores.put("aux_contato", auxiliados.getAux_contato());
        int a = (int) db.insert("auxiliados", null, valores);
        return a;
    }

    public Auxiliados retornaAux(String codigo) {
        Cursor cursor;
        String sql = "SELECT * FROM auxiliados WHERE aux_codigo = '" + codigo + "';";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("aux_id"));
        String nome = cursor.getString(cursor.getColumnIndex("aux_nome"));
        String contato = cursor.getString(cursor.getColumnIndex("aux_contato"));
        String email = cursor.getString(cursor.getColumnIndex("aux_email"));
        int idResp = cursor.getInt(cursor.getColumnIndex("aux_fkRes"));
        Auxiliados auxiliados = new Auxiliados(id, nome, email, contato, idResp);
        cursor.close();
        return auxiliados;
    }

    public Auxiliados retornaAuxPorContato(String contato) {
        Cursor cursor;
        String sql = "SELECT * FROM auxiliados WHERE aux_contato = '" + contato + "';";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("aux_id"));
        String nome = cursor.getString(cursor.getColumnIndex("aux_nome"));
        String telefone = cursor.getString(cursor.getColumnIndex("aux_contato"));
        String email = cursor.getString(cursor.getColumnIndex("aux_email"));
        int idResp = cursor.getInt(cursor.getColumnIndex("aux_fkRes"));
        String codigo = cursor.getString(cursor.getColumnIndex("aux_codigo"));
        Auxiliados auxiliados = new Auxiliados(id,idResp,codigo,nome,email,telefone);
        cursor.close();
        return auxiliados;
    }

    public String retornaNomeAux(int idRes) {
        Cursor cursor;
        String sql = "SELECT aux_nome FROM auxiliados WHERE aux_fkRes = '" + idRes + "'";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String nome = cursor.getString(cursor.getColumnIndex("aux_nome"));
            cursor.close();
            return nome;
        } else {
            cursor.close();
            return null;
        }
    }

    public void insereCodigo(String codigo, int id) {
        db.execSQL("UPDATE auxiliados SET aux_codigo = '" + codigo + "' WHERE aux_id = " + id + "");
    }

    public int ultimoAuxCadastrado() {
        Cursor cursor;
        String sql = "SELECT aux_id FROM auxiliados ORDER BY aux_id DESC";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("aux_id"));
        cursor.close();
        return id;
    }

    public boolean emailExistente(String email) {
        Cursor cursor;
        String sql = "SELECT aux_email FROM auxiliados WHERE aux_email = '" + email + "'";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String emailVerifica = cursor.getString(cursor.getColumnIndex("aux_email"));
            if (email.equals(emailVerifica)) {
                return true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        return false;
    }

    public boolean telefoneExistente(String telefoneEdit) {
        Cursor cursor;
        String sql = "SELECT aux_contato FROM auxiliados WHERE aux_contato = '" + telefoneEdit + "'";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean codigoExistente(String toString) {
        Cursor cursor;
        String sql = "SELECT aux_codigo FROM auxiliados WHERE aux_codigo = '"+ toString +"'";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public int retornaAuxPeloFk(int idRes) {
        Cursor cursor;
        String sql = "SELECT aux_id FROM auxiliados WHERE aux_fkRes = " + idRes + "";
        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int id = cursor.getInt(cursor.getColumnIndex("aux_id"));
            cursor.close();
            return id;
        } else {
            cursor.close();
            return 0;
        }
    }


    public Auxiliados retornaObjAuxPeloFk(int idRes) {
        Cursor cursor;
        String sql = "SELECT * FROM auxiliados WHERE aux_fkRes = " + idRes + "";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int id = cursor.getInt(cursor.getColumnIndex("aux_id"));
            String nome  = cursor.getString(cursor.getColumnIndex("aux_nome"));
            String codigo  = cursor.getString(cursor.getColumnIndex("aux_codigo"));
            String contato  = cursor.getString(cursor.getColumnIndex("aux_contato"));
            String email  = cursor.getString(cursor.getColumnIndex("aux_email"));
            Auxiliados auxiliados = new Auxiliados(id, nome, email, contato, idRes);
            cursor.close();
            return auxiliados;
        } else {
            cursor.close();
            return null;
        }
    }

    public String retornaCodigo(Integer res_id) {
        Cursor cursor;
        String sql = "SELECT aux_codigo FROM auxiliados WHERE aux_fkRes = '" + res_id + "'";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String codigo = cursor.getString(cursor.getColumnIndex("aux_codigo"));
            cursor.close();
            return codigo;
        } else {
            cursor.close();
            return null;
        }
    }

    public int inserirAuxFirebase(Auxiliados aux){
        ContentValues valores = new ContentValues();
        valores.put("aux_id", aux.getAux_id());
        valores.put("aux_codigo", aux.getAux_codigo());
        valores.put("aux_email", aux.getAux_email());
        valores.put("aux_telefone", aux.getAux_contato());
        valores.put("aux_nome", aux.getAux_nome());
        valores.put("aux_fkRes", aux.getAux_fkRes());
        int a = (int) db.insert("responsaveis", null, valores);
        return a;
    }

    public int insereCodigoNaTabela(String codigo, int idUltimoAUx) {
        Cursor cursor;
        String sql = "UPDATE auxiliados SET aux_codigo = '"+codigo+"' WHERE aux_id="+idUltimoAUx+";";
        cursor = db.rawQuery(sql, null);
        int resultado = cursor.getCount();
        return resultado;
    }
}
