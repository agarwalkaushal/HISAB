package com.fullertonfinnovatica.Inventory;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.Create;
import com.fullertonfinnovatica.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class InventoryAdd extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    EditText ed_product_name, ed_product_qty, ed_product_cost, ed_product_thrld;
    Button add;
    String product_name, product_qty, product_cost, product_cat, product_thrld, product_epx = null, date;
    InventoryAPI apiInterface;
    Retrofit retrofit;
    Call<LoginModel> loginCall;
    Call<InventoryModel> inventoryCall;
    View rootLayout;
    RadioButton ed_product_cat;
    RadioGroup product_category;

    private Button expiryDate;

    int revealX;
    int revealY;
    int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_add);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Add Product</font>"));

        expiryDate = findViewById(R.id.expDate);

        expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = 1;
                showDatePickerDialog();
                product_epx = date; //expirydate
            }
        });

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefEditor = prefs.edit();

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

        rootLayout = findViewById(R.id.add);
        ed_product_cost = findViewById(R.id.product_rate);
        ed_product_qty = findViewById(R.id.product_quantity);
        ed_product_name = findViewById(R.id.product_name);
        ed_product_thrld = findViewById(R.id.product_threshold);
        product_category = findViewById(R.id.product_category);
        add = findViewById(R.id.add_product);

        final Intent intent = getIntent();
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_cost = ed_product_cost.getText().toString();
                product_qty = ed_product_qty.getText().toString();
                product_name = ed_product_name.getText().toString();
                product_thrld = ed_product_thrld.getText().toString();
                int selectedId = product_category.getCheckedRadioButtonId();
                ed_product_cat = (RadioButton) findViewById(selectedId);

                String[] inventoryCategories = getResources().getStringArray(R.array.inventory_categories);
                String[] inventoryTags = getResources().getStringArray(R.array.inventory_tags);

                for (String i : inventoryCategories) {

                    if (i.compareTo(ed_product_cat.getText().toString()) == 0) {
                        product_cat = inventoryCategories[c];
                        c = 0;
                        break;
                    }
                    c++;
                }

                //TODO: Sent data
                loginCall.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                        inventoryCall = apiInterface.postInventory(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                product_name, product_cat, Integer.valueOf(product_qty), Integer.valueOf(product_cost));

                        inventoryCall.enqueue(new Callback<InventoryModel>() {
                            @Override
                            public void onResponse(Call<InventoryModel> call, Response<InventoryModel> response) {

                                if (response.code() == 200) {
                                    Toast.makeText(getBaseContext(), "Inventory updated!", Toast.LENGTH_LONG).show();
                                    finish();
                                } else
                                    Log.e("mana", response.toString());
                                Toast.makeText(getBaseContext(), "Servers are down : "+response.toString(), Toast.LENGTH_LONG).show();
                                finish();

                            }

                            @Override
                            public void onFailure(Call<InventoryModel> call, Throwable t) {

                                Log.e("mana", t.toString());
                                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                    }
                });

                //Storing Inventory names in disk
                String product = prefs.getString("products", "Milk,");
                product = product + product_name + ",";
                prefEditor.putString("products", product);
                prefEditor.commit();
            }
        });

    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();

        }
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


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        expiryDate.setText(date);

    }

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setThemeDark(true);
        dpd.setAccentColor(getResources().getColor(R.color.black));
    }

}
