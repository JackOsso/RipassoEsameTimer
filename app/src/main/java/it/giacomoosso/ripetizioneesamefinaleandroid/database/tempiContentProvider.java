package it.giacomoosso.ripetizioneesamefinaleandroid.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Jack on 13/02/17.
 */

public class tempiContentProvider extends ContentProvider {
    private static final String AUTHORITY = "it.giacomoosso.fogliogoriziano.data.tempiContentProvider";
    private static final String BASE_PATH_TEMPI="tempi";

    //Uri Matcher Configuration
    private static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    private static final int TEMPI=10;
    private static final int TEMPI_ID=20;

    public static final Uri TEMPI_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_TEMPI);

    static {
        // content://authority/tempi
        uriMatcher.addURI(AUTHORITY, BASE_PATH_TEMPI, TEMPI);
        // content://authority/tempi/:id
        uriMatcher.addURI(AUTHORITY, BASE_PATH_TEMPI + "/#", TEMPI_ID);
    }

    private DBHelper dbhepler;

    @Override
    public boolean onCreate() {
        dbhepler = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType= uriMatcher.match(uri);
        switch(uriType){
            case TEMPI:
                queryBuilder.setTables(tempiTabella.TABLE_NAME);
                break;
            case TEMPI_ID:
                queryBuilder.setTables(tempiTabella.TABLE_NAME);
                queryBuilder.appendWhere(String.format("%s=%s",tempiTabella._ID,uri.getLastPathSegment()));
                break;
            default:
                throw new IllegalArgumentException("Uri not supported"+uri);

        }

        SQLiteDatabase sqLiteDatabase = dbhepler.getReadableDatabase();
        Cursor cursor = queryBuilder.query(sqLiteDatabase, projection, selection, selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {


        SQLiteDatabase sqLiteDatabase= dbhepler.getWritableDatabase();
        long insertedId=0;

        int uriType=uriMatcher.match(uri);
        switch (uriType){
            case TEMPI:
                insertedId=sqLiteDatabase.insert(tempiTabella.TABLE_NAME,null,values);
                break;
            default:
                throw new IllegalArgumentException("Uri not supported"+uri);

        }
        sqLiteDatabase.close();
        if(insertedId>0){
            getContext().getContentResolver().notifyChange(uri,null);
            return Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_TEMPI+ "/" + insertedId);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbhepler.getWritableDatabase();
        int deletedRows;

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case TEMPI:
                deletedRows = database.delete(tempiTabella.TABLE_NAME, selection, selectionArgs);
                break;
            case TEMPI_ID:
            if (TextUtils.isEmpty(selection)) {
                deletedRows = database.delete(tempiTabella.TABLE_NAME,
                        tempiTabella._ID + " = " + uri.getLastPathSegment(),
                        null);
            } else {
                deletedRows = database.delete(tempiTabella.TABLE_NAME,
                        tempiTabella._ID + " = " + uri.getLastPathSegment() + " and " + selection,
                        selectionArgs);
            }
                break;
            default:

                throw new IllegalArgumentException("Uri not supported: " + uri);
        }
        database.close();

        if (deletedRows > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = dbhepler.getWritableDatabase();
        int rowsUpdated = 0;

        switch(uriType) {
            case TEMPI:
                rowsUpdated = db.update(tempiTabella.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TEMPI_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(tempiTabella.TABLE_NAME, values,
                            tempiTabella._ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(tempiTabella.TABLE_NAME, values,
                            tempiTabella._ID + "=" + id, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        if(rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
