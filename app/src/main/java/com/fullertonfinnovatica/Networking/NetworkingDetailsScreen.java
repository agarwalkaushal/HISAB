package com.fullertonfinnovatica.Networking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

public class NetworkingDetailsScreen extends AppCompatActivity {

    String lat, lon, pno, name;
    TextView t_lat, t_lon, t_pno, t_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networking_details_screen);

        lat = getIntent().getStringExtra("b_lat");
        lon = getIntent().getStringExtra("b_long");
        pno = getIntent().getStringExtra("b_pno");
        name = getIntent().getStringExtra("b_name");

        t_lat = findViewById(R.id.blat);
        t_lon = findViewById(R.id.blong);
        t_name = findViewById(R.id.bname);
        t_pno = findViewById(R.id.bpno);

        t_lat.setText(lat);
        t_pno.setText(pno);
        t_lon.setText(lon);
        t_name.setText(name);
    }
}
