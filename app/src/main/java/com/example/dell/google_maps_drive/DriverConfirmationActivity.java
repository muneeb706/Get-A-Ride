package com.example.dell.google_maps_drive;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 4/30/2016.
 */
public class DriverConfirmationActivity extends AppCompatActivity{
    private Toolbar t_bar;
    ImageButton call;
    ImageButton message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_confirmation_activity);
        t_bar = (Toolbar) findViewById(R.id.driver_confirmation_toolbar);

        setSupportActionBar(t_bar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Intent intent = getIntent();
        final SharedPreferences request_sp = getSharedPreferences("Driver Confirmation", Context.MODE_PRIVATE);
        final SharedPreferences log = getSharedPreferences("login", 0);
        final String msg = intent.getExtras().getString("message");
        if (intent.hasExtra("message")) {
            TextView contact_number = (TextView) findViewById(R.id.driver_contact_number);
            TextView start_loc = (TextView) findViewById(R.id.driver_start_loc);
            TextView end_loc = (TextView) findViewById(R.id.driver_end_loc);
            TextView request_time = (TextView) findViewById(R.id.accept_time);

            contact_number.setText("Number: " + request_sp.getString("SenderNumber" + msg, ""));
            start_loc.setText("Start Location: " + request_sp.getString("Start" + msg, ""));
            end_loc.setText("End Location: " + request_sp.getString("End" + msg, ""));
            request_time.setText("Endorsement Time: " + request_sp.getString("AcceptTime" + msg, ""));

        }

        call=(ImageButton) findViewById(R.id.callbtn);
        message=(ImageButton) findViewById(R.id.messagebtn);

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "03234106476"));
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(getApplicationContext(),"Not allowed to call",Toast.LENGTH_LONG).show();
                    }
                    else {

                        String url = "http://" + ((GlobalClass) getApplication()).getIp() + "/SMD/smd.php";
                        // Request a string response
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("-1")){
                                            Toast.makeText(getApplicationContext(), "DB Error", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("opt","updateDeal");
                                params.put("driver",request_sp.getString("SenderNumber" + msg,""));
                                params.put("sTime",request_sp.getString("AcceptTime" + msg,""));
                                params.put("sLoc",request_sp.getString("Start" + msg,""));
                                params.put("customer",log.getString("loginPhone",""));
                                return params;
                            }
                        };

                        // Add the request to the queue
                        int socketTimeout = 30000;//30 seconds - change to what you want
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(policy);
                        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                        finish();
                        startActivity(intent);
                    }
                    //Toast.makeText(MainActivity.this, check, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    Intent intent = new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("sms", "03234106476", null));
                    startActivity(intent);
                    //Toast.makeText(MainActivity.this, check, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
