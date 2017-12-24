package com.example.anama.parkingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import model.DbHelper;
import model.ParsedParking;
import server.ClientThread;
import server.ConnectionDetector;
import server.Message;
import server.SavedItems;

public class MainActivity extends AppCompatActivity implements SavedItems {

    private static List<ParsedParking> parkingArray = new ArrayList<>();
    private boolean isConnectedToServer = false;
    DbHelper mDbHelper;
    private Handler mHandler;

    public static List<ParsedParking> getParkingArray() {
        return parkingArray;
    }

    //TODO: finish the database update and select
    //TODO: add asincTask run in background to help with the server.
    //TODO: Stylise the UI si it fits for every android device.
    //TODO: easy translatable
    //TODO: blind accessibility
    //TODO: sql database that saves the parking so it can work offline
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DbHelper(this);
        mHandler = new Handler();

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected()) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Server connection Error");
            alertDialog.setMessage("Only works on wi-fi/localhost.\n Switch to wi-fi and restart the app!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
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
                        response = future.get(3000, TimeUnit.MILLISECONDS);
                        isConnectedToServer = true;
                        parkingArray = (List<ParsedParking>) response.getData();
                        updateDatabase();
                        Log.i(debug, parkingArray.toString());

                    } catch (InterruptedException | ExecutionException | TimeoutException | NullPointerException e) {
                        Log.d(debug, "Error", e);
                        e.printStackTrace();
                        future.cancel(true); //this method will stop the running underlying task
                        mHandler.post(new Runnable() {
                            public void run() {
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle("Server connection Error");
                                alertDialog.setMessage("The server might be down..\n " +
                                        "Retry in a few minutes.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alertDialog.show();
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

    private void updateDatabase() {
        for (ParsedParking item : parkingArray)
            mDbHelper.updateData(String.valueOf(item.getId()), item.getAvailability(), item.getName());
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