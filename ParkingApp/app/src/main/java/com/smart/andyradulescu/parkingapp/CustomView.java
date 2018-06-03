package com.smart.andyradulescu.parkingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import helper.SavedItems;
import model.DBService;
import model.DbHelper;
import model.ParkingDTO;
import server.service.GetDataFromBackendThread;

/**
 * Created by Andy Radulescu.
 * This class updates the UI accordingly.
 */
public class CustomView extends View implements SavedItems {

    GetDataFromBackendThread info;
    private List<ParkingDTO> parkingPlaces;
    private Paint paint;
    private Context context;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        parkingPlaces = new ArrayList<>();
        this.paint = new Paint();
        DbHelper mDbHelper = new DbHelper(context);
        parkingPlaces = DBService.getInfoFromDb(mDbHelper);
        info = new GetDataFromBackendThread(context);
        new Thread(info).start();
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

        reinitialize();

        invalidate();
    }

    /**
     * Sends TCP packets to the server to receive the new data-base info.
     * It uses a Future object to receive the date form the OutputStream.
     */
    private void reinitialize() {
        this.parkingPlaces = info.parkingPlaces;
    }
}