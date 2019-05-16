package com.fullertonfinnovatica.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.view.View;

import com.fullertonfinnovatica.R;

public class AccountsMain extends AppCompatActivity {

    CardView journalCard, ledgerCard, pnlCard, balanceSheetCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Accounts</font>"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        journalCard = findViewById(R.id.journal);
        ledgerCard = findViewById(R.id.ledger);
        pnlCard = findViewById(R.id.pnl);
        balanceSheetCard = findViewById(R.id.balanceSheet);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(getBaseContext(), JournalRetrieve.class);
            startActivity(intent);

            }
        });

        journalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), JournalRetrieve.class);
                startActivity(intent);

            }
        });

        ledgerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Ledger.class);
                startActivity(intent);

            }
        });

        pnlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Pnl.class);
                startActivity(intent);

            }
        });

        balanceSheetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), BalanceSheet.class);
                startActivity(intent);

            }
        });

    }

}
