package com.fullertonfinnovatica.Transaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.fullertonfinnovatica.R;

public class TransactionView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+"Past Transactions"+"</font>"));
        // TODO: View all transactions
    }
}
