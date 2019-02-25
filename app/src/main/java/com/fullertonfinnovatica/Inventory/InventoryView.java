package com.fullertonfinnovatica.Inventory;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Networking.NetworkingAPI;
import com.fullertonfinnovatica.Networking.NetworkingAdapter;
import com.fullertonfinnovatica.Networking.NetworkingModel;
import com.fullertonfinnovatica.R;
import com.fullertonfinnovatica.Transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventoryView extends AppCompatActivity {

    LinearLayout emptyInventory;
    RecyclerView recyclerView1;
    TextView inventoryEmpty;
    InventoryAdapter dataAdapter;
    Call<List<InventoryModel>> call;
    List<InventoryModel> filtered_list = new ArrayList<>();
    InventoryAPI inventoryAPI;
    Retrofit retrofit;

    List<InventoryModel> list;

    String type;
    String name;

    ImageView item_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view);
        name = getIntent().getStringExtra("Inventory name");
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+name+"</font>"));

        /*
        type = getIntent().getStringExtra("Inventory type");
        name = getIntent().getStringExtra("Inventory name");



        recyclerView1 = findViewById(R.id.recycler_inventory);
        emptyInventory = findViewById(R.id.emptyInventory);
        inventoryEmpty = findViewById(R.id.inventoryEmpty);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
        inventoryEmpty.setTypeface(font);

        //Condition that inventory is empty
        recyclerView1.setVisibility(View.INVISIBLE);
        */

        /*else

        emptyInventory.setVisibility(View.INVISIBLE);



        retrofit = new Retrofit.Builder().baseUrl(InventoryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        inventoryAPI = retrofit.create(InventoryAPI.class);
        call = inventoryAPI.getStuff();

        call.enqueue(new Callback<List<InventoryModel>>() {

            @Override
            public void onResponse(Call<List<InventoryModel>> call, Response<List<InventoryModel>> response) {

                list = response.body();
                for(int i=0;i<list.size();i++){
                    if(list.get(i).inventory_category.equals(category)){
                        filtered_list.add(list.get(i));
                    }
                }
                //Toast.makeText(getBaseContext(),""+list.size(),Toast.LENGTH_LONG).show();
                dataAdapter = new InventoryAdapter(filtered_list, getBaseContext());
                recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView1.setAdapter(dataAdapter);

            }

            @Override
            public void onFailure(Call<List<InventoryModel>> call, Throwable t) {

                Toast.makeText(getBaseContext(),"API failure : "+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_product) {
            // TODO: Edit product details and update in server
            Toast.makeText(getApplicationContext(), "Edit Inventory", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
