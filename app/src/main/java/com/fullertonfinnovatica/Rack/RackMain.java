package com.fullertonfinnovatica.Rack;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

import static android.view.View.GONE;

public class RackMain extends AppCompatActivity {

    private LinearLayout header;
    private ImageView rackEmptyImage;
    private TextView rackEmptyText;
    private FloatingActionButton addRack;
    private LinearLayout sNo, rackName, productName, main;

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
        main = findViewById(R.id.main);
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
        String currentRacks = prefs.getString("Nexus", "Empty");

        if (currentRacks.matches("Empty")) {
            header.setVisibility(GONE);
            rackEmptyText.setVisibility(View.VISIBLE);
            rackEmptyImage.setVisibility(View.VISIBLE);
            main.setVisibility(GONE);
        } else {
            rackEmptyText.setVisibility(GONE);
            rackEmptyImage.setVisibility(GONE);
            header.setVisibility(View.VISIBLE);
            main.setVisibility(View.VISIBLE);
            String[] temp = currentRacks.split(" ");
            sNo.removeAllViews();
            rackName.removeAllViews();
            productName.removeAllViews();
            for (int i = 0; i < temp.length; i += 3) {
                TextView sno = new TextView(this);
                TextView rakName = new TextView(this);
                TextView prodName = new TextView(this);
                sno.setPadding(7,7,7,7);
                rakName.setPadding(7,7,7,7);
                prodName.setPadding(7,7,7,7);
                sno.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                rakName.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                prodName.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                sno.setGravity(Gravity.CENTER);
                rakName.setGravity(Gravity.CENTER);
                prodName.setGravity(Gravity.CENTER);
                sno.setTextSize(16);
                rakName.setTextSize(16);
                prodName.setTextSize(16);
                sno.setText(temp[i]);
                rakName.setText(temp[i+1]);
                prodName.setText(temp[i+2]);
                sNo.addView(sno);
                rackName.addView(rakName);
                productName.addView(prodName);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            AlertDialog b = new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to empty racks?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().remove("Nexus").apply();
                            updateRacks();
                        }
                    })
                    .setNegativeButton("No", null).show();

            Button nbutton = b.getButton(DialogInterface.BUTTON_NEGATIVE);
            nbutton.setTextColor(Color.BLACK);
            Button pbutton = b.getButton(DialogInterface.BUTTON_POSITIVE);
            pbutton.setTextColor(Color.BLACK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

