package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andy Radulescu on 12/23/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "parking.db";
    private static final String TABLE_NAME = "parking";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "availability";
    private static final String COL_2 = "name";
    private static final int VERSION = 2;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_1 + " INTEGER ," +
                COL_2 + " TEXT ) ");

        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(1,'A')");
        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(0,'A')");
        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(1,'B')");
        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(0,'B')");
        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(1,'C')");
        db.execSQL("insert into " + TABLE_NAME + "(" + COL_1 + "," + COL_2 + ") values(1,'C')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public boolean updateData(String id, int availability, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_0, id);
        contentValues.put(COL_1, availability);
        contentValues.put(COL_2, name);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        return true;
    }
}
