package com.fullertonfinnovatica.Transaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    private int totalAmount = 0;

    private String type;
    private String itemName;
    private String itemQuantity;
    private String itemRate;

    private EditText name;
    private EditText rate;
    private EditText quantity;

    private TextView total;

    private LinearLayout purchaseLayout;
    private LinearLayout purchaseButtons;
    private LinearLayout rentLayout;

    private static DataAdapter dataAdapter;

    private ListView listView;

    private Button addButton;

    ArrayList<DataRow> dataRows = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction);

        rentLayout = (LinearLayout) findViewById(R.id.rent);
        rentLayout.setVisibility(View.INVISIBLE);
        purchaseLayout = (LinearLayout) findViewById(R.id.purchase);
        purchaseButtons = (LinearLayout) findViewById(R.id.purchase_buttons);

        name = (EditText) findViewById(R.id.name);
        rate = (EditText) findViewById(R.id.rate);
        quantity = (EditText) findViewById(R.id.quantity);

        total = (TextView) findViewById(R.id.total);

        addButton = (Button) findViewById(R.id.add);

        Spinner spinner = (Spinner) findViewById(R.id.types_spinner);

        listView=(ListView)findViewById(R.id.purchase_list);

        getSupportActionBar().setTitle(getString(R.string.transaction));

        //TODO: Edit action bar color & text or remove action bar whichever design suits better

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dataAdapter = new DataAdapter(dataRows,this);
        listView.setAdapter(dataAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemName = name.getText().toString();
                itemRate = rate.getText().toString();
                itemQuantity = quantity.getText().toString();

                if(itemName.length()!=0 && itemRate.length()!=0 && itemQuantity.length()!=0)
                {
                    addItem(itemName,Integer.parseInt(itemRate),Integer.parseInt(itemQuantity));
                    name.getText().clear();
                    rate.getText().clear();
                    quantity.getText().clear();
                }
                else
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void addItem(String itemName, int itemRate, int itemQuantity) {
        totalAmount+= itemQuantity*itemRate;
        dataRows.add(new DataRow(itemName,itemRate,itemQuantity));
        total.setText("Rs. "+String.valueOf(totalAmount));
        dataAdapter.notifyDataSetChanged();
    }
}
