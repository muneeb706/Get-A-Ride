package com.example.dell.google_maps_drive;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Talal on 4/18/2016.
 */
public class Driver extends User {
    private String car;
    private int rate;
    private String area;

    public Driver(){}

    public Driver(String n,String cnc,String phn,String c,int r,String are,String p){
        super(n,cnc,phn,p);
        car = c;
        rate = r;
        area = are;
    }

    public void setCar(String c){
        car = c;
    }

    public void setRate(int r){
        rate = r;
    }

    public String getCar(){
        return car;
    }

    public int getRate(){
        return rate;
    }

    public String getType(){
        return "driver";
    }
}
