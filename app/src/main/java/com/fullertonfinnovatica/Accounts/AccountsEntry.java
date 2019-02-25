package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.fullertonfinnovatica.R;

public class AccountsEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_entry);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Accounts Entry</font>"));

    }
}
