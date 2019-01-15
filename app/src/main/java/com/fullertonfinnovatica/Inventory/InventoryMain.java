package com.fullertonfinnovatica.Inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fullertonfinnovatica.R;

public class InventoryMain extends AppCompatActivity {

    Button add, view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        add = findViewById(R.id.add);
        view = findViewById(R.id.vview);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), InventoryAdd.class);
                startActivity(i);

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), InventoryView.class);
                startActivity(i);

            }
        });

    }
}
