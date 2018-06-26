package com.smart.andyradulescu.parkingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import helper.SavedItems;
import server.service.GetDataFromBackendThread;

public class Section1Activity extends Activity implements SavedItems {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_section1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetDataFromBackendThread.setRunning(false);
    }
}


