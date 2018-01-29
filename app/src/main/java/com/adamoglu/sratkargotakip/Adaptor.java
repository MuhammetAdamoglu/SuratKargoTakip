package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptor extends ArrayAdapter<String> {
    //Bu Sınıf, Verileri ListView e Aktarır

    private final Context context;
    private final ArrayList<String> values_Numbers;
    private final ArrayList<String> values_Locations;


    public Adaptor(Context context, ArrayList<String> values_Numbers, ArrayList<String> values_Locations) {
        super(context, R.layout.listview, values_Numbers);
        this.context = context;
        this.values_Numbers = values_Numbers;
        this.values_Locations = values_Locations;
    }


    @NonNull
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        assert inflater != null;
        rowView = inflater.inflate(R.layout.listview, parent, false);

        TextView cargoNumber = rowView.findViewById(R.id.Et_cargoNumber);


        cargoNumber.setText(values_Numbers.get(position));

        return rowView;
    }

}