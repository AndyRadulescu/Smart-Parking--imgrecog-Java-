package com.smart.andyradulescu.parkingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import helper.ConnectionDetector;
import helper.SavedItems;
import model.DbHelper;
import model.ParkingDTO;
import server.ClientThread;
import server.Message;

public class MainActivity extends AppCompatActivity implements SavedItems {

    private static List<ParkingDTO> parkingArray = new ArrayList<>();
    DbHelper mDbHelper;
    private boolean isConnectedToServer = false;
    private Handler mHandler;

    //TODO: Stylise the UI si it fits for every android device.
    //TODO: easy translatable
    //TODO: blind accessibility
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DbHelper(this);
        mHandler = new Handler();

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected()) {
            //database selection
            Cursor cursor = mDbHelper.getAllData();
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String availability = cursor.getString(cursor.getColumnIndex("availability"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    parkingArray.add(new ParkingDTO(Integer.parseInt(id), name, Integer.parseInt(availability)));
                } while (cursor.moveToNext());
            }
            cursor.close();

            Log.d(debug, String.valueOf(parkingArray));

            alert();
        } else {
            goToNextIntentIfConnected();

            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ExecutorService ex = Executors.newFixedThreadPool(3);
                    Log.i(debug, "Starting...");
                    Message ms = new Message(TAKEALL, null);
                    ClientThread client = new ClientThread(ms);
                    Future<Message> future = ex.submit(client);

                    try {
                        Message response = new Message();
                        Log.i(debug, "waiting for the future object");
                        response = future.get(timeout, TimeUnit.SECONDS);
                        isConnectedToServer = true;
                        parkingArray = (List<ParkingDTO>) response.getData();
                        updateDatabase();
                        Log.i(debug, parkingArray.toString());

                    } catch (InterruptedException | ExecutionException | TimeoutException | NullPointerException e) {
                        Log.d(debug, "Error", e);
                        e.printStackTrace();
                        future.cancel(true); //this method will stop the running underlying task
                        mHandler.post(new Runnable() {
                            public void run() {
                                alert();
                            }
                        });
                    } finally {
                        ex.shutdown();
                    }
                }
            });
            serverThread.start();
        }
    }

    private void alertOnClickDismiss(DialogInterface dialog) {
        dialog.dismiss();
        Intent nextFrame = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(nextFrame);
        finish();
    }

    /**
     * Display an alert dialog.
     */
    public void alert() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Server connection Error");
        alertDialog.setMessage("Only works on wi-fi/localhost.\n Switch to wi-fi and restart the app!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertOnClickDismiss(dialog);
                    }
                });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                alertOnClickDismiss(dialog);
            }
        });
        alertDialog.show();
    }

    /**
     * Updates the database.
     */
    private void updateDatabase() {
        for (ParkingDTO item : parkingArray) {
            mDbHelper.updateData(String.valueOf(item.getId()), item.getAvailability(), item.getName());
        }
    }

    /**
     * Every 0.3 seconds a separate thread try's to connect to the server.
     * If the server is online, it will go to the next intent, closing this one.
     */
    private void goToNextIntentIfConnected() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isConnectedToServer) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent nextFrame = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(nextFrame);
                finish();
            }
        }).start();
    }
}