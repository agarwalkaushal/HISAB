package com.fullertonfinnovatica.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.fullertonfinnovatica.R;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
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

    String net, sundryCreditors = "0", sundryDebtors = "0", drawingValue = "0", cashValue="0", bankValue = "0";
    Integer netValue;

    TextView  netTypeText, netValueText, cap, ic, drawing, id, differenceCapital, bl, ibl, differenceLoan, sc, dc, differenceSC,
            cs, mc, dm, differenceMachinery, bank, cash, sd, dd, bd, differenceSD, inv, ii, differenceInv, maxAmount1, maxAmount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Balance Sheet</font>"));
        //TextViews
        netTypeText = findViewById(R.id.net);
        netValueText = findViewById(R.id.netValue);
        cap = findViewById(R.id.cap);
        ic = findViewById(R.id.ic);
        drawing = findViewById(R.id.drawing);
        id = findViewById(R.id.id);
        differenceCapital = findViewById(R.id.differenceCapital);
        bl = findViewById(R.id.bl);
        ibl = findViewById(R.id.ibl);
        differenceLoan = findViewById(R.id.differenceLoan);
        sc = findViewById(R.id.sc);
        dc = findViewById(R.id.dc);
        differenceSC = findViewById(R.id.differenceSC);
        cs = findViewById(R.id.cs);
        mc = findViewById(R.id.mc);
        dm = findViewById(R.id.dm);
        differenceMachinery = findViewById(R.id.differenceMachinery);
        bank = findViewById(R.id.bank);
        cash = findViewById(R.id.cash);
        sd = findViewById(R.id.sd);
        dd = findViewById(R.id.dd);
        bd = findViewById(R.id.bd);
        differenceSD = findViewById(R.id.differenceSD);
        inv = findViewById(R.id.inv);
        ii = findViewById(R.id.ii);
        differenceInv = findViewById(R.id.differenceInv);
        maxAmount1 = findViewById(R.id.maxAmount1);
        maxAmount2 = findViewById(R.id.maxAmount2);

        //Retrieving values from intent
        Intent intent = getIntent();
        ArrayList<String> adjustments = intent.getStringArrayListExtra("Adjustments");
        net = intent.getStringExtra("Net ");
        if(net.matches("Loss")) {
            netTypeText.setText("(-) Net Loss");
            net = intent.getStringExtra("Net Loss");
            netValueText.setText(net);
            netValue = -Integer.parseInt(netValueText.getText().toString());
        }
        else {
            netTypeText.setText("(+) Net Profit");
            net = intent.getStringExtra("Net Profit");
            netValueText.setText(net);
            netValue = Integer.parseInt(netValueText.getText().toString());
        }

        sundryCreditors = intent.getStringExtra("Sundry Creditors");
        sundryDebtors = intent.getStringExtra("Sundry Debtors");
        drawingValue = intent.getStringExtra("Drawing");
        cashValue = intent.getStringExtra("Cash");
        bankValue = intent.getStringExtra("Bank");

        sc.setText(sundryCreditors);
        sd.setText(sundryDebtors);
        drawing.setText(drawingValue);
        cash.setText(cashValue);
        bank.setText(bankValue);

        //Credit Side
        cap.setText(adjustments.get(2));
        ic.setText(String.valueOf(Integer.parseInt(adjustments.get(2))*Integer.parseInt(adjustments.get(10))/100));
        id.setText(String.valueOf(Integer.parseInt(drawing.getText().toString())* Integer.parseInt(adjustments.get(12))/100));

        differenceCapital.setText(
                String.valueOf(Integer.parseInt(cap.getText().toString())+
                Integer.parseInt(ic.getText().toString())-
                Integer.parseInt(drawing.getText().toString())-
                Integer.parseInt(id.getText().toString())+(netValue))
        );

        bl.setText(adjustments.get(3));
        ibl.setText(String.valueOf(Integer.parseInt(adjustments.get(3))*
                Integer.parseInt(adjustments.get(11))/100));
        differenceLoan.setText(String.valueOf(Integer.parseInt(bl.getText().toString())-
                Integer.parseInt(ibl.getText().toString())));

        sc.setText(sundryCreditors);
        dc.setText(String.valueOf(Integer.parseInt(sc.getText().toString())*
                Integer.parseInt(adjustments.get(9))/100));
        differenceSC.setText(String.valueOf(Integer.parseInt(sc.getText().toString())-
                Integer.parseInt(dc.getText().toString())));

        //Debit Side
        cs.setText(adjustments.get(1));
        mc.setText(adjustments.get(5));
        dm.setText(String.valueOf(Integer.parseInt(mc.getText().toString())*
                Integer.parseInt(adjustments.get(7))/100));
        differenceMachinery.setText(String.valueOf(Integer.parseInt(mc.getText().toString())-
                Integer.parseInt(dm.getText().toString())));

        bd.setText(adjustments.get(6));
        sd.setText(sundryDebtors);
        dd.setText(String.valueOf(Integer.parseInt(sd.getText().toString())*
                Integer.parseInt(adjustments.get(8))/100));
        differenceSD.setText(String.valueOf(Integer.parseInt(sd.getText().toString())-
                Integer.parseInt(dd.getText().toString())-
                Integer.parseInt(bd.getText().toString())));

        inv.setText(adjustments.get(4));
        ii.setText(String.valueOf(Integer.parseInt(adjustments.get(4))*
                Integer.parseInt(adjustments.get(13))/100));
        differenceInv.setText(String.valueOf(Integer.parseInt(inv.getText().toString())-
                Integer.parseInt(ii.getText().toString())));

        Integer debitTotal = Integer.parseInt(cs.getText().toString())+
                Integer.parseInt(differenceMachinery.getText().toString())+
                Integer.parseInt(differenceSD.getText().toString())+
                Integer.parseInt(differenceInv.getText().toString())+
                Integer.parseInt(cash.getText().toString())+
                Integer.parseInt(bank.getText().toString());

        Integer creditTotal = Integer.parseInt(differenceCapital.getText().toString())+
                Integer.parseInt(differenceLoan.getText().toString())+
                Integer.parseInt(differenceSC.getText().toString());

        if (debitTotal>creditTotal){
            maxAmount1.setText(String.valueOf(debitTotal));
            maxAmount2.setText(String.valueOf(debitTotal));
        }
        else
        {
            maxAmount1.setText(String.valueOf(creditTotal));
            maxAmount2.setText(String.valueOf(creditTotal));
        }

        /*

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

//                        JsonObject body = response.body();
//
//                        creditBalance = String.valueOf(body.get("creditBalance"));
//                        debitBalance = String.valueOf(body.get("debitBalance"));
//
//                        Log.e("nnn", creditBalance + "  " + debitBalance);

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

        */

    }

    /*
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
    */

}
