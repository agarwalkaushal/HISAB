package com.fullertonfinnovatica.Networking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingMain extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = NetworkingMain.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    //private TextView lblLocation;
    int MY_PERMISSIONS_REQUEST_READ_LOCATION;
    double latitude,longitude;

    RecyclerView recyclerView1;
    NetworkingAdapter dataAdapter;

    List<NetworkingModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Networking</font>"));
        recyclerView1=findViewById(R.id.networking_recycler);

        //TODO: Request for network permission

        if (ContextCompat.checkSelfPermission(NetworkingMain.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(NetworkingMain.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_LOCATION);

        }

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("NETWORKING_BUNDLE");
        list = (ArrayList<NetworkingModel>) args.getSerializable("NETWORKING_ARRAYLIST");

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                displayLocation();
                dataAdapter = new NetworkingAdapter(list, getBaseContext());
                recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView1.setAdapter(dataAdapter);

                recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(),
                        recyclerView1, new ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        //Values are passing to activity & to fragment as well
//                        Toast.makeText(NetworkingMain.this, "Single Click on position :"+position,
//                                Toast.LENGTH_SHORT).show();

                        NetworkingModel pojo;

                        pojo = list.get(position);
                        Intent in = new Intent(NetworkingMain.this,NetworkingDetailsScreen.class);
                        in.putExtra("b_name", pojo.getBname());
                        in.putExtra("b_lat",pojo.getLatitude());
                        in.putExtra("b_long",pojo.getLongitude());
                        in.putExtra("b_pno",pojo.getPno());
                        in.putExtra("b_type", pojo.getType());
                        startActivity(in);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
//                            Toast.makeText(NetworkingMain.this, "Long press on position :"+position,
//                                    Toast.LENGTH_LONG).show();
                    }
                }));


            }
        },2000);

    }

    @SuppressLint("MissingPermission")
    private void displayLocation() {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            //Toast.makeText(getBaseContext(),latitude + ", " + longitude,Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getBaseContext(),"Unable to fetch your location",Toast.LENGTH_LONG).show();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Toast.makeText(getBaseContext(),"Connected",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
