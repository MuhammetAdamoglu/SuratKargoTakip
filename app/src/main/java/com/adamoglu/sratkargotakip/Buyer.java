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

@SuppressLint("ValidFragment")
public class Buyer extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MainActivity mainActivity;
    Buyer(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    TextView buyerName,buyerAdress,buyerPhone,buyerVarisSubesi,buyerVarisSubeTel;

    ConstraintLayout loading;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.buyer, container, false);

        buyerName=view.findViewById(R.id.buyerName);
        buyerAdress=view.findViewById(R.id.buyerAdress);
        buyerPhone=view.findViewById(R.id.buyerPhone);
        buyerVarisSubesi=view.findViewById(R.id.buyerVarisSubesi);
        buyerVarisSubeTel=view.findViewById(R.id.buyerVarisSubeTel);


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


        buyerName.setText(mainActivity.dataBase.getBuyer("Ünvan/Ad Soyad"));
        buyerAdress.setText(mainActivity.dataBase.getBuyer("Adres"));
        buyerPhone.setText(mainActivity.dataBase.getBuyer("Telefon"));
        buyerVarisSubesi.setText(mainActivity.dataBase.getBuyer("Varış Şubesi"));
        buyerVarisSubeTel.setText(mainActivity.dataBase.getBuyer("Varış Şubesi Tel."));

    }

    boolean ControlData(){
        return mainActivity.dataBase.ControlData(MainActivity.CargoNumber);
    }
    boolean ControlNetwork(){
        return mainActivity.getData.ControlNetwork(getData.Buyer);
    }

}
