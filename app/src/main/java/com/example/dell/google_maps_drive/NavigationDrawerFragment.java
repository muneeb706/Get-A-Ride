package com.example.dell.google_maps_drive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

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
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationDrawerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static RecyclerView recyclerView;
    public static final String FILE_NAME="Test_pref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
private View containerView;
    private VivzAdapter adapter;
    private Toolbar t_bar;
    private AutoCompleteTextView searchBar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean mUserLearnedDrawer;//whether user is aware of drawer or not
    private boolean mFromSavedInstanceState;//to check whether drawer launched for first time or rolled back
private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mdrawerLayout;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MapsActivity map_act;
    private AutoCompleteTextView fromAddr;
    private AutoCompleteTextView toAddr;
    String startingAddr;
    String destAddr;
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyBZp5l09Im1JtaxVmCT2jH8JCoa4oVwqWc";
    private static Context cont;
    FragmentManager fm;
    private OnFragmentInteractionListener mListener;
MapsActivity act;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationDrawerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        //if not coming first time

        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }

    }
    public static List<Information> getData(){
        List<Information> data=new ArrayList<>();
        int[]icons={R.drawable.profile_pic,R.drawable.bookings,R.drawable.path,R.drawable.map_icon,R.drawable.log_out};
        String [] titles={"Profile","Bookings","Select Route","Google Map","Logout"};
        for(int i=0;i<titles.length;i++) {
            Information current = new Information();
           current.resourceId = icons[i];
            current.resourceTitle = titles[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView=(RecyclerView)layout.findViewById(R.id.drawerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new VivzAdapter(getActivity(),getData());

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                //Toast.makeText(getActivity(), "onClick " + position, Toast.LENGTH_SHORT).show();
                if(position==0){
                    fm=getFragmentManager();

                    Fragment mapFragment = fm.findFragmentById(R.id.map);
                    mapFragment.getView().setEnabled(false);
                    mapFragment.getView().setVisibility(View.GONE);

                    Fragment profile_fragment=new ProfileFragment();
                    FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.user_fragments, profile_fragment);
                    transaction.commit();

                    FrameLayout userFrame=(FrameLayout)getActivity().findViewById(R.id.user_fragments);
                    userFrame.setVisibility(View.VISIBLE);

                    mdrawerLayout.closeDrawers();

                }
                else if(position==1){
                    fm=getFragmentManager();

                    Fragment mapFragment = fm.findFragmentById(R.id.map);
                    mapFragment.getView().setEnabled(false);
                    mapFragment.getView().setVisibility(View.GONE);

//                    Fragment userFragment = fm.findFragmentById(R.id.user_fragments);
//                    userFragment.getView().setEnabled(true);
//                    userFragment.getView().setVisibility(View.VISIBLE);

                   // fm.beginTransaction().replace(R.id.user_fragments,new BookingsFragment()).commit();

                    final ArrayList<Bookings> totalBook = new ArrayList<Bookings>();
                    SharedPreferences sp = getActivity().getSharedPreferences("login", 0);
                    final String type = sp.getString("type", "");
                    final String id = sp.getString("loginPhone", "");

                    final ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("Loading");
                    if (!pd.isShowing()){
                        pd.show();
                    }

                    String url = "http://" + ((GlobalClass) getActivity().getApplication()).getIp() + "/SMD/smd.php";

                    // Request a string response
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("0")){
                                        if (pd.isShowing()) {
                                            pd.dismiss();
                                        }
                                        Toast.makeText(getActivity(), "No Bookings Available", Toast.LENGTH_LONG).show();
                                    }
                                    else if (response.equals("-1")){
                                        if (pd.isShowing()) {
                                            pd.dismiss();
                                        }
                                        Toast.makeText(getActivity(), "DB Error", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        try{
                                            Bookings bookings;
                                            JSONArray jsonArray = new JSONArray(response);
                                            for(int i = 0; i<jsonArray.length(); i++) {
                                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                                if (type.equals("Driver")) {
                                                    bookings = new Bookings(jsonObject.getString("customerId"), jsonObject.getString("startTime"), jsonObject.getString("startLocation"), jsonObject.getString("endLocation"),i+1);
                                                }
                                                else{
                                                    bookings = new Bookings(jsonObject.getString("driverId"), jsonObject.getString("startTime"), jsonObject.getString("startLocation"), jsonObject.getString("endLocation"),i+1);
                                                }

                                                totalBook.add(bookings);
                                            }
                                            if (pd.isShowing()) {
                                                pd.dismiss();
                                            }
                                            BookingsFragment.setBookings(totalBook);
                                            Fragment booking_fragment=new BookingsFragment();
                                            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.user_fragments, booking_fragment);
                                            transaction.commit();
                                            FrameLayout userFrame=(FrameLayout)getActivity().findViewById(R.id.user_fragments);
                                            userFrame.setVisibility(View.VISIBLE);

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
                            Toast.makeText(getActivity(), "FAILED TO CONNECT",Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("opt","booking");
                            params.put("id",id);
                            params.put("type",type);
                            return params;
                        }
                    };

                    // Add the request to the queue
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    Volley.newRequestQueue(getActivity()).add(stringRequest);
                    mdrawerLayout.closeDrawers();


                }
                else if(position==2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select Route");

// Set up the input
                    View dialogView = View.inflate(getActivity(), R.layout.input_route_dialog, null);
                    fromAddr = (AutoCompleteTextView) dialogView.findViewById(R.id.fromAddr);
                    toAddr = (AutoCompleteTextView) dialogView.findViewById(R.id.toAddr);
                    fromAddr.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));
                    toAddr.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

                    fromAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView parent, View view,
                                                int position, long id) {
                            String str = (String) parent.getItemAtPosition(position);
                            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        }
                    });
                    toAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView parent, View view,
                                                int position, long id) {
                            String str = (String) parent.getItemAtPosition(position);
                            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setView(dialogView);

// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startingAddr = fromAddr.getText().toString();
                            destAddr = toAddr.getText().toString();
                            if (startingAddr.length() != 0 && destAddr.length() != 0) {
                                dialog.dismiss();
                         /*   Intent i = new Intent(cont, MapsActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            i.putExtra("startAddr", startingAddr);
                            i.putExtra("destAddr", destAddr);
                            cont.startActivity(i);*/
                                mdrawerLayout.closeDrawers();
                                map_act.onSearch(destAddr, startingAddr);

                            } else {
                                Toast.makeText(cont, "Both Fields can not be empty", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();



                }
                else if(position==3){
                    fm=getFragmentManager();
//                    Fragment userFragment = fm.findFragmentById(R.id.user_fragments);
//                    userFragment.getView().setEnabled(false);
//                    userFragment.getView().setVisibility(View.GONE);
//
//
                    Fragment mapFragment = fm.findFragmentById(R.id.map);
                    mapFragment.getView().setEnabled(true);
                    mapFragment.getView().setVisibility(View.VISIBLE);

                    FrameLayout userFrame=(FrameLayout)getActivity().findViewById(R.id.user_fragments);
                    userFrame.setVisibility(View.GONE);


                    //fm.beginTransaction().replace(R.id.user_fragments,new MapsActivity()).commit();
                    mdrawerLayout.closeDrawers();


                }
                else if(position==4){




                    SharedPreferences sp = getActivity().getSharedPreferences("login",0);

                    SharedPreferences.Editor ed = sp.edit();
                    if(sp.getString("loginFacebook","").equals("Yes")){
                        ed.putString("loginFacebook","");
                        LoginManager.getInstance().logOut();
                    }
                    ed.putString("loginPhone","");
                    ed.putString("loginName","");
                    ed.putString("loginCnic","");
                    ed.putString("loginImage","");
                    if ((sp.getString("type","")).equals("Driver")) {
                        ed.putString("loginArea", "");
                        ed.putString("type", "");

                        ed.putString("loginCar", "");
                        ed.putString("loginRate", "");
                    }
                    ed.apply();

                    getActivity().finish();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onLongClick(View v, int position) {

                //Toast.makeText(getActivity(), "onLongClick " + position, Toast.LENGTH_SHORT).show();
            }
        }));


        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void setUp(int fragment_id,DrawerLayout drawer_layout,Toolbar toolbar,MapsActivity activity){
        mdrawerLayout=drawer_layout;
        adapter.setDrawerLayout(mdrawerLayout);
        adapter.setActivity(activity);
        map_act=activity;
        t_bar=toolbar;
        act=activity;
        containerView=getActivity().findViewById(fragment_id);
        drawerToggle=new ActionBarDrawerToggle(activity,mdrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
        @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
            if(!mUserLearnedDrawer){
                //if user has not just seen the drawer yet then make him see it
                mUserLearnedDrawer=true;
                saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                getActivity().invalidateOptionsMenu();//makes action bar draw again
            }

        }
            @Override

            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();//makes action bar draw again


            }
            @Override

            public void onDrawerSlide(View drawerView,float slideOffset){
                if(slideOffset<0.6) {
                    t_bar.setAlpha(1 - slideOffset);
                }
            }


        };
        if(!mUserLearnedDrawer&&!mFromSavedInstanceState){

            mdrawerLayout.openDrawer(containerView);
        }
        //for handling through hamburger icon
    mdrawerLayout.setDrawerListener(drawerToggle);
        mdrawerLayout.post(new Runnable(){
           @Override
        public void run(){
               drawerToggle.syncState();
           }
        });
    }
    public static void saveToPreferences(Context context, String preferenceNumber, String preferenceValue){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(preferenceNumber,preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context, String preferenceNumber, String defaultValue){
        SharedPreferences sp=context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(preferenceNumber,defaultValue);
    }
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gd;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context c, RecyclerView rv,ClickListener cl){
            Log.d("Vivz","RecyclerTouchListener Ctor was invoked");
            clickListener=cl;
            gd=new GestureDetector(cont,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("Vivz","onSingleTapUp was invoked "+e);

                    return true;
                    //           return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.d("Vivz","onLongPress was invoked "+e);
                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null&& clickListener!=null){
                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }
                    //             super.onLongPress(e);
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null&& clickListener!=null&&gd.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildAdapterPosition(child));

            }

            Log.d("Vivz","onInterceptTouchEvent was invoked "+gd.onTouchEvent(e)+" "+e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("Vivz","onTouchEvent was invoked");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{

        public void onClick(View v,int position);
        public void onLongClick(View v,int position);
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
