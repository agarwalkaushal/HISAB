package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InventoryAdd extends AppCompatActivity implements Callback<InventoryModel> {

    EditText ed_product_name, ed_product_qty, ed_product_cost;
    Button add;
    String product_name, product_qty, product_cost;
    InventoryAPI apiInterface;
    JSONObject paramObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add);

        ed_product_cost = findViewById(R.id.product_cost);
        ed_product_qty = findViewById(R.id.product_qty);
        ed_product_name = findViewById(R.id.product_name);
        add = findViewById(R.id.add_product);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InventoryAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(InventoryAPI.class);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_cost = ed_product_cost.getText().toString();
                product_qty = ed_product_qty.getText().toString();
                product_name = ed_product_name.getText().toString();

            }
        });

        try {
            paramObject = new JSONObject();
            paramObject.put("mobile_no", "8558855896");
            paramObject.put("inventory_name", "GoodDay");
            paramObject.put("inventory_category", "Food");
            paramObject.put("inventory_cost", "15");
            paramObject.put("inventory_qty", "100");
            TextView t = findViewById(R.id.abcc);
            t.setText(paramObject.toString());
            Call<InventoryModel> userCall = apiInterface.getUser(paramObject.toString());
            userCall.enqueue(this);
            Toast.makeText(getBaseContext(), "Sent data", Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(Call<InventoryModel> call, Response<InventoryModel> response) {
    }

    @Override
    public void onFailure(Call<InventoryModel> call, Throwable t) {
    }


    void apiCall(JSONObject j){

        Call<InventoryModel> userCall = apiInterface.getUser(j.toString());
        userCall.enqueue(this);
        Toast.makeText(getBaseContext(), "Sent data", Toast.LENGTH_LONG).show();

    }

}
