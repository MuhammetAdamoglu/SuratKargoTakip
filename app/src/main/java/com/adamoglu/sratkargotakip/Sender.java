package com.adamoglu.sratkargotakip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Abra on 30.12.2017.
 */

@SuppressLint("ValidFragment")
public class Sender extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MainActivity mainActivity;
    Sender(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    TextView senderName,senderAdress,senderPhone,senderCikisSubesi,senderCikisSubeTel;

    ConstraintLayout loading;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.sender, container, false);

        senderName=view.findViewById(R.id.senderName);
        senderAdress=view.findViewById(R.id.senderAdress);
        senderPhone=view.findViewById(R.id.senderPhone);
        senderCikisSubesi=view.findViewById(R.id.senderCikisSubesi);
        senderCikisSubeTel=view.findViewById(R.id.senderCikisSubeTel);


        scrollView=view.findViewById(R.id.scrollView);
        loading =view.findViewById(R.id.loading);

        loading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        Load();

        return view;
    }

    @SuppressLint("SetTextI18n")
    void  Load(){

        if(getContext()==null)
            return;



        if(!ControlData()){
            scrollView.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            return;
        }else {

            scrollView.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }


        senderName.setText(mainActivity.dataBase.getSender("Ünvan/Ad Soyad"));
        senderAdress.setText(mainActivity.dataBase.getSender("Adres"));
        senderPhone.setText(mainActivity.dataBase.getSender("Telefon"));
        senderCikisSubesi.setText(mainActivity.dataBase.getSender("Çıkış Şubesi"));
        senderCikisSubeTel.setText(mainActivity.dataBase.getSender("Çıkış Şubesi Tel."));

    }

    boolean ControlData(){
        return mainActivity.dataBase.ControlData(MainActivity.CargoNumber);
    }
    boolean ControlNetwork(){
        return mainActivity.getData.ControlNetwork(getData.Sender);
    }
}
