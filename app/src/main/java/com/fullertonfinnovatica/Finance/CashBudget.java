package com.fullertonfinnovatica.Finance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.fullertonfinnovatica.R;

public class CashBudget extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_budget);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cash Budget</font>"));
    }
}
