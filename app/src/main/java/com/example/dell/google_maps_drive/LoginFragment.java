package com.example.dell.google_maps_drive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Talal on 4/6/2016.
 */
public class LoginFragment extends Fragment {
    /*private LoginButton loginButton=null;
    private CallbackManager callbackManager=null;
    private AccessTokenTracker mtracker = null;
    private ProfileTracker mprofileTracker = null;*/
   // private InterstitialAd mInterstitialAd;

    private LoginButton loginButton;
    private CallbackManager callbackManager;


    /*FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            Profile profile = Profile.getCurrentProfile();
            homeFragment(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* callbackManager = CallbackManager.Factory.create();
        mtracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldAccessToken + "||" + "CurrentAccessToken" + currentAccessToken);
            }
        };


        mprofileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" + currentProfile);
                homeFragment(currentProfile);

            }
        };

        mtracker.startTracking();
        mprofileTracker.startTracking();*/

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        //callbackManager = CallbackManager.Factory.create();
        View v = inflater.inflate(R.layout.login_layout, null);



        return v;
    }
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Profile profile = Profile.getCurrentProfile();
        homeFragment(profile);



    }*/
    /*private void homeFragment(Profile profile) {

        if (profile!=null) {
            Bundle b = new Bundle();
            b.putParcelable("FB_Profile", profile);
            Spinner spinner = (Spinner) getView().findViewById(R.id.loginSpin);
            final String spin = spinner.getSelectedItem().toString();

            SharedPreferences login = getActivity().getApplication().getSharedPreferences("login", 0);
            SharedPreferences.Editor editor = login.edit();
            editor.putString("loginPhone", "03314910239");
            editor.putString("loginName", "Danyal Afzaal");
            editor.putString("loginCnic", "3520215793665");
            editor.putString("loginImage", "Koi Nahi");
            editor.putString("loginPass", "12345678901234567890");
            editor.putString("loginFacebook", "Yes");
            if (spin.equals("Driver")) {
                editor.putString("loginCar", "Corolla");
                editor.putString("loginArea", "Ghar");
                editor.putString("loginRate", "12");
            }
            editor.putString("type", spin);
            editor.apply();

            getActivity().finish();
            Intent intent = new Intent(getContext(), MapsActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }

    }*/
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        ShareButton shareButton = (ShareButton) view.findViewById(R.id.shareButton);
        shareButton.setShareContent(content);

       /* loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);
        String androidId =  Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);*/
      // String deviceId = MD5(androidId).toUpperCase();

        /*mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);*/


    }
   /* public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }*/


   /* @Override
    public void onStop() {
        super.onStop();
        mtracker.stopTracking();
        mprofileTracker.stopTracking();
    }*/


/*
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
*/

/*    @Override
    public void onResume() {
        super.onResume();

        if (isLoggedIn()) {
            loginButton.setVisibility(View.INVISIBLE);
            Profile profile = Profile.getCurrentProfile();
            homeFragment(profile);
        }
    }*/
}