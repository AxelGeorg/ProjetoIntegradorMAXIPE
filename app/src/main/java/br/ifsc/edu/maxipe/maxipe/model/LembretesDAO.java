package br.ifsc.edu.maxipe.maxipe.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class LembretesDAO extends DB {

    public LembretesDAO(Context context) {
        super(context);
        this.createTableLem();
    }

    private void createTableLem() {
        this.db.execSQL("CREATE TABLE IF NOT EXISTS lembretes "
                + "(lem_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "lem_fkAux INTEGER,"
                + "lem_fkRes INTEGER,"
                + "lem_tituloText TEXT NOT NULL, "
                + "lem_hora INTEGER NOT NULL, "
                + "lem_minuto INTEGER NOT NULL,"
                + "lem_dia INTEGER NOT NULL,"
                + "lem_mes INTEGER NOT NULL,"
                + "lem_ano INTEGER NOT NULL);");
    }

    public int inserirLembrete(Lembretes lem, Responsaveis resp, Auxiliados aux) {
        ContentValues valores = new ContentValues();
        valores.put("lem_tituloText", lem.getLem_tituloText());
        valores.put("lem_hora", lem.getLem_hora());
        valores.put("lem_minuto", lem.getLem_minuto());
        valores.put("lem_dia", lem.getLem_dia());
        valores.put("lem_mes", lem.getLem_mes());
        valores.put("lem_ano", lem.getLem_ano());
        valores.put("lem_fkRes", resp.getRes_id());
        valores.put("lem_fkAux", aux.getAux_id());
        int a = (int) db.insert("lembretes", null, valores);
        return a;
    }

    public List<Lembretes> recuperaListaLembretes(int idRes, int idAux) {
        Cursor cursor;
        ArrayList<Lembretes> listaLembretes = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM lembretes WHERE lem_fkRes = " + idRes + " AND lem_fkAux = " + idAux + ";", null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tituloTxt = cursor.getString(cursor.getColumnIndex("lem_tituloText"));
            int id = cursor.getInt(cursor.getColumnIndex("lem_id"));
            int hora = cursor.getInt(cursor.getColumnIndex("lem_hora"));
            int minuto = cursor.getInt(cursor.getColumnIndex("lem_minuto"));
            int dia = cursor.getInt(cursor.getColumnIndex("lem_dia"));
            int mes = cursor.getInt(cursor.getColumnIndex("lem_mes"));
            int ano = cursor.getInt(cursor.getColumnIndex("lem_ano"));
            Lembretes lembretes = new Lembretes(id, tituloTxt, hora, minuto, dia, mes, ano);
            listaLembretes.add(lembretes);
            cursor.moveToNext();
        }
        if (idAux != 0) {
            cursor.close();
            return listaLembretes;
        } else {
            cursor.close();
            return null;
        }
    }

    public void excluiLembrete(int id) {
        db.execSQL("DELETE FROM lembretes WHERE lem_id = '" + id + "';");
    }

    public void updateInfosDAO(Lembretes lem) {
        String comandoSql = "UPDATE lembretes SET lem_hora = " + lem.getLem_hora() + "," +
                "lem_minuto = " + lem.getLem_minuto() + ", " +
                "lem_dia = " + lem.getLem_dia() + ", " +
                "lem_mes = " + lem.getLem_mes() + ", " +
                "lem_ano = " + lem.getLem_ano() + ", " +
                "lem_tituloText = '" + lem.getLem_tituloText() + "' " +
                "WHERE " +
                "lem_id = " + lem.getLem_id() + "";

        db.execSQL(comandoSql);
    }

    public boolean lembreteExistenteEdicao(int dia, int mes, int ano, int hora, int minuto, String titulo) {
        Cursor cursor;
        String sql = "SELECT * FROM lembretes WHERE lem_dia = " + dia + " AND " +
                "lem_mes = " + mes + " AND " +
                "lem_ano = " + ano + " AND " +
                "lem_hora = " + hora + " AND " +
                "lem_minuto = " + minuto + " AND " +
                "lem_tituloText = '" + titulo + "' LIMIT 1;";
        cursor = db.rawQuery(sql, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public boolean lembreteExistenteInclusao(int dia, int mes, int ano, int hora, int minuto) {
        Cursor cursor;
        String sql = "SELECT * FROM lembretes WHERE lem_dia = " + dia + " AND " +
                "lem_mes = " + mes + " AND " +
                "lem_ano = " + ano + " AND " +
                "lem_hora = " + hora + " AND " +
                "lem_minuto = " + minuto + " " +
                "LIMIT 1;";
        cursor = db.rawQuery(sql, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public int idUltimoLembrete() {
        Cursor cursor;
        String sql = "SELECT lem_id FROM lembretes ORDER BY lem_id DESC";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("lem_id"));
        cursor.close();
        return id;
    }

    public int retornaTamanhoLista() {
        Cursor cursor;
        String sql = "SELECT * FROM lembretes";
        cursor = db.rawQuery(sql, null, null);
        cursor.moveToFirst();
        cursor.close();
        return cursor.getCount();
    }

    public Lembretes retornaLembretePeloId(int idresp, int idAux) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM lembretes WHERE lem_fkRes = " + idresp + " AND lem_fkAux = " + idAux + ";", null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            if (idAux != 0) {
                String tituloTxt = cursor.getString(cursor.getColumnIndex("lem_tituloText"));
                int id = cursor.getInt(cursor.getColumnIndex("lem_id"));
                int hora = cursor.getInt(cursor.getColumnIndex("lem_hora"));
                int minuto = cursor.getInt(cursor.getColumnIndex("lem_minuto"));
                int dia = cursor.getInt(cursor.getColumnIndex("lem_dia"));
                int mes = cursor.getInt(cursor.getColumnIndex("lem_mes"));
                int ano = cursor.getInt(cursor.getColumnIndex("lem_ano"));
                Lembretes lembretes = new Lembretes(id, tituloTxt, hora, minuto, dia, mes, ano);
                cursor.close();
                return lembretes;
            } else {
                cursor.close();
                return null;
            }
        } else {
            cursor.close();
            return null;
        }
    }
}
