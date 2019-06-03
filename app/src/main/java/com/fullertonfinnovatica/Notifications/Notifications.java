package com.fullertonfinnovatica.Notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.Inventory.InventoryAPI;
import com.fullertonfinnovatica.Inventory.InventoryModel;
import com.fullertonfinnovatica.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationsAdapter dataAdapter;
    NotificationsModel model, model2;
    List<NotificationsModel> list;
    Retrofit retrofit;
    Call<LoginModel> loginCall;
    Call<JsonObject> inventoryCall;
    InventoryAPI apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Notifications</font>"));
        View view = findViewById(R.id.holder);
        model = new NotificationsModel();
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

        loginCall = apiInterface.login(getString(R.string.user_id), getString(R.string.user_pass));

        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                inventoryCall = apiInterface.getInventoryy(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                inventoryCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if(response.body()!=null) {
                            JsonObject bodyyy = response.body();
                            JsonArray bodyy = bodyyy.getAsJsonArray("inventory");

                            for (int i = 0; i < bodyy.size(); i++) {

                                InventoryModel inventoryModel = new InventoryModel();
                                JsonObject jsonObject = (JsonObject) bodyy.get(i);

                                inventoryModel.setInventory_category(jsonObject.get("category").toString());
                                inventoryModel.setInventory_cost(jsonObject.get("cost").toString());
                                inventoryModel.setInventory_name(jsonObject.get("name").toString());
                                inventoryModel.setInventory_qty(jsonObject.get("quantity").toString());
                                inventoryModel.setThreshold(jsonObject.get("thresholdQuantity").toString());
                                inventoryModel.setExpiryDate(jsonObject.get("expiry").toString());
                                Log.e("lolo", inventoryModel.getInventory_qty() + "-" + inventoryModel.getThreshold());

                                if(Integer.valueOf(inventoryModel.getInventory_qty())<=Integer.valueOf(inventoryModel.getThreshold())){

                                    model = new NotificationsModel();
                                    model.setBody(inventoryModel.getInventory_name().substring(1,inventoryModel.getInventory_name().length()-1)+" stock is running out!");
                                    model.setTitle("LOW STOCK ALERT");
                                    model.setImg("threshold");
                                    list.add(model);

                                }

                                String expiry = inventoryModel.getExpiryDate().split("T")[0].substring(1);
                                String pattern = "MM/dd/yyyy";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                final String dateee = simpleDateFormat.format(new Date());

                                int month = Integer.valueOf(dateee.split("/")[0]);
                                int day = Integer.valueOf(dateee.split("/")[1]);
                                int year = Integer.valueOf(dateee.split("/")[2]);

                                int expiryMonth = Integer.valueOf(expiry.split("-")[1]);
                                int expiryYear = Integer.valueOf(expiry.split("-")[0]);
                                int expiryDay = Integer.valueOf(expiry.split("-")[2]);

                                Log.e("njj", month + " " + expiryMonth);
                                Log.e("njj", day + " " + expiryDay);
                                Log.e("njj", year + " " + expiryYear);

                                if((expiryMonth < month && year == expiryYear) || year>expiryYear || (expiryDay<=day && expiryMonth == month && year == expiryYear)) {
                                    model = new NotificationsModel();
                                    model.setBody(inventoryModel.getInventory_name().substring(1,inventoryModel.getInventory_name().length()-1)+" has expired");
                                    model.setTitle("EXPIRY ALERT");
                                    model.setImg("expiry");
                                    list.add(model);
                                }

                                if(month == expiryMonth && year == expiryYear && expiryDay>day) {
                                    model = new NotificationsModel();
                                    model.setBody(inventoryModel.getInventory_name().substring(1,inventoryModel.getInventory_name().length()-1)+" is approaching expiry");
                                    model.setTitle("EXPIRY ALERT");
                                    model.setImg("expiry");
                                    list.add(model);
                                }
//
                                }

                            model = new NotificationsModel();
                            model.setBody("Ramesh General Stores is now open nearby");
                            model.setTitle("NEW RETAILER");
                            model.setImg("shop");
                            list.add(model);

                            recyclerView = findViewById(R.id.recycler);
                            dataAdapter = new NotificationsAdapter(list, getBaseContext(), view);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(dataAdapter);
                            ItemTouchHelper itemTouchHelper = new
                                    ItemTouchHelper(new SwipeToDeleteCallback(dataAdapter));
                            itemTouchHelper.attachToRecyclerView(recyclerView);

                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

//        model.setBody("GoodDay is approaching expiry");
//        model.setTitle("EXPIRY ALERT");
//        model.setImg("expiry");
//        list.add(model);
//        list.add(model);
//        list.add(model);
//        list.add(model);
//
//        recyclerView = findViewById(R.id.recycler);
//        dataAdapter = new NotificationsAdapter(list, getBaseContext(), view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        recyclerView.setAdapter(dataAdapter);
//        ItemTouchHelper itemTouchHelper = new
//                ItemTouchHelper(new SwipeToDeleteCallback(dataAdapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);

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
