package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class AlertDialog {
    AlertDialog(final MainActivity mainActivity, ListView listView){

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Show(mainActivity,mainActivity.cargos.getCargos().get(i));

                return true;
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void Show(final MainActivity mainActivity, final String CargoNumber){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mainActivity);

        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.alertdialog, null);

        final EditText et=  dialoglayout.findViewById(R.id.editText_ChangeCargoName);
        final TextView tv=  dialoglayout.findViewById(R.id.textView);


        builder.setView(dialoglayout);

        final String cargoName=mainActivity.myDb.getCargoName(CargoNumber);
        String buttonName;

        if(cargoName.equals(CargoNumber)){
            tv.setText(CargoNumber+" İçin İsim Ver");
            buttonName="İsim Ver";
        }else {
            tv.setText(CargoNumber+" İçin İsmi Değiştir");
            et.setText(cargoName);
            buttonName="İsim Güncelle";
        }

        builder.setPositiveButton(buttonName,new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){
                if(!et.getText().toString().equals("")){
                    mainActivity.myDb.UpdataCargoName(CargoNumber,et.getText().toString());
                }else {
                    mainActivity.myDb.UpdataCargoName(CargoNumber,CargoNumber);
                }

                mainActivity.cargos.NotiftSetDataChanged(mainActivity);
                mainActivity.cargoName.setText(mainActivity.myDb.getCargoName(MainActivity.CargoNumber));
            }

        });


        builder.setNegativeButton("Sil",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){
                mainActivity.myDb.UpdataCargoName(CargoNumber,CargoNumber);
                mainActivity.cargos.NotiftSetDataChanged(mainActivity);
                mainActivity.cargoName.setText(mainActivity.myDb.getCargoName(MainActivity.CargoNumber));
            }

        });


        builder.setNeutralButton("İptal",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int id){

            }

        });


        builder.show();
    }
}
