package com.example.dell.google_maps_drive;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

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
 * Created by DELL on 4/26/2016.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    View view;
    Boolean check = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.profile_fragment, container, false);
        view=rootView;

        SharedPreferences sp = getActivity().getSharedPreferences("login",0);
        String type = sp.getString("type", "");
        String phone = sp.getString("loginPhone","");
        String name = sp.getString("loginName","");
        String cnic = sp.getString("loginCnic","");
        String pass = sp.getString("loginPass","");
        String car,area,rate;

        EditText editText = (EditText)rootView.findViewById(R.id.area);
        editText.setVisibility(View.GONE);
        editText = (EditText)rootView.findViewById(R.id.car);
        editText.setVisibility(View.GONE);
        editText = (EditText)rootView.findViewById(R.id.rate);
        editText.setVisibility(View.GONE);

        editText = (EditText)rootView.findViewById(R.id.name);
        editText.setText(name);
        editText.setEnabled(false);


        editText = (EditText)rootView.findViewById(R.id.status);
        editText.setText(type);
        editText.setEnabled(false);

        editText = (EditText)rootView.findViewById(R.id.contact);
        editText.setText(phone);
        editText.setEnabled(false);

        editText = (EditText)rootView.findViewById(R.id.pass);
        editText.setText(pass);
        editText.setEnabled(false);

        editText = (EditText)rootView.findViewById(R.id.cnic);
        editText.setText(cnic);
        editText.setEnabled(false);

        if (type.equals("Driver")) {
            car = sp.getString("loginCar","");
            area = sp.getString("loginArea","");
            rate = sp.getString("loginRate","");

            editText = (EditText)rootView.findViewById(R.id.area);
            editText.setVisibility(View.VISIBLE);
            editText.setText(area);
            editText.setEnabled(false);

            editText = (EditText)rootView.findViewById(R.id.car);
            editText.setVisibility(View.VISIBLE);
            editText.setText(car);
            editText.setEnabled(false);

            editText = (EditText)rootView.findViewById(R.id.rate);
            editText.setVisibility(View.VISIBLE);
            editText.setText(rate);
            editText.setEnabled(false);
        }

        Button button = (Button)rootView.findViewById(R.id.cancelbtn);
        button.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("login", 0);
                String type = sp.getString("type", "");
                String phone = sp.getString("loginPhone", "");
                String name = sp.getString("loginName", "");
                String cnic = sp.getString("loginCnic", "");
                String pass = sp.getString("loginPass", "");
                String car, area, rate;

                EditText editText = (EditText) getView().findViewById(R.id.area);
                editText.setVisibility(View.GONE);
                editText = (EditText) getView().findViewById(R.id.car);
                editText.setVisibility(View.GONE);
                editText = (EditText) getView().findViewById(R.id.rate);
                editText.setVisibility(View.GONE);

                editText = (EditText) getView().findViewById(R.id.name);
                editText.setText(name);
                editText.setEnabled(false);

                editText = (EditText)getView().findViewById(R.id.status);
                editText.setText(type);
                editText.setEnabled(false);


                editText = (EditText) getView().findViewById(R.id.contact);
                editText.setText(phone);
                editText.setEnabled(false);

                editText = (EditText) getView().findViewById(R.id.pass);
                editText.setText(pass);
                editText.setEnabled(false);

                editText = (EditText) getView().findViewById(R.id.cnic);
                editText.setText(cnic);
                editText.setEnabled(false);

                Button button = (Button) getView().findViewById(R.id.cancelbtn);
                button.setVisibility(View.GONE);

                if (type.equals("Driver")) {
                    car = sp.getString("loginCar", "");
                    area = sp.getString("loginArea", "");
                    rate = sp.getString("loginRate", "");

                    editText = (EditText) getView().findViewById(R.id.area);
                    editText.setVisibility(View.VISIBLE);
                    editText.setText( area);
                    editText.setEnabled(false);

                    editText = (EditText) getView().findViewById(R.id.car);
                    editText.setVisibility(View.VISIBLE);
                    editText.setText( car);
                    editText.setEnabled(false);

                    editText = (EditText) getView().findViewById(R.id.rate);
                    editText.setVisibility(View.VISIBLE);
                    editText.setText(rate);
                    editText.setEnabled(false);
                }
                ((Button)getView().findViewById(R.id.editbtn)).setText("Enable Edit");
                check = true;
            }
        });

        button = (Button)rootView.findViewById(R.id.editbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check==true) {
                    EditText editText = (EditText) getView().findViewById(R.id.contact);
                    editText.setEnabled(true);

                    editText = (EditText) getView().findViewById(R.id.name);
                    editText.setEnabled(true);

                    editText = (EditText) getView().findViewById(R.id.pass);
                    editText.setEnabled(true);

                    editText = (EditText) getView().findViewById(R.id.cnic);
                    editText.setEnabled(true);

                    SharedPreferences sp = getActivity().getSharedPreferences("login", 0);
                    String type = sp.getString("type", "");

                    if (type.equals("Driver")) {
                        editText = (EditText) getView().findViewById(R.id.area);
                        editText.setEnabled(true);

                        editText = (EditText) getView().findViewById(R.id.car);
                        editText.setEnabled(true);

                        editText = (EditText) getView().findViewById(R.id.rate);
                        editText.setEnabled(true);
                    }

                    Button button = (Button)getView().findViewById(R.id.editbtn);
                    button.setText("Done");

                    button = (Button)getView().findViewById(R.id.cancelbtn);
                    button.setVisibility(View.VISIBLE);
                    check = false;

                }
                else{
                    final String phone,name,cnic,pass;
                    String rate,car,area;
                    car="";
                    area="";
                    rate="";
                    EditText editText = (EditText) getView().findViewById(R.id.contact);
                    phone = editText.getText().toString();

                    editText = (EditText) getView().findViewById(R.id.name);
                    name = editText.getText().toString();

                    editText = (EditText) getView().findViewById(R.id.pass);
                    pass = editText.getText().toString();

                    editText = (EditText) getView().findViewById(R.id.cnic);
                    cnic = editText.getText().toString();

                    final SharedPreferences sp = getActivity().getSharedPreferences("login", 0);
                    final String type = sp.getString("type", "");

                    if (type.equals("Driver")) {
                        editText = (EditText) getView().findViewById(R.id.area);
                        area = editText.getText().toString();

                        editText = (EditText) getView().findViewById(R.id.car);
                        car = editText.getText().toString();

                        editText = (EditText) getView().findViewById(R.id.rate);
                        rate = editText.getText().toString();
                    }
                    if (type.equals("Driver")) {
                        if (phone.isEmpty() || name.isEmpty() || cnic.isEmpty() || pass.isEmpty() || car.equals("") || area.equals("") || rate.equals("")) {
                            Toast.makeText(getContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (cnic.length() != 13) {
                            Toast.makeText(getContext(), "CNIC should be of 13 Digits", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (pass.length() < 8) {
                            Toast.makeText(getContext(), "Password Length should be Greater than 7", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else{
                        if (phone.isEmpty() || name.isEmpty() || cnic.isEmpty() || pass.isEmpty()) {
                            Toast.makeText(getContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (cnic.length() != 13) {
                            Toast.makeText(getContext(), "CNIC should be of 13 Digits", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (pass.length() < 8) {
                            Toast.makeText(getContext(), "Password Length should be Greater than 7", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    check = true;

                    final SharedPreferences.Editor editor = sp.edit();

                    final String temp1,temp2,temp3;
                    temp1 = area;
                    temp2 = car;
                    temp3 = rate;


                    FrameLayout userFrame=(FrameLayout)getActivity().findViewById(R.id.user_fragments);
                    userFrame.setVisibility(View.VISIBLE);

                    String url = "http://" + ((GlobalClass) getActivity().getApplication()).getIp() + "/SMD/smd.php";

                    // Request a string response
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("-1")) {
                                        Toast.makeText(getContext(),"User already Existed with same Number",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getContext(),"Edit Successful",Toast.LENGTH_LONG).show();
                                        editor.putString("loginPhone", phone);
                                        editor.putString("loginName",name);
                                        editor.putString("loginPass",pass);
                                        editor.putString("loginCnic",cnic);

                                        if (type.equals("Driver")) {
                                            editor.putString("loginArea", temp1);
                                            editor.putString("loginCar", temp2);
                                            editor.putString("loginRate", temp3);
                                        }
                                        editor.apply();
                                        android.support.v4.app.Fragment profile_fragment=new ProfileFragment();
                                        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.user_fragments, profile_fragment);
                                        transaction.commit();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("opt","updateUser");
                            params.put("type",type);
                            params.put("phn",phone);
                            params.put("name",name);
                            params.put("cnic",cnic);
                            params.put("pass",pass);
                            params.put("area",temp1);
                            params.put("car",temp2);
                            params.put("rate",temp3);
                            String oldPhn = sp.getString("loginPhone","");
                            params.put("oldPhone",oldPhn);
                            return params;
                        }
                    };

                    // Add the request to the queue
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    Volley.newRequestQueue(getContext()).add(stringRequest);
                }
            }
        });

        return rootView;
    }

    public void hide(){
        view.setVisibility(View.GONE);
    }
    public void show(){
        view.setVisibility(View.VISIBLE);
    }
}
