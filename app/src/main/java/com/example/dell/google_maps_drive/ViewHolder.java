package com.example.dell.google_maps_drive;

import android.view.View;
import android.widget.TextView;



/**
 * Created by hammad on 4/25/2016.
 */
public class ViewHolder {


    TextView date;
    TextView to;
    TextView from;
    TextView driver;
    TextView tolabel;
    TextView fromlabel;
    TextView booking;
    TextView bookingnumber;
    ViewHolder(View v)
    {

        date = (TextView) v.findViewById(R.id.datelabel);
        to = (TextView) v.findViewById(R.id.endlabel);
        from = (TextView) v.findViewById(R.id.startlabel);
        driver = (TextView) v.findViewById(R.id.driverlabel);
        bookingnumber=(TextView) v.findViewById(R.id.bookingnumber);
    }
}