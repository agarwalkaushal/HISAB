package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.fullertonfinnovatica.R;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BalanceSheet extends AppCompatActivity {

    Retrofit retrofit;
    AccountsAPI apiInterface;
    Call<LoginModel> loginCall;
    Call<JsonObject> balanceSheetCall;

    String creditBalance, debitBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);

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

        loginCall = apiInterface.login("demouserid", "demo");

        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {


                balanceSheetCall = apiInterface.getTrial(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                balanceSheetCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        JsonObject body = response.body();

                        creditBalance = String.valueOf(body.get("creditBalance"));
                        debitBalance = String.valueOf(body.get("debitBalance"));

                        Log.e("nnn", creditBalance + "  " + debitBalance);

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
