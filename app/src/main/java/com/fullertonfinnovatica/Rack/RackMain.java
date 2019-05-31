package com.fullertonfinnovatica.Rack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fullertonfinnovatica.R;
import com.google.gson.Gson;

public class RackMain extends AppCompatActivity {

    private LinearLayout header;
    private ImageView rackEmptyImage;
    private TextView rackEmptyText;
    private FloatingActionButton addRack;
    private LinearLayout sNo, rackName, productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rack_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Rack Management</font>"));

        header = findViewById(R.id.header);
        rackEmptyImage = findViewById(R.id.rackEmptyImage);
        rackEmptyText = findViewById(R.id.rackEmptyText);
        addRack = findViewById(R.id.fab);
        sNo = findViewById(R.id.sNo);
        rackName = findViewById(R.id.rackName);
        productName = findViewById(R.id.productName);

        updateRacks();

        addRack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRack.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRacks();


    }

    private void updateRacks() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String currentRacks = prefs.getString("Final", "Empty");

        if (currentRacks.matches("Empty")) {
            header.setVisibility(View.GONE);
        } else {
            rackEmptyText.setVisibility(View.GONE);
            rackEmptyImage.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            String[] temp = currentRacks.split(" ");
            sNo.removeAllViews();
            rackName.removeAllViews();
            productName.removeAllViews();
            for (int i = 0; i < temp.length; i += 3) {
                TextView sno = new TextView(this);
                TextView rakName = new TextView(this);
                TextView prodName = new TextView(this);
                sno.setPadding(5,5,5,5);
                rakName.setPadding(5,5,5,5);
                prodName.setPadding(5,5,5,5);
                sno.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                rakName.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                prodName.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                sno.setGravity(Gravity.CENTER);
                rakName.setGravity(Gravity.CENTER);
                prodName.setGravity(Gravity.CENTER);
                sno.setTextSize(14);
                rakName.setTextSize(14);
                prodName.setTextSize(14);
                sno.setText(temp[i]);
                rakName.setText(temp[i+1]);
                prodName.setText(temp[i+2]);
                sNo.addView(sno);
                rackName.addView(rakName);
                productName.addView(prodName);
            }
        }

    }
}
