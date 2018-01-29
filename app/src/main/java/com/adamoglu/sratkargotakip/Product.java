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
public class Product extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MainActivity mainActivity;
    Product(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    TextView cargoPiece,cargoLoginSystem,cargoUnit,cargoDate,cargoDelivery,cargoInterlocutor,cargoAvailability,cargoFinishing,cargoBarcode;
    ConstraintLayout loading;
    ScrollView scrollView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.product, container, false);

        cargoPiece=view.findViewById(R.id.cargoPiece);
        cargoLoginSystem=view.findViewById(R.id.cargoLoginSystem);
        cargoUnit=view.findViewById(R.id.cargoUnit);
        cargoDate=view.findViewById(R.id.cargoDate);
        cargoDelivery=view.findViewById(R.id.cargoDelivery);
        cargoInterlocutor=view.findViewById(R.id.cargoInterlocutor);
        cargoAvailability=view.findViewById(R.id.cargoAvailability);
        cargoFinishing=view.findViewById(R.id.cargoFinishing);
        cargoBarcode=view.findViewById(R.id.cargoBarcode);

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


        cargoPiece.setText(mainActivity.dataBase.getProduc("Parça"));
        cargoLoginSystem.setText(mainActivity.dataBase.getProduc("Sisteme Giriş"));
        cargoUnit.setText(mainActivity.dataBase.getProduc("Birim"));
        cargoDate.setText(mainActivity.dataBase.getProduc("Tarih"));
        cargoDelivery.setText(mainActivity.dataBase.getProduc("Teslimat"));
        cargoInterlocutor.setText(mainActivity.dataBase.getProduc("Muhatap"));
        cargoAvailability.setText(mainActivity.dataBase.getProduc("Durumu"));
        cargoFinishing.setText(mainActivity.dataBase.getProduc("Son İşlem"));
        cargoBarcode.setText(mainActivity.dataBase.getProduc("Barkod"));
    }

    boolean ControlData(){
        return mainActivity.dataBase.ControlData(MainActivity.CargoNumber);
    }
    boolean ControlNetwork(){
        return mainActivity.getData.ControlNetwork(getData.Product);
    }
}
