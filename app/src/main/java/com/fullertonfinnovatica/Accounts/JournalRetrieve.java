package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import com.fullertonfinnovatica.Networking.NetworkingAdapter;
import com.fullertonfinnovatica.R;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalRetrieve extends AppCompatActivity {

    Call<JournalEntryListModel> retrieveCall;
    Call<LoginModel> loginCall;
    AccountsAPI apiInterface;
    Retrofit retrofit;

    RecyclerView recyclerView1;
    JournalRetrieveAdapter dataAdapter;

    List<JournalEntryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_retrieve);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient cleint = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
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

                        list = response.body().getContacts();
                        recyclerView1 = findViewById(R.id.journal_recycler);
                        dataAdapter = new JournalRetrieveAdapter(list, getBaseContext());
                        recyclerView1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        recyclerView1.setAdapter(dataAdapter);
                        //Log.e("jknks", response.body().getContacts().get(0).getFrom().toString());


                    }

                    @Override
                    public void onFailure(Call<JournalEntryListModel> call, Throwable t) {

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
