package com.fullertonfinnovatica;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Initial extends AppCompatActivity {

    private Button createNewBussiness;
    private Button loginBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_initial);

//        Intent i = new Intent(this,Dashboard.class);
//        startActivity(i);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Adamcg.otf");
        createNewBussiness = (Button) findViewById(R.id.create_new);
        createNewBussiness.setTypeface(font);
        loginBusiness = (Button) findViewById(R.id.login);
        loginBusiness.setTypeface(font);

        createNewBussiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Initial.this, Create.class);
                startActivity(i);
                finish();
            }
        });

        loginBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Initial.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private void hideStatusBar()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN );
    }

}
