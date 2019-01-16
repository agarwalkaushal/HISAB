package com.fullertonfinnovatica.Inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.fullertonfinnovatica.Networking.NetworkingAPI;
import com.fullertonfinnovatica.Networking.NetworkingAdapter;
import com.fullertonfinnovatica.Networking.NetworkingModel;
import com.fullertonfinnovatica.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventoryView extends AppCompatActivity {

    RecyclerView recyclerView1;
    InventoryAdapter dataAdapter;
    Call<List<InventoryModel>> call;
    InventoryAPI inventoryAPI;
    Retrofit retrofit;

    List<InventoryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view);

        recyclerView1 = findViewById(R.id.recycler_inventory);

        retrofit = new Retrofit.Builder().baseUrl(InventoryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inventoryAPI = retrofit.create(InventoryAPI.class);
        call = inventoryAPI.getStuff();

        call.enqueue(new Callback<List<InventoryModel>>() {

            @Override
            public void onResponse(Call<List<InventoryModel>> call, Response<List<InventoryModel>> response) {

                list = response.body();
                //Toast.makeText(getBaseContext(),""+list.size(),Toast.LENGTH_LONG).show();
                dataAdapter = new InventoryAdapter(list, getBaseContext());
                recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView1.setAdapter(dataAdapter);

            }

            @Override
            public void onFailure(Call<List<InventoryModel>> call, Throwable t) {

                Toast.makeText(getBaseContext(),"API failure : "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
