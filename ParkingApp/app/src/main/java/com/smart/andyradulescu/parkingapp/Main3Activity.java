package com.smart.andyradulescu.parkingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import helper.SavedItems;
import server.service.GetDataFromBackendThread;

public class Main3Activity extends AppCompatActivity implements SavedItems {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(debug, "mortii ma-sii din onCreate");
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetDataFromBackendThread.setRunning(false);
    }
}


