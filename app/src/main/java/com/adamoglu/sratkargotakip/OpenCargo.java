package com.adamoglu.sratkargotakip;

import android.content.Context;



class OpenCargo {
    OpenCargo(String cargoNumber, MainActivity mainActivity, getData getData){

        MainActivity.CargoNumber =cargoNumber;
        getData.start(mainActivity,cargoNumber);

        mainActivity.CargoOpened();
    }
}
