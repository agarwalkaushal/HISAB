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
import android.widget.Toast;

import com.fullertonfinnovatica.Login;
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

public class Ledger extends AppCompatActivity {

    Retrofit retrofit;
    AccountsAPI apiInterface;
    Call<LoginModel> loginCall;
    Call<JsonObject> ledgerCall;
    Call<JsonObject> ledgerPopulateCall;
    List<LedgerModel> ledgerList;
    RecyclerView recyclerView;
    LedgerAdapter dataAdapter;

    String account_name, balance_type, balance_amt;
    String[] debit_name, debit_amt, credit_name, credit_amt;

    RelativeLayout progressParent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Ledger</font>"));

        progressParent = findViewById(R.id.progressParent);
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);


        ledgerList = new ArrayList<>();

        recyclerView = findViewById(R.id.ledgerRecycler);

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
                ledgerCall = apiInterface.getLedger(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                ledgerCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

//                        Log.e("kyu", response.toString());
                        progressParent.setVisibility(View.GONE);
                        if (response.body() != null) {
                            JsonObject bodyy = response.body();
                            JsonArray ledgerAray = bodyy.getAsJsonArray("ledger");
                            for (int i = 0; i < ledgerAray.size(); i++) {
                                debit_amt = new String[10000];
                                debit_name = new String[10000];
                                credit_name = new String[10000];
                                credit_amt = new String[10000];
                                JsonObject ledger = ledgerAray.get(i).getAsJsonObject();

                                JsonObject account = ledger.getAsJsonObject("account");
                                account_name = String.valueOf(account.get("name"));

                                JsonArray debitsArray = ledger.getAsJsonArray("debits");
                                JsonArray creditsArray = ledger.getAsJsonArray("credits");


                                Log.e("Acc name", i+" "+account_name);

                                for (int j = 0; j < debitsArray.size(); j++) {

                                    JsonObject deb;
                                    deb = debitsArray.get(j).getAsJsonObject();
                                    debit_amt[j] = String.valueOf(deb.get("amount"));
                                    JsonObject to = deb.getAsJsonObject("to");
                                    debit_name[j] = String.valueOf(to.get("name"));
                                    Log.e("Debits", j+" "+debit_amt[j] + " " + debit_name[j]);

                                }

                                for (int j = 0; j < creditsArray.size(); j++) {

                                    JsonObject cred;
                                    cred = creditsArray.get(j).getAsJsonObject();
                                    credit_amt[j] = String.valueOf(cred.get("amount"));
                                    JsonObject to = cred.getAsJsonObject("from");
                                    credit_name[j] = String.valueOf(to.get("name"));
                                    Log.e("Credits", j+" "+credit_amt[j] + " " + credit_name[j]);
                                }

                                JsonObject balance = ledger.getAsJsonObject("balance");
                                balance_type = String.valueOf(balance.get("type"));
                                balance_amt = String.valueOf(balance.get("amount"));

                                LedgerModel model = new LedgerModel();

                                model.setAccount_name(account_name);
                                model.setBalance_amt(balance_amt);
                                model.setBalance_type(balance_type);
                                model.setCredit_amt(credit_amt);
                                model.setCredit_name(credit_name);
                                model.setDebit_amt(debit_amt);
                                model.setDebit_name(debit_name);
                                model.setDebitSize(debitsArray.size());
                                model.setCreditSize(creditsArray.size());

                                ledgerList.add(model);

                            }

                            dataAdapter = new LedgerAdapter(ledgerList, getBaseContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerView.setAdapter(dataAdapter);
                        }else{
                            Toast.makeText(getBaseContext(), "No entries found", Toast.LENGTH_LONG).show();
                            finish();
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Log.e("blabla", t.toString());
                        Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                    }
                });



            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                Log.e("blabla", t.toString());
                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

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
