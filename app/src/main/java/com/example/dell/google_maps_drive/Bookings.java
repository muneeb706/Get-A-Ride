package com.example.dell.google_maps_drive;

/**
 * Created by hammad on 4/25/2016.
 */
public class Bookings {

    String driver;
    String date;
    String to;
    String from;
    static int counter=0;
    int index;


    Bookings(String d, String da,String t, String f,int position)
    {
        driver=d;
        date=da;
        to=t;
        from=f;
        counter++;
        index=position;

    }

}
