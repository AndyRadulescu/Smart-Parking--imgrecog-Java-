package com.example.anama.parkingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

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

public class Main3Activity extends AppCompatActivity {

    private String debug = "debug";
    private volatile List<ParsedParking> parsedArray = new ArrayList<>();
    private volatile Paint p;
    private volatile Bitmap bitmap;
    private volatile Canvas canvas;
    private volatile LinearLayout ll;
    private volatile boolean isRunning = true;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        p = new Paint();
        bitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        ll = (LinearLayout) findViewById(R.id.rect1);
        ll.setBackground(new BitmapDrawable(bitmap));

        parsedArray = MainActivity.take();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    Log.i(debug, "entering while");
                    //parking lots
                    if (parsedArray.get(0).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(170, 150, 20, 50, p);

                    if (parsedArray.get(1).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(460, 150, 310, 50, p);

                    if (parsedArray.get(2).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(170, 450, 20, 350, p);

                    if (parsedArray.get(3).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(460, 450, 310, 350, p);

                    if (parsedArray.get(4).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(170, 750, 20, 650, p);

                    if (parsedArray.get(5).getAvailability() == 1)//true=liber, false=ocupat
                    {
                        p.setColor(Color.parseColor("#32CD32"));
                    } else {
                        p.setColor(Color.parseColor("#da4747"));
                    }

                    canvas.drawRect(460, 750, 310, 650, p);

                    ll.setBackground(new BitmapDrawable(bitmap));

                    //road
                    p.setColor(Color.parseColor("#636161"));
                    canvas.drawRect(290, 800, 200, 10, p);
                    p.setColor(Color.parseColor("#eeeeee"));
                    canvas.drawRect(250, 70, 240, 20, p);
                    canvas.drawRect(250, 220, 240, 160, p);
                    canvas.drawRect(250, 370, 240, 320, p);
                    canvas.drawRect(250, 520, 240, 470, p);
                    canvas.drawRect(250, 670, 240, 620, p);
                    canvas.drawRect(250, 800, 240, 760, p);

                    Log.i(debug, "-------------------finished--------------------");

                    ExecutorService ex = Executors.newSingleThreadExecutor();

                    Message ms = new Message(TAKEALL, null);
                    ClientThread client = new ClientThread(ms);
                    Future<Message> future = ex.submit(client);
                    try {
                        Thread.sleep(1000); //refresh by 5 secoonds
                        Log.i(debug, "Starting...");
                        Message response = new Message();
                        Log.i(debug, "waiting for the future object");
                        response = future.get(3000, TimeUnit.MILLISECONDS);
                        parsedArray = (List<ParsedParking>) response.getData();
                        Log.i(debug, parsedArray.toString());

                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                        future.cancel(true); //this method will stop the running underlying task
                        Log.e(debug, e.getMessage());
                        AlertDialog alertDialog = new AlertDialog.Builder(Main3Activity.this).create();
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
        });
        thread.start();
    }

}


