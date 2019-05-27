package com.fullertonfinnovatica.Accounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;
import com.google.gson.JsonArray;
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

public class Pnl extends AppCompatActivity {

    Retrofit retrofit;
    AccountsAPI apiInterface;
    Call<LoginModel> loginCall;
    Call<JsonObject> pnlCall;

    String profit, loss, balanceAmt, balanceType, accountName, net, cash = "0", bank = "0", drawing="0";

    TextView os, totalSales, totalPurchase, totalSalesReturn, totalPurchaseReturn, differenceSales, differencePurchase,
            cs, grossprofittext1, grosslosstext1, grossprofitvalue1, grosslossvalue1, maxAmount1, maxAmount2;

    TextView grosslosstext2, grossprofittext2, grosslossvalue2, grossprofitvalue2,
            rentText1, totalRent1, rentText2, totalRent2,
            commissionText1, totalCommission1, commissionText2, totalCommission2,
            discountText1, totalDiscount1, discountText2, totalDiscount2,
            totalSalaries, dc, dm, ii, bd, idl, dd, ic, ibl,
            netlossvalue, netlosstext, netprofitvalue, netprofittext, maxAmount3, maxAmount4;

    List<Integer> debitAmountsPnl;
    List<Integer> creditAmountsPnl;

    CardView balanceSheetCard;

    ArrayList<String> adjustments;

    RelativeLayout progressParent;

    Integer sumSundryCreditors = 0, sumSundryDebtors = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnl);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Trading / Profit & Loss</font>"));

        progressParent = findViewById(R.id.progressParent);
        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

        balanceSheetCard = findViewById(R.id.balanceSheet);
        balanceSheetCard.setVisibility(View.GONE);
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

        adjustments = intent.getStringArrayListExtra("Adjustments");

        //updateValues();

        balanceSheetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), BalanceSheet.class);
                intent.putStringArrayListExtra("Adjustments", adjustments);
                if (net.matches("Loss")) {
                    intent.putExtra("Net ", net);
                    intent.putExtra("Net Loss", netlossvalue.getText().toString());
                } else {
                    intent.putExtra("Net ", net);
                    intent.putExtra("Net Profit", netprofitvalue.getText().toString());
                }
                intent.putExtra("Sundry Creditors",String.valueOf(sumSundryCreditors));
                intent.putExtra("Sundry Debtors",String.valueOf(sumSundryDebtors));
                intent.putExtra("Drawing",drawing);
                intent.putExtra("Cash",cash);
                intent.putExtra("Bank",bank);
                startActivity(intent);

            }
        });

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

                        progressParent.setVisibility(View.GONE);
                        balanceSheetCard.setVisibility(View.VISIBLE);

                        if (response.body() != null) {

                            JsonObject body = response.body();
                            profit = String.valueOf(body.get("profit"));
                            loss = String.valueOf(body.get("loss"));
                            JsonArray ledger = body.getAsJsonArray("ledger");

                            for (int i = 0; i < ledger.size(); i++) {

                                JsonObject fieldss = (JsonObject) ledger.get(i);
                                JsonObject balanceFields = (JsonObject) fieldss.get("balance");
                                balanceAmt = String.valueOf(balanceFields.get("amount"));
                                balanceType = String.valueOf(balanceFields.get("type"));

                                JsonObject accountFields = (JsonObject) fieldss.get("account");
                                accountName = String.valueOf(accountFields.get("name"));


                                if (accountName.toLowerCase().equals("\"purchase\"")) {
                                    totalPurchase.setText(balanceAmt);
                                }
                                else if (accountName.toLowerCase().equals("\"purchase return\"")) {
                                    totalPurchaseReturn.setText(balanceAmt);
                                }
                                else if (accountName.toLowerCase().equals("\"sales\"")) {
                                    totalSales.setText(balanceAmt);
                                }
                                else if (accountName.toLowerCase().equals("\"sales return\"")) {
                                    totalSalesReturn.setText(balanceAmt);
                                }
                                else if (accountName.toLowerCase().contains("rent")) {
                                    if (balanceType.contains("debit")) {
                                        rentText1.setText("To Rent");
                                        totalRent1.setText(balanceAmt);
                                        rentText2.setText("");
                                        totalRent2.setText("");
                                        debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    } else {
                                        rentText2.setText("By Rent");
                                        totalRent2.setText(balanceAmt);
                                        rentText1.setText("");
                                        totalRent1.setText("");
                                        creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    }
                                }
                                if (accountName.toLowerCase().contains("commission")) {
                                    if (balanceType.contains("debit")) {
                                        commissionText1.setText("To Commission");
                                        totalCommission1.setText(balanceAmt);
                                        commissionText2.setText("");
                                        totalCommission2.setText("");
                                        debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    } else {
                                        commissionText1.setText("");
                                        totalCommission1.setText("");
                                        commissionText2.setText("By Commission");
                                        totalCommission2.setText(balanceAmt);
                                        creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    }
                                }
                                else if (accountName.toLowerCase().contains("discount")) {
                                    if (balanceType.contains("debit")) {
                                        discountText1.setText("To Discount");
                                        totalDiscount1.setText(balanceAmt);
                                        discountText2.setText("");
                                        totalDiscount2.setText("");
                                        debitAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    } else {
                                        discountText1.setText("");
                                        totalDiscount1.setText("");
                                        discountText2.setText("By Discount");
                                        totalDiscount2.setText(balanceAmt);
                                        creditAmountsPnl.add(Integer.valueOf(balanceAmt));
                                    }
                                }
                                else if (accountName.toLowerCase().contains("salary")) {
                                    totalSalaries.setText(balanceAmt);
                                    debitAmountsPnl.add(Integer.valueOf(balanceAmt.substring(1, balanceAmt.length() - 1)));
                                }
                                else if (accountName.toLowerCase().contains("cash")) {
                                    cash = balanceAmt;
                                }
                                else if (accountName.toLowerCase().contains("bank")) {
                                    bank = balanceAmt;
                                }
                                else if (accountName.toLowerCase().contains("drawing")) {
                                    drawing = balanceAmt;
                                }
                                else
                                {
                                    if (balanceType.contains("debit"))
                                        sumSundryDebtors+=Integer.parseInt(balanceAmt);
                                    else
                                        sumSundryCreditors+=Integer.parseInt(balanceAmt);
                                }
                            }

                            updateValues();

                        } else {
                            Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                            finish();

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
                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void updateValues()
    {

        os.setText(adjustments.get(0));
        cs.setText(adjustments.get(1));
        dm.setText(String.valueOf(Integer.parseInt(adjustments.get(5)) * Integer.parseInt(adjustments.get(7)) / 100));
        debitAmountsPnl.add(Integer.parseInt(dm.getText().toString()));
        bd.setText(String.valueOf(Integer.parseInt(adjustments.get(6))));
        debitAmountsPnl.add(Integer.parseInt(bd.getText().toString()));
        ii.setText(String.valueOf(Integer.parseInt(adjustments.get(4)) * Integer.parseInt(adjustments.get(13)) / 100));
        creditAmountsPnl.add(Integer.parseInt(ii.getText().toString()));
        ic.setText(String.valueOf(Integer.parseInt(adjustments.get(2)) * Integer.parseInt(adjustments.get(10))));
        debitAmountsPnl.add(Integer.parseInt(ic.getText().toString()));
        ibl.setText(String.valueOf(Integer.parseInt(adjustments.get(3)) * Integer.parseInt(adjustments.get(11)) / 100));
        debitAmountsPnl.add(Integer.parseInt(ibl.getText().toString()));
        dd.setText(String.valueOf(sumSundryDebtors* Integer.parseInt(adjustments.get(8))/100));
        debitAmountsPnl.add(Integer.parseInt(dd.getText().toString()));
        dc.setText(String.valueOf(sumSundryCreditors* Integer.parseInt(adjustments.get(9))/100));
        creditAmountsPnl.add(Integer.parseInt(dc.getText().toString()));

        Integer debitSideTotal =
                Integer.valueOf(os.getText().toString()) +
                        Integer.valueOf(totalPurchase.getText().toString()) -
                        Integer.valueOf(totalPurchaseReturn.getText().toString());

        Integer creditSideTotal =
                Integer.valueOf(cs.getText().toString()) +
                        Integer.valueOf(totalSales.getText().toString()) -
                        Integer.valueOf(totalSalesReturn.getText().toString());

        if (debitSideTotal > creditSideTotal) {
            grossprofittext1.setText("");
            grossprofitvalue1.setText("");
            grosslosstext1.setText("By Gross Loss");
            grosslossvalue1.setText(String.valueOf(debitSideTotal - creditSideTotal));
            maxAmount1.setText("Rs. "+String.valueOf(debitSideTotal));
            maxAmount2.setText("Rs. "+String.valueOf(debitSideTotal));

            grosslosstext2.setText("To Gross Loss");
            grosslossvalue2.setText(String.valueOf(debitSideTotal - creditSideTotal));
            grossprofittext2.setText("");
            grossprofitvalue2.setText("");
            debitAmountsPnl.add(debitSideTotal - creditSideTotal);
        } else {
            grossprofittext1.setText("Gross Profit");
            grossprofitvalue1.setText(String.valueOf(creditSideTotal - debitSideTotal));
            grosslosstext1.setText("");
            grosslossvalue1.setText("");
            maxAmount1.setText("Rs. "+String.valueOf(creditSideTotal));
            maxAmount2.setText("Rs. "+String.valueOf(creditSideTotal));

            grossprofittext2.setText("By Gross Profit");
            grossprofitvalue2.setText(String.valueOf(creditSideTotal - debitSideTotal));
            grosslosstext2.setText("");
            grosslossvalue2.setText("");
            creditAmountsPnl.add(creditSideTotal - debitSideTotal);
        }

        idl.setText(String.valueOf(Integer.parseInt(drawing)* Integer.parseInt(adjustments.get(13))/100));
        creditAmountsPnl.add(Integer.parseInt(idl.getText().toString()));
        differencePurchase.setText(String.valueOf(Integer.valueOf(totalPurchase.getText().toString()) - Integer.valueOf(totalPurchaseReturn.getText().toString())));
        differenceSales.setText(String.valueOf(Integer.valueOf(totalPurchaseReturn.getText().toString()) - Integer.valueOf(totalSalesReturn.getText().toString())));

        int debitSideTotalPnl = 0, creditSideTotalPnl = 0;

        for (Integer i : debitAmountsPnl)
            debitSideTotalPnl += i;

        for (Integer i : creditAmountsPnl)
            creditSideTotalPnl += i;

        if (debitSideTotalPnl > creditSideTotalPnl) {
            netprofittext.setText("");
            netprofitvalue.setText("");
            netlosstext.setText("By Net Loss");
            netlossvalue.setText(String.valueOf(debitSideTotalPnl - creditSideTotalPnl));
            maxAmount3.setText("Rs. "+String.valueOf(debitSideTotalPnl));
            maxAmount4.setText("Rs. "+String.valueOf(debitSideTotalPnl));
            net = "Loss";
        } else {
            netprofittext.setText("To Net Profit");
            netprofitvalue.setText(String.valueOf(creditSideTotalPnl - debitSideTotalPnl));
            netlossvalue.setText("");
            netlosstext.setText("");
            maxAmount3.setText("Rs. "+String.valueOf(creditSideTotalPnl));
            maxAmount4.setText("Rs. "+String.valueOf(creditSideTotalPnl));
            net = "Profit";
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


}
