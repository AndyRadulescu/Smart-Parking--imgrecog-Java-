package com.smart.andyradulescu.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import model.DBService;
import model.DbHelper;
import model.ParkingDTO;
import server.service.GetDataFromBackendThread;

import static helper.SavedItems.debug;

public class Main2Activity extends Activity {

    GetDataFromBackendThread info;
    private volatile List<ParkingDTO> parkingPlaces;
    private TextView mTvParkingPlaces;

    private volatile boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
    }

    @Override
    protected void onResume() {
        Log.d(debug, "entered");
        isRunning = true;
        info = new GetDataFromBackendThread(this);
        new Thread(info).start();
        super.onResume();
        DbHelper mDbHelper = new DbHelper(this);
        mTvParkingPlaces = findViewById(R.id.numberOfPlaces);
        parkingPlaces = DBService.getInfoFromDb(mDbHelper);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    parkingPlaces = info.parkingPlaces;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateParkingNumber(mTvParkingPlaces, parkingPlaces);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Updates the parking places number.
     *
     * @param mTvParkingPlaces the text View.
     * @param parkingPlaces
     */
    private void updateParkingNumber(TextView mTvParkingPlaces, List<ParkingDTO> parkingPlaces) {
        int nr = 0;
        for (ParkingDTO item : parkingPlaces) {
            if (item.getAvailability() == 1) {
                nr++;
            }
        }
        String numberOfPlaces = "places: " + String.valueOf(nr);
        mTvParkingPlaces.setText(numberOfPlaces);
    }

    /**
     * Goes to the parking section1.
     *
     * @param v view for the onClick method.
     */
    public void showParking1(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Section1Activity.class);
        startActivity(nextFrame);
        isRunning = false;
    }

    /**
     * Goes to the parking section2.
     *
     * @param v view for the onClick method.
     */
    public void showParking2(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Section2Activity.class);
        startActivity(nextFrame);
        isRunning = false;
    }

    /**
     * Goes to the parking section3.
     *
     * @param v view for the onClick method.
     */
    public void showParking3(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Section3Activity.class);
        startActivity(nextFrame);
        isRunning = false;
    }

    /**
     * Goes to the parking section4.
     *
     * @param v view for the onClick method.
     */
    public void showParking4(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Section4Activity.class);
        startActivity(nextFrame);
        isRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetDataFromBackendThread.setRunning(false);
        isRunning = false;
    }
}