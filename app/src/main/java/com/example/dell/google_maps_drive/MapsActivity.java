package com.example.dell.google_maps_drive;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import com.facebook.Profile;
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,NavigationDrawerFragment.OnFragmentInteractionListener
{

    private GoogleMap mMap;
    AutoCompleteTextView searchAddr;
    static List<Address> addressList;
    static List<Address> addressList1;
    double current_lang;
    double current_lat;
    Location myLocation;
    static Context context;
    static ProgressDialog progressDialog;
    String destination_addr;
    String destination_city;
    static LatLng currLocation;
    static LatLng destLocation;
    private static final int GPS_ERRORDIALOG_REQUEST = 9001;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private int[] colors = new int[]{R.color.primary_dark,R.color.primary,R.color.primary_light,R.color.accent,R.color.primary_dark_material_light};
    Button search;
    private static final String LOG_TAG_NETWORK = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private boolean isLocationEnabled=false;
    private static String LOG_TAG = "MyActivity";
    private GoogleCloudMessaging gcm;
    String duration;
private int PLAY_SERVICES_RESOLUTION_REQUEST=9000;
    String regId;
    String project_number="31903821228";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    Boolean Loaded;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    String currAddress;
    String destAddress;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private GoogleApiClient client;
    private ArrayList<Polyline> polylines;
    private Toolbar t_bar;
    AtomicInteger msgId = new AtomicInteger();
private GcmRegistrationAsyncTask RegisteredGcm;
    ImageView imageV;
    RoundImage roundI;
    String distance;
TextView user_name;
    private Profile profile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        t_bar=(Toolbar)findViewById(R.id.activity_toolbar);

        setSupportActionBar(t_bar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        t_bar.setTitle("TaxiApp");
       imageV=(ImageView)findViewById(R.id.display_photo);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.default_pic);
        roundI=new RoundImage(bitmap);
        imageV.setImageDrawable(roundI);
Loaded=false;
        user_name=(TextView)findViewById(R.id.user_name);
        SharedPreferences sp = getSharedPreferences("login",0);
        String t = sp.getString("loginName", "");
        user_name.setText(t);

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        Fragment pf= new ProfileFragment();
        ft.replace(R.id.user_fragments, pf);
        ft.commit();

        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), t_bar,this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        context=this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        polylines = new ArrayList<>();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API).build();

        if(checkPlayServices()) {
            RegisteredGcm=new GcmRegistrationAsyncTask(this);
            RegisteredGcm.execute();
        }

    }

    private SharedPreferences getAppPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            current_lang = location.getLongitude();
            current_lat = location.getLatitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
            return;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            return;
        }

        @Override
        public void onProviderEnabled(String provider) {
            return;
        }


    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Get Location Manager and check for GPS & Network location services

mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if(isLocationEnabled ) {
            Loaded=true;
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                LatLng Lahore = new LatLng(31.582045, 74.329376);

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
                List<String> providers = locationManager.getProviders(true);
                Location myLocation = null;
                for (String provider : providers) {
                    myLocation = locationManager.getLastKnownLocation(provider);
                    if (myLocation == null) {
                        continue;
                    } else {
                        break;
                    }
                }
                if(myLocation!=null) {
                    current_lang = myLocation.getLongitude();
                    current_lat = myLocation.getLatitude();
                    currLocation = new LatLng(current_lat, current_lang);
                }
                else{
                    currLocation = Lahore;
                }
                    // mMap.addMarker(new MarkerOptions().position(currLocation).title("Your Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 14.0f));
                    //  mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(31.582045, 74.329376) , 14.0f) );
                    context = this;
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        public void onMapClick(LatLng point) {
                            Geocoder geocoder = new Geocoder(context);
                            try {
                                addressList = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                                destLocation = new LatLng(point.latitude, point.longitude);
                                destination_addr = addressList.get(0).getAddressLine(0);
                                destination_city = addressList.get(0).getLocality();
                                destAddress=destination_addr+","+destination_city+","+addressList.get(0).getCountryName();

                                addressList = geocoder.getFromLocation(currLocation.latitude, currLocation.longitude, 1);
                                currAddress = addressList.get(0).getAddressLine(0) + ","+addressList.get(0).getLocality()+"," +addressList.get(0).getCountryName();

                                if (Util.Operations.isOnline(context)) {
                                    route();
                                } else {
                                    Toast.makeText(context, "No internet connectivity", Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }

    }
   public void route() {

        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Fetching route information.", true);
    Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(currLocation, destLocation)
                .build();
        routing.execute();
    }
    public void onZoomIn(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(LOG_TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(LOG_TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else if(requestCode==402){
            finish();
            Intent i= new Intent(this,MapsActivity.class);
            startActivity(i);
        }
    }



    @Override
    public void onRoutingFailure(RouteException e) {
        progressDialog.dismiss();
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(final ArrayList<Route> route, int shortestRouteIndex) {

        mMap.clear();
        progressDialog.dismiss();
        CameraUpdate center = CameraUpdateFactory.newLatLng(currLocation);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);


        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        String[] temp_route=new String[route.size()];
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % colors.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(colors[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);
            temp_route[i]=("Route " + (i + 1) + ": distance - " + route.get(i).getDistanceText() + ": duration - " + route.get(i).getDurationText()+" Total Points "+route.get(i).getPoints().size());
        //    Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();

        }

        MarkerOptions options;
        options=new MarkerOptions();
        options.position(currLocation);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(options);

        options = new MarkerOptions();
        options.position(destLocation);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        mMap.addMarker(options);

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Choose a route")
                .setItems(temp_route, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        polylines.get(which).setColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "You selected route " + (which + 1), Toast.LENGTH_LONG).show();
                        // sendMessage("Hello from compton the outsideer!!");

                        getCompleteAddressString(route.get(which).getPoints().get(1).latitude,route.get(which).getPoints().get(1).longitude);
                        duration = route.get(which).getDurationText();
                       distance=route.get(which).getDistanceText();
                        final StringBuilder sb4 = new StringBuilder(
                                distance.length() /* also inspired by seh's comment */);
                        int i = 0;
                        for (; i < distance.length(); i++) {
                            final char c = distance.charAt(i);
                            if ((c > 47 && c < 58)||c==46) {

                                sb4.append(c);
                            }
                        }
                        distance=sb4.toString();
                        final StringBuilder sb = new StringBuilder(
                                duration.length() /* also inspired by seh's comment */);
                         i = 0;
                        for (; i < duration.length(); i++) {
                            final char c = duration.charAt(i);
                            if (c > 47 && c < 58) {

                                if (i > 0) {

                                    final char d = duration.charAt(i - 1);
                                    if (d == 32) {
                                        sb.append(" ");
                                    }
                                }
                                sb.append(c);
                            }
                        }
                        String duration_int = sb.toString();

                        final StringBuilder sb1 = new StringBuilder(
                                duration_int.length() /* also inspired by seh's comment */);
                        final StringBuilder sb2 = new StringBuilder(
                                duration_int.length() /* also inspired by seh's comment */);
                        boolean flag = false;
                        i = 0;
                        for (; i < duration_int.length(); i++) {
                            final char c = duration_int.charAt(i);
                            if (c > 47 && c < 58) {
                                if (i > 0 ) {
                                    final char d = duration.charAt(i);
                                    if (d == 32) {
                                        flag = true;
                                        continue;
                                    }
                                }
                                if (!flag) {
                                    sb1.append(c);
                                } else {
                                    sb2.append(c);
                                }
                            }
                        }
                        String duration_hour;
                        String duration_min;
                        if (flag) {
                            duration_hour = sb1.toString();
                            duration_min = sb2.toString();
                        } else {
                            duration_hour = "00";
                            duration_min = sb1.toString();
                        }

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
                        final String requestTime = year+":"+month+":"+day+" "+requestHour + ":" + requestMin + ":00";
                        final String durationTime=duration_hour+":"+duration_min+":00";

                        int totalHours = Integer.parseInt(requestHour) + Integer.parseInt(duration_hour);
                        int totalMinutes = Integer.parseInt(requestMin) + Integer.parseInt(duration_min);
                        int totalSeconds = 0;
                        if (totalSeconds >= 60) {
                            totalMinutes ++;
                            totalSeconds = totalSeconds % 60;
                        }
                        if (totalMinutes >= 60) {
                            totalHours ++;
                            totalMinutes = totalMinutes % 60;
                        }

                        final String endTime=year+":"+month+":"+day+" "+totalHours+""+":"+totalMinutes+":00";
                      //  final String currAddress = getAddress(currLocation);
                       // final String destAddress = getAddress(destLocation);
                        SharedPreferences sp = getSharedPreferences("login", 0);
                        final String name = sp.getString("loginPhone", "");

                        String url = "http://" + ((GlobalClass) getApplication()).getIp() + "/SMD/smd.php";

                        // Request a string response
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("-1")) {
                                            Toast.makeText(getApplicationContext(), "DB Error", Toast.LENGTH_LONG).show();
                                        } else {

                                            SharedPreferences sp = getSharedPreferences("login", 0);
                                            String curr_phone = sp.getString("loginPhone", "");

                                            new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "Request For A Ride,"+curr_phone),new Pair<Context,String>(context,name.replace(",",""))
                                                    ,new Pair<Context,String>(context,currAddress.replace(",", "")),new Pair<Context,String>(context,destAddress.replace(",",""))
                                                    ,new Pair<Context,String>(context,requestTime.replace(",",""))
                                            );
                                            Toast.makeText(getApplicationContext(), "Request sent to drivers", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "FAILED TO CONNECT", Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("opt", "insertDeal");
                                params.put("customer",name.replace(",", ""));
                                params.put("sTime",requestTime.replace(",", ""));
                                params.put("eTime",endTime.replace(",", ""));
                                params.put("dist",distance.replace(",", ""));
                                params.put("sLoc",currAddress.replace(",", ""));
                                params.put("eLoc",destAddress.replace(",", ""));
                                return params;
                            }
                        };


                        int socketTimeout = 30000;//30 seconds - change to what you want
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        stringRequest.setRetryPolicy(policy);
                        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                        //Toast.makeText(getApplicationContext(),requestTime, Toast.LENGTH_LONG).show();

                    }

                }).show();


    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
       // Toast.makeText(this,strAdd,Toast.LENGTH_LONG).show();
        return strAdd;
    }

//    void sendToGcm() {
//        new AsyncTask<Void,Void,String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                    Bundle data = new Bundle();
//                    data.putString("my_message", "Hello World");
//                    data.putString("my_action", "SAY_HELLO");
//                    String id = Integer.toString(msgId.incrementAndGet());
//                    RegisteredGcm.gcm.send(project_number + "@gcm.googleapis.com", id, data);
//                    msg = "Sent message";
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//            }
//        }.execute(null, null, null);
//    }
    @Override
    public void onRoutingCancelled() {
        Log.i(LOG_TAG, "Routing was cancelled.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.v(LOG_TAG, connectionResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    public void onZoomOut(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void onSearch(String dest, String start) {
        if(isConnected && Geocoder.isPresent()) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(start, 1);//second arg is maximum number of location
                addressList1 = geocoder.getFromLocationName(dest, 1);//second arg is maximum number of location

            } catch (IOException e) {
                e.printStackTrace();
            }
            //address class stores the object of lat and lan
            if (addressList.size()>0&& addressList1.size()>0) {
                Address addr = addressList.get(0);
                Address addr1 = addressList1.get(0);
                currLocation = new LatLng(addr.getLatitude(), addr.getLongitude());
                destLocation = new LatLng(addr1.getLatitude(), addr1.getLongitude());
                if (Util.Operations.isOnline(this)) {
                    route();
                } else {
                    Toast.makeText(this, "No internet connectivity", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No internet connectivity", Toast.LENGTH_LONG).show();
            }


        }
        else {
            Toast.makeText(this, "Sorry unable to get to or from location", Toast.LENGTH_LONG).show();
        }
        currAddress=start;
        destAddress=dest;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.google_maps_drive/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();
       // unregister();
        unregisterReceiver(receiver);
    }

    @Override

    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){

        super.onPause();
        //unregister();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    public void onStop() {
        super.onStop();
//unregister();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.dell.google_maps_drive/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            Log.v(LOG_TAG_NETWORK, "Receieved notification about network status");
            isNetworkAvailable(context);
            isLocationAvailable(context);
        }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if(!isConnected){
                            Log.v(LOG_TAG_NETWORK, "Now you are connected to Internet!");
                           /* searchAddr.setHint("Choose a destination...!");
                            searchAddr.setEnabled(true);
                            search.setVisibility(View.VISIBLE);*/
                            isConnected = true;
                            //do your processing here ---
                            //if you need to post any data to the server or get status
                            //update from the server
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG_NETWORK, "You are not connected to Internet!");
       /* searchAddr.setHint("You are not connected to Internet!");
        searchAddr.setEnabled(false);
        search.setVisibility(View.GONE);*/
        isConnected = false;
        return false;
     }
        private boolean isLocationAvailable(Context con) {

            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                // Build the alert dialog

                isLocationEnabled=false;
                AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setTitle("Location Services Not Active");
                builder.setMessage("Please enable Location Services and GPS");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Show location settings when the user acknowledges the alert dialog
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 402);

                    }
                });

                Dialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else{
                isLocationEnabled=true;
            }
            return true;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.satellite) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        }
        else if(id==R.id.normal){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        }
        else if(id==R.id.hybrid){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        }
        else if(id==R.id.terrain){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
 //           Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
   //         Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

}
