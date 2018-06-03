package com.smart.andyradulescu.parkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import model.DBService;
import model.DbHelper;
import model.ParkingDTO;
import server.service.GetDataFromBackendThread;

import static helper.SavedItems.debug;

public class Main2Activity extends AppCompatActivity {

    GetDataFromBackendThread info;
    private volatile List<ParkingDTO> parkingPlaces;
    private TextView mTvParkingPlaces;

    //TODO: add the label to show how many available places are in the data packing lot.
    private volatile boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String numberOfPlaces = "Number of available places: " + String.valueOf(nr);
        mTvParkingPlaces.setText(numberOfPlaces);
    }

    /**
     * Goes to the parking section.
     *
     * @param v view for the onClick method.
     */
    public void showParking(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Main3Activity.class);
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