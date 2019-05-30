package com.fullertonfinnovatica.Inventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.fullertonfinnovatica.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class InventoryEdit extends AppCompatActivity {

    AutoCompleteTextView product_name;
    EditText product_rate, product_quantity, product_threshold;
    RadioButton product_cat_button;
    RadioGroup product_cat_group;
    Button product_expiry, update_product;
    String date, product_category;

    RelativeLayout progressParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);

        progressParent = findViewById(R.id.progressParent);
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        progressParent.setVisibility(View.GONE);

        product_name = findViewById(R.id.product_name);
        product_rate = findViewById(R.id.product_rate);
        product_quantity = findViewById(R.id.product_quantity);
        product_threshold = findViewById(R.id.product_threshold);
        product_expiry = findViewById(R.id.product_expiry);
        product_cat_group = findViewById(R.id.product_category);
        update_product = findViewById(R.id.update_product);

        int selectedId = product_cat_group.getCheckedRadioButtonId();
        product_cat_button = (RadioButton) findViewById(selectedId);

        // TODO: In autocomplete text view select name from inventory,
        //  If valid then set rate, quantity, threshold, expiry, category

        // TODO: ON update_product update from values/product name cannot be changed

    }
}
