package com.example.dell.google_maps_drive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by DELL on 4/30/2016.
 */
public class UserConfirmationActivity extends AppCompatActivity {
    private Toolbar t_bar;
    String cust_number;
    String cust_start;
    String cust_end;
    String req_time;
    String acc_request_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmactivity);
        t_bar=(Toolbar)findViewById(R.id.confirmation_toolbar);

        setSupportActionBar(t_bar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        Intent intent = getIntent();
        if (intent.hasExtra("message")) {
            String msg = intent.getExtras().getString("message");
            SharedPreferences request_sp=getSharedPreferences("Notification Request", Context.MODE_PRIVATE);
           TextView contact_number= (TextView)findViewById(R.id.contact_number);
            TextView start_loc= (TextView)findViewById(R.id.start_loc);
            TextView end_loc= (TextView)findViewById(R.id.end_loc);
            TextView request_time= (TextView)findViewById(R.id.request_time);
            cust_number=request_sp.getString("Number"+msg,"");
            cust_start=request_sp.getString("Start" + msg, "");
            cust_end=request_sp.getString("End" + msg, "");
            req_time=request_sp.getString("RequestTime"+msg,"");


            contact_number.setText("Number: "+request_sp.getString("Number" + msg, ""));
            start_loc.setText("Start Location: "+request_sp.getString("Start" + msg, ""));
            end_loc.setText("End Location: "+request_sp.getString("End" + msg, ""));
            request_time.setText("Request Time: "+request_sp.getString("RequestTime"+msg,""));

        }

        Button accept=(Button)findViewById(R.id.accept);
        Button reject=(Button)findViewById(R.id.reject);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c;
                int mhour;
                int mminute;
                int year,month,day;
                c = Calendar.getInstance();
                mhour = c.get(Calendar.HOUR);
                mminute = c.get(Calendar.MINUTE);
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH)+1;
                day=c.get(Calendar.DATE);


                String requestHour = mhour + "";
                String requestMin = mminute + "";
                acc_request_time = year+":"+month+":"+day+" "+requestHour + ":" + requestMin + ":00";

                SharedPreferences sp = getSharedPreferences("login", 0);
                String curr_phone = sp.getString("loginPhone", "");

                new EndpointsAsyncTask().execute(new Pair<Context, String>(getApplicationContext(), "Accepted Your Request,"+curr_phone),new Pair<Context,String>(getApplicationContext(),cust_number.replace(",",""))
                        ,new Pair<Context,String>(getApplicationContext(),cust_start.replace(",", "")),new Pair<Context,String>(getApplicationContext(),cust_end.replace(",",""))
                        ,new Pair<Context,String>(getApplicationContext(),req_time.replace(",","")));
                finish();

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }
}
