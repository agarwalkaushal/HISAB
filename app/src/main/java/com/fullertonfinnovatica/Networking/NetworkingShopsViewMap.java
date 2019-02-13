package com.fullertonfinnovatica.Networking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.fullertonfinnovatica.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkingShopsViewMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyBVEJlSiNVvpBAZh8nL1v_-ZsIHL6ZKUcQ";
    private MapView mapView;
    private GoogleMap gmap;
    Bundle mapViewBundle = null;

    Call<List<NetworkingModel>> call;
    NetworkingAPI networkingAPI;
    Retrofit retrofit;
    List<NetworkingModel> list;

    List<String> shops_lat = new ArrayList<>();
    List<String> shops_long = new ArrayList<>();
    List<String> shops_name = new ArrayList<>();
    List<String> shops_num = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking_shops_view_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);


        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        retrofit = new Retrofit.Builder().baseUrl(NetworkingAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkingAPI = retrofit.create(NetworkingAPI.class);
        call = networkingAPI.getBirds();

        call.enqueue(new Callback<List<NetworkingModel>>() {

            @Override
            public void onResponse(Call<List<NetworkingModel>> call, Response<List<NetworkingModel>> response) {

                list = response.body();
                for(int i=0;i<list.size();i++){
                    shops_lat.add(list.get(i).getLatitude());
                    shops_long.add(list.get(i).getLongitude());
                    shops_name.add(list.get(i).getBname());
                    shops_num.add(list.get(i).getPno());
                }

                mapView.getMapAsync(NetworkingShopsViewMap.this);

            }

            @Override
            public void onFailure(Call<List<NetworkingModel>> call, Throwable t) {

                Toast.makeText(getBaseContext(),"API failure"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NetworkingMain.class);
                Bundle args = new Bundle();
                args.putSerializable("NETWORKING_ARRAYLIST",(Serializable)list);
                intent.putExtra("NETWORKING_BUNDLE",args);
                startActivity(intent);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        for(int i=0;i<shops_lat.size();i++) {
            LatLng ny = new LatLng(Float.valueOf(shops_lat.get(i)), Float.valueOf(shops_long.get(i)));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(ny);
            markerOptions.title(shops_name.get(i));
            markerOptions.snippet(shops_num.get(i));
            gmap.addMarker(markerOptions);
        }
        LatLng ny = new LatLng(Float.valueOf(shops_lat.get(0)), Float.valueOf(shops_long.get(0)));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

}
