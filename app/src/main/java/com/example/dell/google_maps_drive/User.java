package com.example.dell.google_maps_drive;

import android.content.SharedPreferences;
import android.widget.Toast;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Talal on 4/18/2016.
 */
public abstract class User {
    protected String name;
    protected String cnic;
    protected String telephone;
    protected String pass;

    public User(){}

    public User(String n,String cnc,String phn,String p)
    {
        name = n;
        cnic = cnc;
        telephone = phn;
        pass = p;
    }

    public void setName(String n){
        name = n;
    }

    public void setCnic(String n){
        cnic = n;
    }

    public void setPhone(String n){
        telephone = n;
    }

    public String getName(){
        return name;
    }

    public String getCnic(){
        return cnic;
    }

    public String getPhone(){
        return telephone;
    }

    public abstract String getType();

    /*public static void validate(final Application app,final String phn,final String pass,final String type){

        String url = "http://" + ((GlobalClass) app).getIp() + "/SMD/smd.php";

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("1")) {
                            //startNextActivity();
                            //start new activity
                        }
                        else {
                            Toast.makeText(app.getApplicationContext(), "Invalid Cell Number or Password", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(app.getApplicationContext(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opt","login");
                params.put("phn",phn);
                params.put("pass",pass);
                params.put("type",type);
                return params;
            }
        };

        // Add the request to the queue
        int socketTimeout = 5000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(app.getApplicationContext()).add(stringRequest);
    }*/
}