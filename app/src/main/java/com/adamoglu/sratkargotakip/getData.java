package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;


public class getData {

    MainActivity mainActivity;
    String CargoNumber;
    static getData here;

    @SuppressLint("NewApi")
    void start(final MainActivity mainActivity,String CargoNumber){

        here=this;

        this.mainActivity=mainActivity;
        this.CargoNumber=CargoNumber;

        new QueryCargoNumber(CargoNumber,1);

    }

    void TrueQuery(){
        mainActivity.ConnectLine.setBackgroundColor(Color.parseColor("#E53935"));
        new LoadApplications().execute();
    }

    void FalseQuery(){
        Toast.makeText(mainActivity, "Sorgu Bulunamadı", Toast.LENGTH_SHORT).show();
    }

    private HashMap<String,String> product = new HashMap<>();

    void getProduct(Document doc){
        Elements e = doc.select("table[id=ASPxRoundPanel1_gridviewparca]");

        int falseCount=0;

        for(int i = 0;true; i++){
            title = e.select("th").eq(i).text();
            content = e.select("td").eq(i).text();

            product.put(title,content);

            if(title.equals("") && content.equals("")){
                if(++falseCount==3){
                    break;
                }
            }else
                falseCount=0;
        }
    }

    private HashMap<String,String> sender = new HashMap<>();

    void getSender(Document doc){
        Elements e = doc.select("div[id=ASPxRoundPanel1_Panel2]");

        int falseCount=0;

        for(int i = 0;true; i++){
            Elements e2=e.select("tr").eq(i);
            title = e2.select("td").eq(0).text();
            content = e2.select("td").eq(1).text();

            sender.put(title,content);

            if(title.equals("") && content.equals("")){
                if(++falseCount==3){
                    break;
                }
            }else
                falseCount=0;
        }
    }

    private HashMap<String,String> buyer = new HashMap<>();

    void getBuyer(Document doc){
        Elements e = doc.select("div[id=ASPxRoundPanel1_Panel3]");

        int falseCount=0;

        for(int i = 0;true; i++){
            Elements e2=e.select("tr").eq(i);
            title = e2.select("td").eq(0).text();
            content = e2.select("td").eq(1).text();

            buyer.put(title,content);

            if(title.equals("") && content.equals("")){
                if(++falseCount==3){
                    break;
                }
            }else
                falseCount=0;
        }
    }

    private String title,content;

    @SuppressLint({"StaticFieldLeak", "NewApi"})
    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String url = "http://www.suratkargo.com.tr/kargoweb/bireysel.aspx?no="+CargoNumber;
                Document doc = Jsoup.connect(url).get();

                getBuyer(doc);
                getSender(doc);
                getProduct(doc);

            }catch (SecurityException ex) {
                new LoadApplications().execute();
            }catch (IOException e1) {
                new LoadApplications().execute();
            }

            return null;
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(product.size()!=0){
                mainActivity.dataBase.Save(MainActivity.CargoNumber,product,sender,buyer);
                mainActivity.ConnectTrue();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    static final int Product=0;
    static final int Buyer=1;
    static final int Sender=2;

    boolean ControlNetwork(int id){
        if(id==Product)
            if(product.size()==0)
                return false;
        else if(id==Buyer)
            if(buyer.size()==0)
                return false;
        else if(id==Sender)
            if(sender.size()==0)
                return false;
        else return false;

        return true;
    }
}
