package com.example.dell.google_maps_drive;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Talal on 4/20/2016.
 */
public class TaxiAppProvider extends ContentProvider {

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI("com.example.talal.taxiapp.taxiappprovider","Driver",1);
        matcher.addURI("com.example.talal.taxiapp.taxiappprovider","Customer",2);
        matcher.addURI("com.example.talal.taxiapp.taxiappprovider","Deal",3);
    }

    private SQLiteDatabase db;

    public TaxiAppProvider()
    {}

    public TaxiAppProvider(SQLiteDatabase database)
    {
        db = database;
    }

    public boolean onCreate(){
        return true;
    }

    public String getType(Uri uri){
        return null;
    }

    public Uri insert(Uri uri,ContentValues values){
        if(matcher.match(uri) == 1) {
            long rowID = db.insert("Driver", null, values);
            if (rowID > 0) {
                return ContentUris.withAppendedId(uri, rowID);
            }
            throw new SQLException("Failed to add a record into " + uri);
        }
        else if(matcher.match(uri) == 2) {
            long rowID = db.insert("Customer", null, values);
            if (rowID > 0) {
                return ContentUris.withAppendedId(uri, rowID);
            }
            throw new SQLException("Failed to add a record into " + uri);
        }
        else if(matcher.match(uri) == 3) {
            long rowID = db.insert("Deal", null, values);
            if (rowID > 0) {
                return ContentUris.withAppendedId(uri, rowID);
            }
            throw new SQLException("Failed to add a record into " + uri);
        }
        return null;
    }

    public int update(Uri uri,ContentValues values,String selection,String[] args){
        return 0;
    }

    public int delete(Uri uri,String selection,String[] args){
        if(matcher.match(uri) == 1) {
            int rowID = db.delete("Driver", "phone = '" + selection + "'", null);
            if (rowID>0)
                return rowID;
        }
        else if(matcher.match(uri) == 2) {
            int rowID = db.delete("Customer", "phone = '" + selection + "'", null);
            if (rowID>0)
                return rowID;
        }
        else if(matcher.match(uri) == 3) {
            int rowID = db.delete("Deal", "customerId = '" + args[0] + "' and driverId = '" + args[1] + "'", null);
            if (rowID>0)
                return rowID;
        }
        return 0;
    }

    public Cursor query(Uri uri,String [] projection,
                        String selection,String[] selectionArgs,String sortOrder){

        if(matcher.match(uri) == 1){
            return db.query("Driver",projection,selection,selectionArgs,null,null,sortOrder);
        }
        else if(matcher.match(uri) == 2) {
            return db.query("Customer", projection, selection, selectionArgs, null, null, sortOrder);
        }
        else if(matcher.match(uri) == 3){
            return db.query("Deal",projection,selection,selectionArgs,null,null,sortOrder);
        }
        return null;
    }
}