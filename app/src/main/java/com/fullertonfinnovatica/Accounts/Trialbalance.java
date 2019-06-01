package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;
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

public class Trialbalance extends AppCompatActivity {

    RelativeLayout progressParent;
    Retrofit retrofit;
    AccountsAPI apiInterface;
    Call<JsonObject> trialCall;
    Call<LoginModel> loginCall;
    String amount, name, type;
    List<TrialModel> list;
    TrialAdapter dataAdapter;
    RecyclerView recyclerView;

    TextView totalCredit, totalDebit;

    int total_credit, total_debit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialbalance);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Trial Balance</font>"));

        totalCredit = findViewById(R.id.amount_credit);
        totalDebit = findViewById(R.id.amount_debit);

        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

        progressParent = findViewById(R.id.progressParent);
        recyclerView = findViewById(R.id.trial_recycler);

        list = new ArrayList<>();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient cleint = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(AccountsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleint)
                .build();

        apiInterface = retrofit.create(AccountsAPI.class);
        loginCall = apiInterface.login(getString(R.string.user_id), getString(R.string.user_pass));

        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Toast.makeText(getApplicationContext(),"Please hang on..",Toast.LENGTH_LONG).show();
                trialCall = apiInterface.getTrial(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                trialCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        progressParent.setVisibility(View.GONE);
                        if(response.body()!=null){
                            JsonObject bodyy = response.body();
                            JsonArray ledgerArray = bodyy.getAsJsonArray("ledger");

                            for(int i=0;i<ledgerArray.size();i++){
                                JsonObject ledgerEntry = (JsonObject) ledgerArray.get(i);
                                JsonObject balance = ledgerEntry.getAsJsonObject("balance");
                                amount = String.valueOf(balance.get("amount"));
                                type = String.valueOf(balance.get("type"));

                                JsonObject account = ledgerEntry.getAsJsonObject("account");
                                name = String.valueOf(account.get("name"));
                                TrialModel model = new TrialModel();
                                model.setAmount(amount);
                                model.setName(name);
                                model.setType(type);
                                list.add(model);

                                if(model.getType().toLowerCase().contains("debit")){
                                    total_debit+= Integer.valueOf(model.getAmount());
                                }else {
                                    total_credit+= Integer.valueOf(model.getAmount());
                                }

                            }

                            totalCredit.setText(String.valueOf(total_credit));
                            totalDebit.setText(String.valueOf(total_debit));
                            Log.e("jojo", total_credit+" "+total_debit);

                            dataAdapter = new TrialAdapter(list, getBaseContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(dataAdapter);


                        }else{
                            Toast.makeText(getBaseContext(), "No entries found", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                finish();

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

}
