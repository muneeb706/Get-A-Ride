package com.example.dell.google_maps_drive;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Talal on 4/18/2016.
 */
public class Customer extends User {
    public Customer(){}

    public Customer(String n,String cnc,String phn,String p){
        super(n,cnc,phn,p);
    }

    public String getType(){
        return "customer";
    }
}
