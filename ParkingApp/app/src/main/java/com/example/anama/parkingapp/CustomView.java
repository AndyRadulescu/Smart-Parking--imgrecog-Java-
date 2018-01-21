package com.example.anama.parkingapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
import server.Message;

import static server.SavedItems.DISTANCE_HEIGHT_LINES;
import static server.SavedItems.MIDDLE_LINE;
import static server.SavedItems.MIDDLE_WHITE_LINES;
import static server.SavedItems.PARKING_HORIZONTAL_PADDING;
import static server.SavedItems.PARKING_VERTICAL_PADDING;
import static server.SavedItems.TAKEALL;
import static server.SavedItems.debug;

/**
 * Created by Andy Radulescu on 10/23/2017.
 */

public class CustomView extends View {

    private volatile List<ParsedParking> parkingPlaces = new ArrayList<>();
    private Paint paint;
    private DbHelper mDbHelper;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        mDbHelper = new DbHelper(context);
        getInfoFromDb();
    }

    /**
     * Selects the parking info from the database.
     */
    private void getInfoFromDb() {
        Cursor cursor = mDbHelper.getAllData();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("ID"));
                String availability = cursor.getString(cursor.getColumnIndex("availability"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                parkingPlaces.add(new ParsedParking(Integer.parseInt(id), name, Integer.parseInt(availability)));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int horizontalParkingSlot = getWidth() / 2;
        int heightOfLine = (getHeight() - 200) / 6;
        int heightOfParkingSlot = (getHeight() - 200) / 3;
        int horizontalWidth = getWidth();


        Log.i(debug, "entering while");
        //parking lot

        if (parkingPlaces.get(0).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalParkingSlot - MIDDLE_LINE - PARKING_HORIZONTAL_PADDING, heightOfParkingSlot - PARKING_VERTICAL_PADDING, 0, PARKING_VERTICAL_PADDING, paint);

        if (parkingPlaces.get(1).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalWidth, heightOfParkingSlot - PARKING_VERTICAL_PADDING, horizontalParkingSlot + MIDDLE_LINE + PARKING_HORIZONTAL_PADDING, PARKING_VERTICAL_PADDING, paint);

        if (parkingPlaces.get(2).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalParkingSlot - MIDDLE_LINE - PARKING_HORIZONTAL_PADDING, heightOfParkingSlot * 2 - PARKING_VERTICAL_PADDING, 0, heightOfParkingSlot + PARKING_VERTICAL_PADDING, paint);

        if (parkingPlaces.get(3).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalWidth, heightOfParkingSlot * 2 - PARKING_VERTICAL_PADDING, horizontalParkingSlot + MIDDLE_LINE + PARKING_HORIZONTAL_PADDING, heightOfParkingSlot + PARKING_VERTICAL_PADDING, paint);

        if (parkingPlaces.get(4).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalParkingSlot - MIDDLE_LINE - PARKING_HORIZONTAL_PADDING, heightOfParkingSlot * 3 - PARKING_VERTICAL_PADDING, 0, heightOfParkingSlot * 2 + PARKING_VERTICAL_PADDING, paint);

        if (parkingPlaces.get(5).getAvailability() == 1) {
            paint.setColor(Color.parseColor("#32CD32"));
        } else {
            paint.setColor(Color.parseColor("#da4747"));
        }

        canvas.drawRect(horizontalWidth, heightOfParkingSlot * 3 - PARKING_VERTICAL_PADDING, horizontalParkingSlot + MIDDLE_LINE + PARKING_HORIZONTAL_PADDING, heightOfParkingSlot * 2 + PARKING_VERTICAL_PADDING, paint);

        // linearLayout.setBackground(new BitmapDrawable(bitmap));

        //road
        paint.setColor(Color.parseColor("#636161"));
        canvas.drawRect(horizontalParkingSlot + MIDDLE_LINE, getHeight(), horizontalParkingSlot - MIDDLE_LINE, 0, paint);
        paint.setColor(Color.parseColor("#ffffff"));

        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, DISTANCE_HEIGHT_LINES, paint);
        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine * 2 - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, heightOfLine + DISTANCE_HEIGHT_LINES, paint);
        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine * 3 - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, heightOfLine * 2 + DISTANCE_HEIGHT_LINES, paint);
        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine * 4 - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, heightOfLine * 3 + DISTANCE_HEIGHT_LINES, paint);
        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine * 5 - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, heightOfLine * 4 + DISTANCE_HEIGHT_LINES, paint);
        canvas.drawRect(horizontalParkingSlot + MIDDLE_WHITE_LINES, heightOfLine * 6 - DISTANCE_HEIGHT_LINES, horizontalParkingSlot - MIDDLE_WHITE_LINES, heightOfLine * 5 + DISTANCE_HEIGHT_LINES, paint);

        Log.i(debug, "-------------------finished--------------------");

        reinitialize();

        invalidate();
    }

    /**
     * Updates the database.
     */
    private void updateDatabase() {
        for (ParsedParking item : parkingPlaces)
            mDbHelper.updateData(String.valueOf(item.getId()), item.getAvailability(), item.getName());
    }

    /**
     * Sends TCP packets to the server to receive the new data-base info.
     * It uses a Future object to receive the date form the OutputStream.
     */
    private void reinitialize() {
        ExecutorService ex = Executors.newSingleThreadExecutor();

        Message ms = new Message(TAKEALL, null);
        ClientThread client = new ClientThread(ms);
        Future<Message> future = ex.submit(client);
        try {
            Log.i(debug, "Starting...");
            Message response = new Message();
            Log.i(debug, "waiting for the future object");
            response = future.get(3000, TimeUnit.MILLISECONDS);
            parkingPlaces = (List<ParsedParking>) response.getData();
            updateDatabase();
            Log.i(debug, parkingPlaces.toString());

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.d(debug, "Error", e);
            future.cancel(true); //this method will stop the running underlying task
        } finally {
            ex.shutdown();
        }
    }
}
