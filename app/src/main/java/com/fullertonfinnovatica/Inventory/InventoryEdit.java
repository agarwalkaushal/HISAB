package com.fullertonfinnovatica.Inventory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.R;
import com.fullertonfinnovatica.Transaction.TransactionAPIs;
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

public class InventoryEdit extends AppCompatActivity {

    AutoCompleteTextView product_name;
    EditText product_rate, product_quantity, product_threshold;
    RadioButton product_cat_button;
    RadioGroup product_cat_group;
    Button product_expiry, update_product;
    String date, product_category;
    String product;
    InventoryModel inventoryModel;
    List<InventoryModel> list;
    String[] products;
    ArrayAdapter<String> products_adapter;

    Call<JsonObject> inventoryCall;
    Call<LoginModel> loginCall;
    InventoryAPI apiInterface;
    Retrofit retrofit;

    boolean isNameFromInventory;

    RelativeLayout progressParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'Edit Inventory</font>"));
        progressParent = findViewById(R.id.progressParent);
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
//        progressParent.setVisibility(View.GONE);
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


        loginCall = apiInterface.login("demo", "demo");
        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                inventoryCall = apiInterface.getInventoryy(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));
                inventoryCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressParent.setVisibility(View.GONE);

                        if(response.body()!=null){

                            JsonObject bodyyy = response.body();
                            JsonArray bodyy = bodyyy.getAsJsonArray("inventory");
                            product = "";
                            for (int i = 0; i < bodyy.size(); i++) {

                                inventoryModel = new InventoryModel();
                                JsonObject jsonObject = (JsonObject) bodyy.get(i);

                                inventoryModel.setInventory_category(jsonObject.get("category").toString());
                                inventoryModel.setInventory_cost(jsonObject.get("cost").toString());
                                inventoryModel.setInventory_name(jsonObject.get("name").toString());
                                inventoryModel.setInventory_qty(jsonObject.get("quantity").toString());
                                list.add(inventoryModel);
                                product += inventoryModel.getInventory_name().substring(1, inventoryModel.getInventory_name().length() - 1) + ",";
//                                qtyString+=inventoryModel.getInventory_qty()+",";

                            }

                            Toast.makeText(getBaseContext(), "Products loaded from Inventory", Toast.LENGTH_LONG).show();
                            products = product.split(",");
                            products_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.select_dialog_item, products);
                            product_name.setAdapter(products_adapter);

                            product_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (!hasFocus) {
                                        checkNameFromInventory();
                                    }
                                }
                            });

                            update_product.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                }
                            });


                        }else{
                            Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                    }
                });


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });



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

    private void checkNameFromInventory() {
        String inputName = product_name.getText().toString();
        int c = 0;
        if (products != null) {
            for (String i : products) {
                if (i.compareTo(inputName) == 0) {
                    isNameFromInventory = true;
                    c++;
                    break;
                }
            }
        }
        if (c == 0) {
            product_name.setError("Name error! Item not in inventory");
            isNameFromInventory = false;
            //Toast.makeText(getBaseContext(), "Enter a product name that exists in inventory, or add that item in inventory and proceed", Toast.LENGTH_LONG).show();
        }
    }

}
