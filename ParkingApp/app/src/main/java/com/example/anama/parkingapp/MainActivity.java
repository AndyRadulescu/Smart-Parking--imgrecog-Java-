package com.example.anama.parkingapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import model.ParsedParking;
import server.ClientThread;
import server.Message;

import static server.SavedItems.TAKEALL;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    private static String debug = "debug";
    private static List<ParsedParking> parkingArray = new ArrayList<>();

    public static List<ParsedParking> take() {
        return parkingArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            if (!isConnected()) {
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setTitle("Server connection Error");
//                alertDialog.setMessage("Your internet connection might be down..");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                finish();
//                            }
//                        });
//                alertDialog.show();
//            } else {

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(MainActivity.this, Main2Activity.class);
//                startActivity(i);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

        ExecutorService ex = Executors.newFixedThreadPool(3);
        Log.i(debug, "Starting...");
        Message ms = new Message(TAKEALL, null);
        ClientThread client = new ClientThread(ms);

        Future<Message> future = ex.submit(client);

        try {
            Message response = new Message();
            Log.i(debug, "waiting for the future object");
            response = future.get(3000, TimeUnit.MILLISECONDS);
            parkingArray = (List<ParsedParking>) response.getData();
            Log.i(debug, parkingArray.toString());
        } catch (InterruptedException | ExecutionException | TimeoutException | NullPointerException e) {

            e.printStackTrace();
            // future.cancel(true); //this method will stop the running underlying task
            Log.e(debug, e.getMessage());
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Server connection Error");
            alertDialog.setMessage("Your internet connection might be down..");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            alertDialog.show();
        } finally {
            ex.shutdown();
        }
    }
}