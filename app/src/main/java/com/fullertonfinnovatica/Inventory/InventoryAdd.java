package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fullertonfinnovatica.R;

import java.io.File;
import java.io.OutputStream;

public class InventoryAdd extends AppCompatActivity {

    EditText ed_product_name, ed_product_qty, ed_product_cost;
    Button add;
    String product_name, product_qty, product_cost;
    String filename = "inventory";
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add);

        ed_product_cost = findViewById(R.id.product_cost);
        ed_product_qty = findViewById(R.id.product_qty);
        ed_product_name = findViewById(R.id.product_name);
        add = findViewById(R.id.add_product);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_cost = ed_product_cost.getText().toString();
                product_qty = ed_product_qty.getText().toString();
                product_name = ed_product_name.getText().toString();

            }
        });

    }
}
