package com.fullertonfinnovatica.Transaction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Inventory.InventoryAdd;
import com.fullertonfinnovatica.Inventory.InventoryCategories;
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
    private LinearLayout rentLayout;

    private static DataAdapter dataAdapter;

    private ListView listView;

    private Button addButton;
    private Button doneButton;

    private RadioButton cashSelected;
    private RadioButton creditSelected;

    ArrayList<DataRow> dataRows = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Transaction</font>"));
        rentLayout = (LinearLayout) findViewById(R.id.rent);
        rentLayout.setVisibility(View.GONE);
        purchaseLayout = (LinearLayout) findViewById(R.id.purchase);

        name = (EditText) findViewById(R.id.name);
        rate = (EditText) findViewById(R.id.rate);
        quantity = (EditText) findViewById(R.id.quantity);

        total = (TextView) findViewById(R.id.total);

        addButton = (Button) findViewById(R.id.add);
        doneButton = (Button) findViewById(R.id.done);

        cashSelected = (RadioButton) findViewById(R.id.cash);
        creditSelected = (RadioButton) findViewById(R.id.credit);
        cashSelected.setText("CASH");

        Spinner spinner = (Spinner) findViewById(R.id.types_spinner);

        listView=(ListView)findViewById(R.id.purchase_list);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_product) {
            Intent intent = new Intent(Transaction.this, InventoryAdd.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        type = parent.getItemAtPosition(pos).toString();

        if(pos == 0)
        {
            addButton.setVisibility(View.VISIBLE);
            purchaseLayout.setVisibility(View.VISIBLE);
            rentLayout.setVisibility(View.GONE);
        }
        else if(pos == 2)
        {
            purchaseLayout.setVisibility(View.GONE);
            addButton.setVisibility(View.GONE);
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

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.cash:
                if (checked){
                    cashSelected.setText("CASH");
                    creditSelected.setText("");
                    //TODO: set layout invisible
                }
                    break;
            case R.id.credit:
                if (checked){
                    creditSelected.setText("CREDIT");
                    cashSelected.setText("");
                    //TODO: set layout visible
                }
                    break;
        }
    }
}
