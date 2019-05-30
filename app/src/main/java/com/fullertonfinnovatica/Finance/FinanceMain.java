package com.fullertonfinnovatica.Finance;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.fullertonfinnovatica.Accounts.AccountsMain;
import com.fullertonfinnovatica.Accounts.JournalRetrieve;
import com.fullertonfinnovatica.Accounts.Ledger;
import com.fullertonfinnovatica.Accounts.Trialbalance;
import com.fullertonfinnovatica.R;

public class FinanceMain extends AppCompatActivity {

    CardView cashBudget, flexibleBudget, ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Finance</font>"));

        cashBudget = findViewById(R.id.cash);
        flexibleBudget = findViewById(R.id.flexible);
        ratio = findViewById(R.id.ratios);

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

        ratio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpAlert();

            }
        });

    }

    private void setUpAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Generate Profit & Loss first!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent1 = new Intent(getApplicationContext(), Ratios.class);
                startActivity(intent1);
                Intent intent = new Intent(getApplicationContext(), AccountsMain.class);
                intent.putExtra("Ratio", true);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog b = builder.show();

        Button nbutton = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = b.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.BLACK);
    }
}
