package com.fullertonfinnovatica.Accounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;

import com.fullertonfinnovatica.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Trialbalance extends AppCompatActivity {

    RelativeLayout progressParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trialbalance);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Trial Balance</font>"));

        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);
        circularProgressBar.enableIndeterminateMode(true);

        progressParent = findViewById(R.id.progressParent);

    }
}
