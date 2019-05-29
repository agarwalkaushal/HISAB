package com.fullertonfinnovatica.Finance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.fullertonfinnovatica.Accounts.JournalRetrieve;
import com.fullertonfinnovatica.Accounts.Ledger;
import com.fullertonfinnovatica.Accounts.Trialbalance;
import com.fullertonfinnovatica.R;

public class FinanceMain extends AppCompatActivity {

    CardView cashBudget, flexibleBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Finance</font>"));

        cashBudget = findViewById(R.id.cash);
        flexibleBudget = findViewById(R.id.flexible);

        cashBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), CashBudget.class);
                startActivity(intent);

            }
        });

        flexibleBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), FlexibleBudget.class);
                startActivity(intent);

            }
        });

    }
}
