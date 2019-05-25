package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.fullertonfinnovatica.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Trialbalance extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialbalance);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Trial Balance</font>"));

        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

    }
}
