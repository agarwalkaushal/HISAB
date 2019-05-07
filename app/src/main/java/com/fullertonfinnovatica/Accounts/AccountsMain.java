package com.fullertonfinnovatica.Accounts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.fullertonfinnovatica.R;

public class AccountsMain extends AppCompatActivity {

    CardView journalCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Accounts</font>"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        journalCard = findViewById(R.id.journal);

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
    }

}
