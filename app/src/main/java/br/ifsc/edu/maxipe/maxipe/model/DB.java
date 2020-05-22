package br.ifsc.edu.maxipe.maxipe.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DB {

    public static final String NAMEDB = "maxipe";
    private static final int VERSION =1;
    SQLiteDatabase db;
    Context mCoxtext;

    public DB(Context context) {
        mCoxtext = context;
        db = context.openOrCreateDatabase(NAMEDB, Context.MODE_PRIVATE, null);

    }


}
