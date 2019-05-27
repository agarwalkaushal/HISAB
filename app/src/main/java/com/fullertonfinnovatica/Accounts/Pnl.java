package com.fullertonfinnovatica.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.fullertonfinnovatica.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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

public class Pnl extends AppCompatActivity {

    Retrofit retrofit;
    AccountsAPI apiInterface;
    Call<LoginModel> loginCall;
    Call<JsonObject> pnlCall;

    String profit, loss, balanceAmt, balanceType, accountName;

    TextView os,totalSales, totalPurchase, totalSalesReturn, totalPurchaseReturn, differenceSales, differencePurchase,
            cs, grossprofittext1, grosslosstext1, grossprofitvalue1, grosslossvalue1, maxAmount1, maxAmount2;

    TextView grosslosstext2, grossprofittext2, grosslossvalue2, grossprofitvalue2,
    rentText1, totalRent1, rentText2, totalRent2,
    commissionText1, totalCommission1, commissionText2, totalCommission2,
    discountText1, totalDiscount1, discountText2, totalDiscount2,
    totalSalaries, dc, dm, ii, bd, idl, dd, ic, ibl,
    netlossvalue, netlosstext, netprofitvalue, netprofittext, maxAmount3, maxAmount4;

    List<Integer> debitAmountsPnl;
    List<Integer> creditAmountsPnl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnl);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Trading / Profit & Loss</font>"));

        debitAmountsPnl = new ArrayList<Integer>();
        creditAmountsPnl = new ArrayList<Integer>();

        os = findViewById(R.id.os);
        totalSales = findViewById(R.id.totalSales);
        totalPurchase = findViewById(R.id.totalPurchase);
        totalSalesReturn = findViewById(R.id.totalSalesReturn);
        totalPurchaseReturn = findViewById(R.id.totalPurchaseReturn);
        differenceSales = findViewById(R.id.differenceSales);
        differencePurchase = findViewById(R.id.differencePurchase);
        cs = findViewById(R.id.cs);
        grosslosstext1 = findViewById(R.id.grosslosstext1);
        grosslossvalue1 = findViewById(R.id.grosslossvalue1);
        grossprofittext1 = findViewById(R.id.grossprofittext1);
        grossprofitvalue1 = findViewById(R.id.grossprofitvalue1);
        maxAmount1 = findViewById(R.id.maxAmount1);
        maxAmount2 = findViewById(R.id.maxAmount2);

        grosslosstext2 = findViewById(R.id.grosslosstext2);
        grosslossvalue2 = findViewById(R.id.grosslossvalue2);
        grossprofittext2 = findViewById(R.id.grossprofittext1);
        grossprofitvalue2 = findViewById(R.id.grossprofitvalue2);
        rentText1 = findViewById(R.id.rentText1);
        totalRent1 = findViewById(R.id.totalRent1);
        rentText2 = findViewById(R.id.rentText2);
        totalRent2 = findViewById(R.id.totalRent2);
        commissionText1 = findViewById(R.id.commissionText1);
        totalCommission1 = findViewById(R.id.totalCommission1);
        commissionText2 = findViewById(R.id.commissionText2);
        totalCommission2 = findViewById(R.id.totalCommission2);
        discountText1 = findViewById(R.id.discountText1);
        totalDiscount1 = findViewById(R.id.totalDiscount1);
        discountText2 = findViewById(R.id.discountText2);
        totalDiscount2 = findViewById(R.id.totalDiscount2);
        totalSalaries = findViewById(R.id.totalSalaries);
        dc = findViewById(R.id.dc);
        dm = findViewById(R.id.dm);
        ii = findViewById(R.id.ii);
        bd = findViewById(R.id.bd);
        idl = findViewById(R.id.idl);
        ibl = findViewById(R.id.ibl);
        dd = findViewById(R.id.dd);
        ic = findViewById(R.id.ic);
        netlosstext = findViewById(R.id.netlosstext);
        netlossvalue = findViewById(R.id.netlossvalue);
        netprofittext = findViewById(R.id.netprofittext);
        netprofitvalue = findViewById(R.id.netprofitvalue);
        maxAmount3 = findViewById(R.id.maxAmount3);
        maxAmount4 = findViewById(R.id.maxAmount4);

        Intent intent = getIntent();

        ArrayList<String> adjustments = intent.getStringArrayListExtra("Adjustments");

        os.setText(adjustments.get(0));
        cs.setText(adjustments.get(1));

        // TODO: Balance amount of Account name Purchase to TextView totalPurchase 1
        // TODO: Balance amount of Account name Purchase Return to TextView totalPurchaseReturn 2
        // TODO: Balance amount of Account name Sale to TextView totalSale 3
        // TODO: Balance amount of Account name Sale Return to TextView totalSalesReturn 4
        // TODO: Difference of 1-2 to TextView differencePurchase
        // TODO: Difference of 2-4 to TextView differenceSale

        int debitSideTotal =
                Integer.valueOf(os.getText().toString())+
                Integer.valueOf(totalPurchase.getText().toString())-
                Integer.valueOf(totalPurchaseReturn.getText().toString());

        int creditSideTotal =
                Integer.valueOf(cs.getText().toString())+
                        Integer.valueOf(totalSales.getText().toString())-
                        Integer.valueOf(totalSalesReturn.getText().toString());

        // TODO: Check if Account name Rent is debit or credit. If doubt ask me. See xml layout you will understand.
        // TODO: Check if Account name Commission is debit or credit. If doubt ask me. See xml layout you will understand.
        // TODO: Check if Account name Discount is debit or credit.  If doubt ask me. See xml layout you will understand.
        // TODO: totalSalaries from Salaries account
        // TODO: For each of the four above add values to integer lists of credit or debit amounts.

        dm.setText(String.valueOf(Integer.parseInt(adjustments.get(5))*Integer.parseInt(adjustments.get(7))));
        debitAmountsPnl.add(Integer.parseInt(dm.getText().toString()));
        bd.setText(String.valueOf(Integer.parseInt(adjustments.get(6))));
        debitAmountsPnl.add(Integer.parseInt(bd.getText().toString()));
        ii.setText(String.valueOf(Integer.parseInt(adjustments.get(4))*Integer.parseInt(adjustments.get(13))));
        creditAmountsPnl.add(Integer.parseInt(ii.getText().toString()));
        ic.setText(String.valueOf(Integer.parseInt(adjustments.get(2))*Integer.parseInt(adjustments.get(10))));
        debitAmountsPnl.add(Integer.parseInt(ic.getText().toString()));
        ibl.setText(String.valueOf(Integer.parseInt(adjustments.get(3))*Integer.parseInt(adjustments.get(11))));
        debitAmountsPnl.add(Integer.parseInt(ibl.getText().toString()));
        dd.setText("100"); //TODO
        debitAmountsPnl.add(Integer.parseInt(dd.getText().toString()));
        dc.setText("60"); //TODO
        creditAmountsPnl.add(Integer.parseInt(dc.getText().toString()));
        idl.setText("85"); //TODO
        creditAmountsPnl.add(Integer.parseInt(idl.getText().toString()));

        if (debitSideTotal > creditSideTotal)
        {
            grossprofittext1.setText("");
            grossprofitvalue1.setText("");
            grosslosstext1.setText("By Gross Loss");
            grosslossvalue1.setText(String.valueOf(debitSideTotal-creditSideTotal));
            maxAmount1.setText(String.valueOf(debitSideTotal));
            maxAmount2.setText(String.valueOf(debitSideTotal));

            grosslosstext2.setText("To Gross Loss");
            grosslossvalue2.setText(String.valueOf(debitSideTotal-creditSideTotal));
            grossprofittext2.setText("");
            grossprofitvalue2.setText("");
            debitAmountsPnl.add(debitSideTotal-creditSideTotal);
        }
        else
        {
            grossprofittext1.setText("Gross Profit");
            grossprofitvalue1.setText(String.valueOf(creditSideTotal-debitSideTotal));
            grosslosstext1.setText("");
            grosslossvalue1.setText("");
            maxAmount1.setText(String.valueOf(creditSideTotal));
            maxAmount2.setText(String.valueOf(creditSideTotal));

            grossprofittext2.setText("By Gross Profit");
            grossprofitvalue2.setText(String.valueOf(creditSideTotal-debitSideTotal));
            grosslosstext2.setText("");
            grosslossvalue2.setText("");
            creditAmountsPnl.add(creditSideTotal-debitSideTotal);
        }

        int debitSideTotalPnl=0;

        for(Integer i : debitAmountsPnl)
            debitSideTotalPnl+=i;

        int creditSideTotalPnl=0;

        for(Integer i : creditAmountsPnl)
            creditSideTotalPnl+=i;

        if (debitSideTotalPnl>creditSideTotalPnl)
        {
            netprofittext.setText("");
            netprofitvalue.setText("");
            netlosstext.setText("By Net Loss");
            netlossvalue.setText(String.valueOf(debitSideTotalPnl-creditSideTotalPnl));
            maxAmount3.setText(String.valueOf(debitSideTotalPnl));
            maxAmount4.setText(String.valueOf(debitSideTotalPnl));

        }
        else
        {
            netprofittext.setText("To Net Profit");
            netprofitvalue.setText(String.valueOf(creditSideTotalPnl-debitSideTotalPnl));
            netlossvalue.setText("");
            netlosstext.setText("");
            maxAmount3.setText(String.valueOf(creditSideTotalPnl));
            maxAmount4.setText(String.valueOf(creditSideTotalPnl));
        }

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

                pnlCall = apiInterface.getFinal(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                pnlCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        JsonObject body = response.body();
                        profit = String.valueOf(body.get("profit"));
                        loss = String.valueOf(body.get("loss"));
                        JsonArray ledger = body.getAsJsonArray("ledger");

                        for (int i=0;i<ledger.size();i++){

                            JsonObject fieldss = (JsonObject) ledger.get(i);
                            JsonObject balanceFields = (JsonObject) fieldss.get("balance");
                            balanceAmt = String.valueOf(balanceFields.get("amount"));
                            balanceType = String.valueOf(balanceFields.get("type"));

                            JsonObject accountFields = (JsonObject) fieldss.get("account");
                            accountName = String.valueOf(accountFields.get("name"));

                            if(accountName.toLowerCase().equals("\"purchase\"")){
                                totalPurchase.setText(balanceAmt);
                            }
                            if(accountName.toLowerCase().equals("\"purchase return\"")){
                                totalPurchaseReturn.setText(balanceAmt);
                            }
                            if(accountName.toLowerCase().equals("\"sale\"")){
                                totalSales.setText(balanceAmt);
                            }
                            if(accountName.toLowerCase().equals("\"sale return\"")){
                                totalSalesReturn.setText(balanceAmt);
                            }
                            if(accountName.toLowerCase().contains("rent")){
                                if(balanceType.contains("debit")){
                                    totalRent1.setText(balanceAmt);
                                    debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                                else{
                                    totalRent2.setText(balanceAmt);
                                    creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                            }
                            if(accountName.toLowerCase().contains("commission")){
                                if(balanceType.contains("debit")){
                                    totalCommission1.setText(balanceAmt);
                                    debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                                else{
                                    totalCommission2.setText(balanceAmt);
                                    creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                            }
                            if(accountName.toLowerCase().contains("discount")){
                                if(balanceType.contains("debit")){
                                    totalDiscount1.setText(balanceAmt);
                                    debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                                else{
                                    totalDiscount2.setText(balanceAmt);
                                    creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                }
                            }
                            if(accountName.toLowerCase().contains("salary")){
                                totalSalaries.setText(balanceAmt);
                                debitAmountsPnl.add(Integer.valueOf(balanceAmt.substring(1, balanceAmt.length()-1)));
                            }

                            Log.e("nana", balanceAmt + "   " + balanceType + " " + accountName);


                            // TODO: Check if Account name Rent is debit or credit. If doubt ask me. See xml layout you will understand.
                            // TODO: Check if Account name Commission is debit or credit. If doubt ask me. See xml layout you will understand.
                            // TODO: Check if Account name Discount is debit or credit.  If doubt ask me. See xml layout you will understand.
                            // TODO: totalSalaries from Salaries account
                            // TODO: For each of the four above add values to integer lists of credit or debit amounts.
                        }

                        differencePurchase.setText(String.valueOf(Integer.valueOf(totalPurchase.getText().toString())-Integer.valueOf(totalPurchaseReturn.getText().toString())));
                        differenceSales.setText(String.valueOf(Integer.valueOf(totalPurchaseReturn.getText().toString())-Integer.valueOf(totalSalesReturn.getText().toString())));

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
