package model;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy Radulescu
 * This class Creates a service to connect to the database;
 */

public class DBService {

    /**
     * Selects the parking info from the database.
     */
    public static List<ParkingDTO> getInfoFromDb(DbHelper mDbHelper) {
        List<ParkingDTO> parkingPlaces = new ArrayList<>();
        parkingPlaces.clear();
        try (Cursor cursor = mDbHelper.getAllData()) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String availability = cursor.getString(cursor.getColumnIndex("availability"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    parkingPlaces.add(new ParkingDTO(Integer.parseInt(id), name, Integer.parseInt(availability)));
                } while (cursor.moveToNext());
            }
            return parkingPlaces;
        }
    }

    /**
     * Updates the database.
     */
    public static void updateDatabase(DbHelper mDbHelper, List<ParkingDTO> parkingPlaces) {
        for (ParkingDTO item : parkingPlaces) {
            mDbHelper.updateData(String.valueOf(item.getId()), item.getAvailability(), item.getName());
        }
    }
}