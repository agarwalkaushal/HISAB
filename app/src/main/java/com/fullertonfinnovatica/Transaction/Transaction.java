package com.fullertonfinnovatica.Transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.fullertonfinnovatica.SignUpAPI;
import com.fullertonfinnovatica.SignUpModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Transaction extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, Callback<InventoryModel> {

    private double totalAmount = 0;

    private String type;
    private String itemName;
    private String itemQuantity;
    private String itemRate;
    private String creditName;
    private String creditNumber;
    private String product;
    private String[] products;

    private AutoCompleteTextView name;
    private EditText rate;
    private EditText quantity;
    private EditText nameCredit;
    private EditText numberCredit;

    private TextView total;

    private LinearLayout purchaseLayout;
    private LinearLayout rentLayout;
    private LinearLayout creditCredentials;

    private static DataAdapter dataAdapter;

    private ListView listView;

    private Button doneButton;

    private RadioButton cashSelected;
    private RadioButton creditSelected;

    ArrayList<DataRow> dataRows = new ArrayList<>();

    TransactionAPIs apiInterface1, apiInterface2, apiInterface3, apiInterface4;
    JSONObject paramObject;
    String typeOfTrans, modeOfTrans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Transaction</font>"));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TransactionAPIs.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface1 = retrofit.create(TransactionAPIs.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(TransactionAPIs.BASE_URL2)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface2 = retrofit2.create(TransactionAPIs.class);

        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl(TransactionAPIs.BASE_URL3)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface3 = retrofit3.create(TransactionAPIs.class);

        Retrofit retrofit4 = new Retrofit.Builder()
                .baseUrl(TransactionAPIs.BASE_URL4)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface4 = retrofit4.create(TransactionAPIs.class);

        product = prefs.getString("products","Milk,");
        products = product.split(",");

        Log.e("Products",product);

        ArrayAdapter<String> products_adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, products);

        name = (AutoCompleteTextView) findViewById(R.id.name);
        name.setThreshold(1);
        name.setAdapter(products_adapter);

        creditCredentials = (LinearLayout) findViewById(R.id.credit_view);
        creditCredentials.setVisibility(View.GONE);
        rentLayout = (LinearLayout) findViewById(R.id.rent);
        rentLayout.setVisibility(View.GONE);
        purchaseLayout = (LinearLayout) findViewById(R.id.purchase);

        rate = (EditText) findViewById(R.id.rate);
        quantity = (EditText) findViewById(R.id.quantity);
        nameCredit = (EditText) findViewById(R.id.credit_name);
        numberCredit = (EditText) findViewById(R.id.credit_number);

        total = (TextView) findViewById(R.id.total);

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


        name.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            addItem();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String inputName = name.getText().toString();
                    int c=0;
                    for(String i : products)
                    {
                        if(i.compareTo(inputName)==0) {
                            c++;
                            break;
                        }
                    }
                    if(c==0) {
                        name.setError("Name error");
                        Toast.makeText(getBaseContext(), "Enter a product name that exists in inventory, or add that item in inventory and proceed", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        rate.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            addItem();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        quantity.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_ENTER:
                            addItem();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Check for current layout and see if all details are entered properly

                Date currentTime = Calendar.getInstance().getTime();
                Toast.makeText(getBaseContext(), "Time: "+currentTime.toString(), Toast.LENGTH_LONG).show();

                //TODO: Send transaction to server and update inventory
                try {
                    //fromname, toname, date, transmode, creditamount, debitamount
                    paramObject = new JSONObject();
                    paramObject.put("fromname", typeOfTrans);
                    paramObject.put("toname", modeOfTrans);
                    paramObject.put("date", currentTime.toString());
                    paramObject.put("transmode", modeOfTrans);
                    paramObject.put("creditamount", Integer.valueOf(rate.getText().toString())*Integer.valueOf(quantity.getText().toString()));
                    paramObject.put("debitamount", Integer.valueOf(rate.getText().toString())*Integer.valueOf(quantity.getText().toString()));
                    sendData(paramObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

    }

    @Override
    public void onResponse(Call<InventoryModel> call, Response<InventoryModel> response) {
    }

    @Override
    public void onFailure(Call<InventoryModel> call, Throwable t) {
    }


    private void addItem()
    {
        itemName = name.getText().toString();
        itemRate = rate.getText().toString();
        itemQuantity = quantity.getText().toString();

        if(itemName.length()!=0 && itemRate.length()!=0 && itemQuantity.length()!=0)
        {
            addItem(itemName,Double.parseDouble(itemRate),Double.parseDouble(itemQuantity));
            name.getText().clear();
            rate.getText().clear();
            quantity.getText().clear();
        }
        else
            Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
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
            purchaseLayout.setVisibility(View.VISIBLE);
            rentLayout.setVisibility(View.GONE);
            typeOfTrans = "Purchase";
        }
        else if(pos == 2)
        {
            purchaseLayout.setVisibility(View.GONE);
            rentLayout.setVisibility(View.VISIBLE);
            typeOfTrans = "Paid Rent";
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void addItem(String itemName, double itemRate, double itemQuantity) {
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
                    creditCredentials.setVisibility(View.GONE);
                    modeOfTrans = "Cash";
                }
                    break;
            case R.id.credit:
                if (checked){
                    creditSelected.setText("CREDIT");
                    cashSelected.setText("");
                    creditCredentials.setVisibility(View.VISIBLE);
                    modeOfTrans = "Credit";
                }
                    break;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();

        product = prefs.getString("products","Milk,");
        products = product.split(",");

        Log.e("Products",product);

        ArrayAdapter<String> products_adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, products);
        name.setAdapter(products_adapter);
    }

    void sendData(JSONObject j){


        Call<InventoryModel> userCall1 = apiInterface1.sendInventory(j.toString());
        Call<InventoryModel> userCall2 = apiInterface2.sendInventory(j.toString());
        Call<InventoryModel> userCall3 = apiInterface3.sendInventory(j.toString());
        Call<InventoryModel> userCall4 = apiInterface4.sendInventory(j.toString());

        userCall1.enqueue(this);
        userCall2.enqueue(this);
        userCall3.enqueue(this);
        userCall4.enqueue(this);
        Toast.makeText(getBaseContext(), "Sent data", Toast.LENGTH_LONG).show();

    }

}
