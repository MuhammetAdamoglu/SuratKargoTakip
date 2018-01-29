package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;



public class QueryCargoNumber {
    private static String CargoNumber;
    String message="empty";
    private int id;

    QueryCargoNumber(String CargoNumber,int id){
        this.id=id;
        QueryCargoNumber.CargoNumber =CargoNumber;
        new LoadApplications().execute();
    }

    private class LoadApplications extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                String url = "http://www.suratkargo.com.tr/kargoweb/bireysel.aspx?no="+CargoNumber;
                Document doc = Jsoup.connect(url).get();

                Elements e = doc.select("span[id=ASPxRoundPanel1_SLabelmesaj]");

                message=e.text();

            }catch (SecurityException ex) {
                message="empty";
            }catch (IOException e1) {
                message="empty";
            }

            return null;
        }

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(id==1){
                if(message.equals(""))
                    getData.here.TrueQuery();
                else
                    getData.here.FalseQuery();
            }else if(id==2){
                if(message.equals(""))
                    MainActivity.here.TrueQuery(CargoNumber);
                else
                    MainActivity.here.FalseQuery();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }
}
