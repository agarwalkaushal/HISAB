package com.fullertonfinnovatica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Transaction extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    String type;

    private LinearLayout purchaseLayout;
    private LinearLayout purchaseButtons;
    private LinearLayout rentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_transaction);
        rentLayout = (LinearLayout) findViewById(R.id.rent);
        rentLayout.setVisibility(View.INVISIBLE);

        getSupportActionBar().setTitle(getString(R.string.transaction));

        //TODO: Edit action bar color & text or remove action bar whichever design suits better

        Spinner spinner = (Spinner) findViewById(R.id.types_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        purchaseLayout = (LinearLayout) findViewById(R.id.purchase);
        purchaseButtons = (LinearLayout) findViewById(R.id.purchase_buttons);



    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        type = parent.getItemAtPosition(pos).toString();

        Log.d("Pos", String.valueOf(pos));
        if(pos == 0)
        {

            purchaseLayout.setVisibility(View.VISIBLE);
            purchaseButtons.setVisibility(View.VISIBLE);
            rentLayout.setVisibility(View.INVISIBLE);
        }
        else if(pos == 2)
        {
            purchaseLayout.setVisibility(View.INVISIBLE);
            purchaseButtons.setVisibility(View.INVISIBLE);
            rentLayout.setVisibility(View.VISIBLE);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
