package com.fullertonfinnovatica.Inventory;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Accounts.AccountsAPI;
import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.Networking.NetworkingAPI;
import com.fullertonfinnovatica.Networking.NetworkingAdapter;
import com.fullertonfinnovatica.Networking.NetworkingModel;
import com.fullertonfinnovatica.R;
import com.fullertonfinnovatica.Transaction.Transaction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventoryView extends AppCompatActivity {


    RecyclerView recyclerView1;
    TextView inventoryEmpty;
    InventoryAdapter dataAdapter;
    InventoryModel inventoryModel;
    Call<LoginModel> loginCall;
    Call<JsonObject> inventoryCall;
    List<InventoryModel> filtered_list = new ArrayList<>();
    InventoryAPI apiInterface;
    Retrofit retrofit;

    List<InventoryModel> list;

    String type;
    String name;

    ImageView item_icon;

    RelativeLayout progressParent;
    LinearLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_view);

        progressParent = findViewById(R.id.progressParent);
        header = findViewById(R.id.header);
        header.setVisibility(View.GONE);
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

        type = getIntent().getStringExtra("Inventory type");
        name = getIntent().getStringExtra("Inventory name");

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+name+"</font>"));
        list = new ArrayList<>();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient cleint = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(InventoryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleint)
                .build();

        apiInterface = retrofit.create(InventoryAPI.class);

        loginCall = apiInterface.login("demo", "demo");

        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                inventoryCall = apiInterface.getInventoryy(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                inventoryCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        progressParent.setVisibility(View.GONE);

                        if(response.body()!=null) {
                            JsonObject bodyyy = response.body();
                            JsonArray bodyy = bodyyy.getAsJsonArray("inventory");

                            for (int i = 0; i < bodyy.size(); i++) {

                                inventoryModel = new InventoryModel();
                                JsonObject jsonObject = (JsonObject) bodyy.get(i);

                                if (jsonObject.get("category").toString().toLowerCase().contains(name.toLowerCase())) {
                                    inventoryModel.setInventory_category(jsonObject.get("category").toString());
                                    inventoryModel.setInventory_cost(jsonObject.get("cost").toString());
                                    inventoryModel.setInventory_name(jsonObject.get("name").toString());
                                    inventoryModel.setInventory_qty(jsonObject.get("quantity").toString());
                                    Log.e("lolo", jsonObject.get("category").toString() + ", " + jsonObject.get("cost").toString() + ", " + jsonObject.get("name").toString() + ", " + jsonObject.get("quantity").toString());
                                    list.add(inventoryModel);
                                }
                            }

//                            Log.e("mman", jsonObject.get("inventory_category").toString().toLowerCase() + "  " + name.toLowerCase() + jsonObject.get("inventory_category").toString().toLowerCase().contains(name.toLowerCase()) + "  " + list.size());
                            if (list.size() != 0) {
                                header.setVisibility(View.VISIBLE);
                                recyclerView1 = findViewById(R.id.recycler_inventory);
                                dataAdapter = new InventoryAdapter(list, getBaseContext());
                                recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                recyclerView1.setAdapter(dataAdapter);
                            } else {
                                recyclerView1 = findViewById(R.id.recycler_inventory);
                                recyclerView1.setVisibility(View.GONE);
                                ImageView emptyImg = findViewById(R.id.imageView2);
                                TextView emptyTxt = findViewById(R.id.inventoryEmpty);
                                emptyImg.setVisibility(View.VISIBLE);
                                emptyTxt.setVisibility(View.VISIBLE);
                            }


//                            Log.e("Pata", bodyy.toString());
                        }else{
                            Toast.makeText(getBaseContext(), "Servers are down "+response.toString(), Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Log.e("Pata", t.toString());
                        Toast.makeText(getBaseContext(), "Servers are down "+ t.toString(), Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Log.e("Pata", t.toString());
                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                finish();

            }
        });

//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");

        //Condition that inventory is empty
        //recyclerView1.setVisibility(View.INVISIBLE);


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
            Intent intent = new Intent(InventoryView.this, InventoryEdit.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getAuthToken(String userName, String password) {
        byte[] data = new byte[0];
        try {
            data = (userName + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("chekin2", "Basic " + Base64.encodeToString(data, Base64.NO_WRAP));
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

}
