package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;


public class DataBase {

    private static SQLite myDb;
    private Context context;
    private MainActivity mainActivity;


    DataBase(MainActivity mainActivity){
        context=mainActivity.getApplicationContext();
        this.mainActivity=mainActivity;
        myDb=new SQLite(context);
    }

    void Save(String CargoNumber, HashMap<String,String> product,HashMap<String,String> sender,HashMap<String,String> buyer){

        StringBuilder P_key = new StringBuilder();
        StringBuilder P_value = new StringBuilder();
        for (String s : product.keySet()) {

            P_key.append(",").append(s);
            P_value.append(",").append(product.get(s));
        }

        StringBuilder S_key = new StringBuilder();
        StringBuilder S_value = new StringBuilder();
        for (String s : sender.keySet()) {

            S_key.append(",").append(s);
            S_value.append(",").append(sender.get(s));
        }

        StringBuilder B_key = new StringBuilder();
        StringBuilder B_value = new StringBuilder();
        for (String s : buyer.keySet()) {

            B_key.append(",").append(s);
            B_value.append(",").append(buyer.get(s));
        }

        String cargoName=myDb.getCargoName(CargoNumber);
        myDb.DeleteCargo(CargoNumber);
        myDb.Add(CargoNumber,cargoName, P_key.toString(), P_value.toString(), S_key.toString(), S_value.toString(), B_key.toString(), B_value.toString());
    }


    private HashMap<String,String> product,sender,buyer;


    @SuppressLint("SetTextI18n")
    void getSaveData(String CargoNumber){


        String[] keys=myDb.getKey(CargoNumber);
        String[] values=myDb.getValue(CargoNumber);

        if(ControlData(CargoNumber)){
            product=Splid(keys[getData.Product],values[getData.Product]);
            buyer=Splid(keys[getData.Buyer],values[getData.Buyer]);
            sender=Splid(keys[getData.Sender],values[getData.Sender]);

        }

    }

    private HashMap<String,String> Splid(String key, String value){
        HashMap<String,String> hashMap=new HashMap<>();

        String[] keys = key.split(",");
        String[] values = value.split(",");


        for (int i=0; i<values.length; i++)
            try {
                hashMap.put(keys[i],values[i]);
            }catch (Exception ignored){}



        return hashMap;
    }

    String getProduc(String key){
        String value="";
        try {
            value=product.get(key);
        }catch (Exception ignored){
            Toast.makeText(context, "Girdi", Toast.LENGTH_SHORT).show();
        }
        return value;
    }
    String getBuyer(String key){
        String value="";
        try {
            value=buyer.get(key);
        }catch (Exception ignored){}
        return value;
    }
    String getSender(String key){
        String value="";
        try {
            value=sender.get(key);
        }catch (Exception ignored){}
        return value;
    }



    boolean ControlData(String cargoNumber){
        return myDb.ControlCargo(cargoNumber);
    }
}
