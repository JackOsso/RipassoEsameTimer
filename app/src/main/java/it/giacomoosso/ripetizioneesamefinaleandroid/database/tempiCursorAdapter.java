package it.giacomoosso.ripetizioneesamefinaleandroid.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import it.giacomoosso.ripetizioneesamefinaleandroid.R;



/**
 * Created by fedom on 18/01/2017.
 */

public class tempiCursorAdapter extends CursorAdapter {

    public tempiCursorAdapter(Context context, Cursor cursor){

        super(context,cursor,false);
    }
    class ViewHolder{

        TextView txt_Numero , txt_Data, txt_Tempo;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_tempo,null);

        ViewHolder vHolder= new ViewHolder();
        vHolder.txt_Numero=(TextView)view.findViewById(R.id.n_tempo);
        vHolder.txt_Data=(TextView)view.findViewById(R.id.data_tempo);
        vHolder.txt_Tempo=(TextView)view.findViewById(R.id.tempo_tempo);
        view.setTag(vHolder);

        return view;

    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        //viewHolder.mImageview;
        viewHolder.txt_Numero.setText(cursor.getInt(cursor.getColumnIndex(tempiTabella._ID)));
        viewHolder.txt_Tempo.setText(cursor.getString(cursor.getColumnIndex(tempiTabella.TEMPO)));
        viewHolder.txt_Data.setText(cursor.getString(cursor.getColumnIndex(tempiTabella.DATA)));

    }

}
