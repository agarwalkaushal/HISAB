package com.fullertonfinnovatica.Rack;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fullertonfinnovatica.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddRack extends AppCompatActivity {

    private TextView rackSno;
    private EditText rackName, productName;
    private Button add;
    private int rno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rack);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Add Rack</font>"));
        rackSno = findViewById(R.id.rackSno);
        rackName = findViewById(R.id.rack_name);
        productName = findViewById(R.id.product_name);
        add = findViewById(R.id.addRack);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String currentRacks = prefs.getString("Nexus","Empty");

        if(currentRacks.matches("Empty"))
        {
            rackSno.setText("1");
            rno = 0;
        }
        else
        {
            String[] temp = currentRacks.split(" ");
            rno = temp.length/3;
            rackSno.setText(String.valueOf(rno+1));
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rackName.getText().toString().length()!=0)
                {
                    if(productName.getText().toString().length()!=0)
                    {
                        String toCommit = rno+1+" "+rackName.getText().toString().replaceAll("\\s","")+" "+productName.getText().toString().replaceAll("\\s","")+" ";
                        if(!currentRacks.matches("Empty"))
                            toCommit = currentRacks+toCommit;
                        prefs.edit().putString("Nexus",toCommit).apply();
                        finish();
                    }
                    else
                    {
                        productName.setError("Cannot be empty");
                        return;
                    }
                }
                else{
                    rackName.setError("Cannot be empty");
                    return;
                }
            }
        });
    }
}
