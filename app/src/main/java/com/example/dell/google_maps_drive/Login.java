package com.example.dell.google_maps_drive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private SQLiteDatabase db;
    private User user;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private TaxiDbHelper helper;
    private GcmRegistrationAsyncTask RegisteredGcm;
    private Toolbar t_bar;
    private InterstitialAd interstitialAd;
    Boolean exitapp=false;
    //facebook

    //facebook
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        launchinter();
        loadInter();

        SharedPreferences sp = getSharedPreferences("login",0);
        String temp = sp.getString("loginPhone","");
        if (!temp.equals(""))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
        Context context=this;
        ((GlobalClass)getApplication()).setIp("192.168.8.100:8080");
        t_bar=(Toolbar)findViewById(R.id.login_toolbar);

        setSupportActionBar(t_bar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Getting a reference to action bar of this activity */
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        }


    //adcode
    private void launchinter()
    {
        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-123456789/123456789");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                if (exitapp)
                    finish();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdLoaded() {
                showadinter();
            }
        });
    }


    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {

        }
        return "";
    }

    public void showadinter()
    {
        if(interstitialAd.isLoaded())
        {
            interstitialAd.show();
        }
        else
        {
            Log.d("","add was not ready to be shown");
        }
    }

    public void loadInter()
    {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();

        AdRequest adRequest= new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(deviceId)
                .build();

        interstitialAd.loadAd(adRequest);
        Toast.makeText(Login.this, "entering", Toast.LENGTH_SHORT).show();
    }
    //adcode
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new LoginFragment();
                case 1 : return new SignUpFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Login";
                case 1 :
                    return "Sign Up";
            }
            return null;
        }
    }

    //network checking for interaction with server

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void loginClick(View view)
    {
        Spinner spinner = (Spinner)findViewById(R.id.loginSpin);
       final String spin = spinner.getSelectedItem().toString();
        final String cell = ((EditText)findViewById(R.id.loginName)).getText().toString();
       final String pass = ((EditText)findViewById(R.id.loginPass)).getText().toString();
        if (cell.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.length() < 8){
            Toast.makeText(getApplicationContext(), "Password Length should be Greater than 7", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        if (!pd.isShowing()){
            pd.show();
        }
        //User.validate(getApplication(),cell,pass,spin);
        String url = "http://" + ((GlobalClass) getApplication()).getIp() + "/SMD/smd.php";

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("0")){
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "Invalid Cell Number or Password", Toast.LENGTH_LONG).show();
                        }
                        else if (response.equals("-1")){
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "DB Error", Toast.LENGTH_LONG).show();
                        }
                        else {
                            try{
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0; i<jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    SharedPreferences login = getApplication().getSharedPreferences("login", 0);
                                    SharedPreferences.Editor editor = login.edit();
                                    editor.putString("loginPhone", jsonObject.getString("phone"));
                                    editor.putString("loginName", jsonObject.getString("name"));
                                    editor.putString("loginCnic", jsonObject.getString("cnic"));
                                    editor.putString("loginImage", jsonObject.getString("img"));
                                    editor.putString("loginPass", jsonObject.getString("password"));
                                    if (spin.equals("Driver")) {
                                        editor.putString("loginCar", jsonObject.getString("car"));
                                        editor.putString("loginArea", jsonObject.getString("area"));
                                        editor.putString("loginRate", jsonObject.getString("rateKm"));
                                    }
                                    editor.putString("type", spin);
                                    editor.apply();
                                    finish();
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getApplicationContext(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("opt","login");
                params.put("phn",cell);
                params.put("pass",pass);
                params.put("type",spin);
                return params;
            }
        };

        // Add the request to the queue
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }

}
