package com.fullertonfinnovatica.Networking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fullertonfinnovatica.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NetworkingDetailsScreen extends AppCompatActivity implements OnMapReadyCallback {

    String lat, lon, pno, name;
    TextView t_lat, t_lon, t_pno, t_name;

    private MapView mapView;
    private GoogleMap gmap;
    private LinearLayout phoneLayout;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyBVEJlSiNVvpBAZh8nL1v_-ZsIHL6ZKUcQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking_details_screen);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Details</font>"));

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        phoneLayout = findViewById(R.id.phone);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        lat = getIntent().getStringExtra("b_lat");
        lon = getIntent().getStringExtra("b_long");
        pno = getIntent().getStringExtra("b_pno");
        name = getIntent().getStringExtra("b_name");

        t_lat = findViewById(R.id.blat);
        t_lon = findViewById(R.id.blong);
        t_name = findViewById(R.id.bname);
        t_pno = findViewById(R.id.bpno);

        t_lat.setText("Lat: " + lat);
        t_pno.setText(pno);
        t_lon.setText("Long: " + lon);
        t_name.setText(name);

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCall();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(Float.valueOf(lat), Float.valueOf(lon));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        gmap.setIndoorEnabled(true);
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        gmap.addMarker(markerOptions);
    }

    private void onClickCall() {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + pno));
        startActivity(intent);
    }


}
