package com.fullertonfinnovatica.Finance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fullertonfinnovatica.Accounts.AccountsMain;
import com.fullertonfinnovatica.R;

public class Ratios extends AppCompatActivity {

    private TextView net, gross, netValue, grossValue, sales1, sales2, answer1, answer2;
    private int sales_value, net_value, gross_value, answer1Value, answer2Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratios);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Financial Margin</font>"));

        net = findViewById(R.id.net);
        gross = findViewById(R.id.gross);
        netValue = findViewById(R.id.net_value);
        grossValue = findViewById(R.id.gross_value);
        sales1 = findViewById(R.id.sales1);
        sales2 = findViewById(R.id.sales2);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
    }

    private void setValues()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sales_value = prefs.getInt("Sales",100);
        gross_value = prefs.getInt("Gross",0);
        net_value = prefs.getInt("Net",0);

        netValue.setText(String.valueOf(net_value));
        grossValue.setText(String.valueOf(gross_value));
        sales1.setText(String.valueOf(sales_value));
        sales2.setText(String.valueOf(sales_value));

        answer1Value = (net_value*100)/sales_value;
        answer2Value = (gross_value*100)/sales_value;
        answer1.setText(String.valueOf(answer1Value));
        answer2.setText(String.valueOf(answer2Value));

        if(net_value>0) {
            net.setText("Net\nProfit\nMargin");
        }
        else {
            net.setText("Net\nLoss\nMargin");
        }

        if(gross_value>0) {
            gross.setText("Gross\nProfit\nMargin");
        }
        else {
            gross.setText("Gross\nLoss\nMargin");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setValues();
    }
}
