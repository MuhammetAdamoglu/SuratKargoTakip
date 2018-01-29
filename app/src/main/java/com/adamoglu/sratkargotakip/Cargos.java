package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;



class Cargos {
    static private ArrayList<String> cargos=new ArrayList<>();
    static private ArrayList<String> cargo_names=new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private Adaptor adaptor;
    private Cursor res;

    void start(Context context){
        getData(context);

        adaptor=new Adaptor(context,cargo_names,null);
        MainActivity.listView.setAdapter(adaptor);
    }

    private void getData(Context context){
        SQLite myDb = new SQLite(context);
        cargos.clear();
        cargo_names.clear();

        res= myDb.getAllData();

        while (res.moveToNext()){
            cargos.add(res.getString(0));
            cargo_names.add(res.getString(1));
        }

    }

    void NotiftSetDataChanged(Context context){
        getData(context);
        adaptor.notifyDataSetChanged();
    }

    ArrayList<String> getCargos(){
        return cargos;
    }

}
