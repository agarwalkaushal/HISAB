package com.fullertonfinnovatica.Transaction;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fullertonfinnovatica.Accounts.AccountsAPI;
import com.fullertonfinnovatica.Accounts.JournalEntryListModel;
import com.fullertonfinnovatica.Accounts.JournalEntryModel;
import com.fullertonfinnovatica.Accounts.JournalRetrieveAdapter;
import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
TransactionView extends AppCompatActivity {

    Call<JournalEntryListModel> retrieveCall;
    Call<LoginModel> loginCall;
    AccountsAPI apiInterface;
    Retrofit retrofit;

    RecyclerView recyclerView1;
    TransactionAdapter dataAdapter;

    List<JournalEntryModel> list;

    LinearLayout view;
    RelativeLayout progressParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        view = findViewById(R.id.view);
        view.setVisibility(View.GONE);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+"Past Transactions"+"</font>"));

        progressParent = findViewById(R.id.progressParent);
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

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

                retrieveCall = apiInterface.journalRetrieveExp(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                retrieveCall.enqueue(new Callback<JournalEntryListModel>() {
                    @Override
                    public void onResponse(Call<JournalEntryListModel> call, Response<JournalEntryListModel> response) {

                        view.setVisibility(View.VISIBLE);
                        progressParent.setVisibility(View.GONE);
                        if(response.body()!=null) {
                            list = response.body().getContacts();
                            recyclerView1 = findViewById(R.id.transViewRecycler);
                            dataAdapter = new TransactionAdapter(list, getBaseContext());
                            recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView1.setAdapter(dataAdapter);
                        }else{
                            Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<JournalEntryListModel> call, Throwable t) {

                        //Toast.makeText(getBaseContext(), t.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getBaseContext(), "Please try again! Error retrieving data", Toast.LENGTH_SHORT).show();
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
