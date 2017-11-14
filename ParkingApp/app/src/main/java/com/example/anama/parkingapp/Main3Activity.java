package com.example.anama.parkingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import server.SavedItems;

public class Main3Activity extends AppCompatActivity implements SavedItems {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }
}


