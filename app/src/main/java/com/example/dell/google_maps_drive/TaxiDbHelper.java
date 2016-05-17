package com.example.dell.google_maps_drive;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Talal on 4/20/2016.
 */
public class TaxiDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "smd.db";

    public TaxiDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    // creation of db
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Customer (phone TEXT PRIMARY KEY, " +
                "name TEXT," +
                "rating INT," +
                "password TEXT," +
                "cnic TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE Driver (phone TEXT PRIMARY KEY, " +
                "name TEXT," +
                "rating INT," +
                "password TEXT," +
                "cnic TEXT," +
                "area TEXT," +
                "rate TEXT," +
                "car TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE Deal (customerId TEXT, " +
                "driverId TEXT," +
                "startTime DATETIME," +
                "endTime DATETIME," +
                "distance INT," +
                "amount FLOAT," +
                "status BOOLEAN," +
                "endLocation TEXT," +
                "startLocation TEXT)";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Driver");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Deal");
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}