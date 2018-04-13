package server.service;

import android.database.Cursor;

import java.util.List;

import model.DbHelper;
import model.ParsedParking;

/**
 * Created by Andy Radulescu
 * This class Creates a service to connect to the database;
 */

public class DBService {

    /**
     * Selects the parking info from the database.
     */
    public static List<ParsedParking> getInfoFromDb(DbHelper mDbHelper, List<ParsedParking> parkingPlaces) {
        try (Cursor cursor = mDbHelper.getAllData()) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("ID"));
                    String availability = cursor.getString(cursor.getColumnIndex("availability"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    parkingPlaces.add(new ParsedParking(Integer.parseInt(id), name, Integer.parseInt(availability)));
                } while (cursor.moveToNext());
            }
            return parkingPlaces;
        }
    }

    /**
     * Updates the database.
     */
    public static void updateDatabase(DbHelper mDbHelper, List<ParsedParking> parkingPlaces) {
        for (ParsedParking item : parkingPlaces)
            mDbHelper.updateData(String.valueOf(item.getId()), item.getAvailability(), item.getName());
    }
}
