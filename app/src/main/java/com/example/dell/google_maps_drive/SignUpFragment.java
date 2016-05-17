package com.example.dell.google_maps_drive;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Talal on 4/6/2016.
 */
public class SignUpFragment extends Fragment {
private static ProgressDialog progressDialog;
    private User user;
    AutoCompleteTextView list_of_areas;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyBZp5l09Im1JtaxVmCT2jH8JCoa4oVwqWc";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.sign_layout, null);
final Application app=getActivity().getApplication();
        Button button = (Button) v.findViewById(R.id.sign);
        list_of_areas=((AutoCompleteTextView) v.findViewById(R.id.signArea));
        list_of_areas.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));
        list_of_areas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
        final ProgressDialog pd = new ProgressDialog(getActivity());

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View vw) {
                Spinner spinner = (Spinner) v.findViewById(R.id.signSpin);
                final String temp = spinner.getSelectedItem().toString();
                final String phone,name,cnic,pass,rate,car,area;
                if (temp.equals("Customer")){
                    phone = ((EditText) v.findViewById(R.id.signNumber)).getText().toString();
                    name = ((EditText) v.findViewById(R.id.signName)).getText().toString();
                    cnic = ((EditText) v.findViewById(R.id.signCnic)).getText().toString();
                    pass = ((EditText) v.findViewById(R.id.signPass)).getText().toString();
                    area = "";
                    car = "";
                    rate = "";
                    if (phone.isEmpty() || name.isEmpty() || cnic.isEmpty() || pass.isEmpty()){
                        Toast.makeText(v.getContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (cnic.length() != 13){
                        Toast.makeText(v.getContext(), "CNIC should be of 13 Digits", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (pass.length() < 8){
                        Toast.makeText(v.getContext(), "Password Length should be Greater than 7", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user = new Customer(name,cnic,phone,pass);
                }
                else{
                    phone = ((EditText) v.findViewById(R.id.signNumber)).getText().toString();
                    name = ((EditText) v.findViewById(R.id.signName)).getText().toString();
                    cnic = ((EditText) v.findViewById(R.id.signCnic)).getText().toString();
                    pass = ((EditText) v.findViewById(R.id.signPass)).getText().toString();
                    rate = ((EditText) v.findViewById(R.id.signKm)).getText().toString();
                    area = list_of_areas.getText().toString();
                    car = ((EditText) v.findViewById(R.id.signCar)).getText().toString();
                    if (phone.isEmpty() || name.isEmpty() || cnic.isEmpty() || pass.isEmpty() || rate.isEmpty() || area.isEmpty() || car.isEmpty()){
                        Toast.makeText(v.getContext(),"Fill All Fields",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (cnic.length() != 13){
                        Toast.makeText(v.getContext(), "CNIC should be of 13 Digits", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (pass.length() < 8){
                        Toast.makeText(v.getContext(), "Password Length should be Greater than 7", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user = new Driver(name,cnic,phone,car,Integer.parseInt(rate),area,pass);
                }


                pd.setMessage("Loading");
                if (!pd.isShowing()){
                    pd.show();
                }
                String url = "http://" + ((GlobalClass) app).getIp() + "/SMD/smd.php";


                // Request a string response
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equals("-1")) {
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    SharedPreferences login = v.getContext().getSharedPreferences("login", 0);
                                    SharedPreferences.Editor editor = login.edit();
                                    editor.putString("type", user.getType());
                                    editor.putString("loginPhone", phone);
                                    editor.putString("loginName", name);
                                    editor.putString("loginCnic", cnic);
                                    editor.putString("loginPass", pass);
                                    editor.putString("loginImage", "");
                                    if (user.getType().equals("Driver")) {
                                        editor.putString("loginCar", car);
                                        editor.putString("loginArea", rate);
                                        editor.putString("loginRate", area);
                                    }

                                    editor.apply();
                                    getActivity().finish();
                                    Intent intent = new Intent(getContext(), MapsActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    if (pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    Toast.makeText(v.getContext(),"User already Existed with same Number",Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (pd.isShowing()) {
                            pd.dismiss();
                        }
                        Toast.makeText(v.getContext(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("opt","insert" + temp);
                        if (temp.equals("Driver")){
                            params.put("phn",phone);
                            params.put("name",name);
                            params.put("cnic",cnic);
                            params.put("pass",pass);
                            params.put("area",area);
                            params.put("car",car);
                            params.put("rate",rate);
                        }
                        else{
                            params.put("phn",phone);
                            params.put("name",name);
                            params.put("cnic",cnic);
                            params.put("pass",pass);
                        }
                        return params;
                    }
                };

                // Add the request to the queue
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                Volley.newRequestQueue(v.getContext()).add(stringRequest);
            }
        });

       Spinner spinner = (Spinner) v.findViewById(R.id.signSpin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = parent.getSelectedItem().toString();
                if (temp.equals("Customer")) {
                    ((LinearLayout) v.findViewById(R.id.carVis)).setVisibility(View.GONE);
                    ((LinearLayout) v.findViewById(R.id.rateVis)).setVisibility(View.GONE);
                    ((LinearLayout) v.findViewById(R.id.areaVis)).setVisibility(View.GONE);
                } else {
                    ((LinearLayout) v.findViewById(R.id.carVis)).setVisibility(View.VISIBLE);
                    ((LinearLayout) v.findViewById(R.id.rateVis)).setVisibility(View.VISIBLE);
                    ((LinearLayout) v.findViewById(R.id.areaVis)).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?input=" + URLEncoder.encode(input, "utf8"));
            sb.append("&sensor=" +"false");
            sb.append("&key=" + API_KEY);
            URL url = new URL(sb.toString());
            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {

                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("MapsActivity", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("MapsActivity", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("MapsActivity", "Cannot process JSON results", e);
        }

        return resultList;
    }

}
