package com.fullertonfinnovatica.Inventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fullertonfinnovatica.R;

public class InventoryDetails extends AppCompatActivity {

    String name, threshold, qty, expiry, category, cost;
    TextView tName, tThreshold, tQty, tExpiry, tCategory, tCost;
    ImageView catImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        name = getIntent().getStringExtra("inv_name");
        name = name.substring(1,name.length()-1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+name.toUpperCase()+"</font>"));
        threshold = getIntent().getStringExtra("inv_threshold");
        qty = getIntent().getStringExtra("inv_qty");
        expiry = getIntent().getStringExtra("inv_expiry");
        expiry = expiry.split("T")[0].substring(1);
        category = getIntent().getStringExtra("inv_category");
        category = category.substring(1,category.length()-1);
        cost = getIntent().getStringExtra("inv_cost");

        tName = findViewById(R.id.invName);
        tThreshold = findViewById(R.id.invThreshold);
        tQty = findViewById(R.id.invQty);
        tExpiry = findViewById(R.id.invExpiry);
        tCategory = findViewById(R.id.invCategory);
        tCost = findViewById(R.id.invCost);
        catImage = findViewById(R.id.catImage);

        tName.setText(name.toUpperCase());
        tThreshold.setText("Threshold: "+threshold);
        tQty.setText("Quantity: "+qty);
        tExpiry.setText(expiry);
        tCategory.setText(category.toUpperCase());
        tCost.setText(cost);

        String[] inventoryCat = this.getResources().getStringArray(R.array.inventory_categories);
        String[] inventoryTags = this.getResources().getStringArray(R.array.inventory_tags);

        int c=0;
        for (String temp: inventoryCat) {
            if(category.matches(temp.trim().toLowerCase()))
            {
                break;
            }
            c++;
        }
        Glide.with(this).load(this.getResources().getIdentifier(inventoryTags[c], "drawable", this.getPackageName()))
                .error(R.drawable.back)
                .into(catImage);

    }
}
