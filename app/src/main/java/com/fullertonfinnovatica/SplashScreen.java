package com.fullertonfinnovatica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullertonfinnovatica.Transaction.Transaction;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

public class SplashScreen extends AppCompatActivity {

    ViewGroup viewGroup;
    TextView hisab, hisabff;
    ImageView imageView;
    boolean visible = true,expanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_spash_screen);


        viewGroup = findViewById(R.id.transitionContainer);
        hisab = findViewById(R.id.hisabText);
        hisabff = findViewById(R.id.hisabff);
        imageView = findViewById(R.id.imgSplash);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Futura.ttf");
        hisab.setTypeface(font);

        font = Typeface.createFromAsset(getAssets(), "fonts/Adamcg.otf");
        hisabff.setTypeface(font);

        final TransitionSet set = new TransitionSet()
                .addTransition(new Scale(0.7f))
                .addTransition(new Fade())
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                        new FastOutLinearInInterpolator());

        final TransitionSet set2 = new TransitionSet()
                .addTransition(new Slide(Gravity.TOP))
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                        new FastOutLinearInInterpolator());

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {

                TransitionManager.beginDelayedTransition(viewGroup, set2);
                hisabff.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

            }
        },500);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {

                TransitionManager.beginDelayedTransition(viewGroup, set);
//                hisab.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                imageView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

            }
        },1000);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                if(prefs.getBoolean("login",false)){
                    Intent i = new Intent(SplashScreen.this, Dashboard.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(SplashScreen.this, Initial.class);
                    startActivity(i);
                    finish();
                }

            }
        },1500);

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
