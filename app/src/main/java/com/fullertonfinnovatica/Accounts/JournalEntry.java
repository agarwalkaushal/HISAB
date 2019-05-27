package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fullertonfinnovatica.R;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalEntry extends AppCompatActivity {

    Call<JournalEntryModel> entryCall;
    Call<LoginModel> loginCall;
    AccountsAPI apiInterface;
    Retrofit retrofit;
    EditText edTo, edFrom, edDate, edDebit, edCredit, edNarration;
    String to, from, date, debit, credit, narration;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);

        edTo = findViewById(R.id.je_to);
        edFrom = findViewById(R.id.je_from);
        edDate = findViewById(R.id.je_date);
        edDebit = findViewById(R.id.je_debit);
        edCredit = findViewById(R.id.je_credit);
        edNarration = findViewById(R.id.je_narration);
        send = findViewById(R.id.sendJE);

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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to = edTo.getText().toString();
                from = edFrom.getText().toString();
                date = edDate.getText().toString();
                debit = edDebit.getText().toString();
                credit = edCredit.getText().toString();
                narration = edNarration.getText().toString();

                loginCall = apiInterface.login("demouserid","demo");

                loginCall.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                        Log.e("blabla", from + " " + to+ " " +date+ " " +debit+ " " +credit+ " " +narration);

                        entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                from, to, date, debit, credit, narration);

//                        ledgerPostCall = apiInterface.ledgerPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
//                                from, to, date, debit, credit, narration);
//
//                        try {
//                            ledgerPostCall.execute();
//                            Log.e("nll", "Posted");
//                        } catch (IOException e) {
//                            Log.e("nll", e.toString());
//                        }

//                        entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
//                                from, to, date, debit, credit, narration);


                        entryCall.enqueue(new Callback<JournalEntryModel>() {
                            @Override
                            public void onResponse(Call<JournalEntryModel> call, Response<JournalEntryModel> response) {

                                if(response.code() == 200)
                                    Toast.makeText(getBaseContext(), "Entry successfully made", Toast.LENGTH_LONG).show();

                                else
                                    Toast.makeText(getBaseContext(), "Error : " + response.code() + " " + response.message(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<JournalEntryModel> call, Throwable t) {

                                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {

                        Log.d("ErrorString", t.toString());

                    }
                });


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
