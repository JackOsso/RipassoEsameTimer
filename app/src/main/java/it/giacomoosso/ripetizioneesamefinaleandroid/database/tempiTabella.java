package it.giacomoosso.ripetizioneesamefinaleandroid.database;

import android.provider.BaseColumns;

/**
 * Created by Jack on 13/02/17.
 */
//tabella per la gestione degli articoli off line
public class tempiTabella implements BaseColumns{
    public static final String TABLE_NAME = "tempi";
    public static final String TEMPO = "tempo";
    public static final String DATA = "data";

    public static final String CREATE_QUERY = "CREATE TABLE " +
            TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TEMPO + " TEXT NOT NULL, " +
            DATA + " TEXT"+
            ");";

    public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
