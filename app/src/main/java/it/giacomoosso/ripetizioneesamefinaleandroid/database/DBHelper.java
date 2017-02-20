package it.giacomoosso.ripetizioneesamefinaleandroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nardigabriele on 13/01/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private final static String NAME = "timer.db";
    private final static int VERSION = 1;

    public DBHelper(Context aContext) {

        super(aContext, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(tempiTabella.CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(tempiTabella.DROP_QUERY);
        sqLiteDatabase.execSQL(tempiTabella.CREATE_QUERY);

    }

}
