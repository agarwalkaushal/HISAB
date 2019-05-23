package com.fullertonfinnovatica.Transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Accounts.AccountsAPI;
import com.fullertonfinnovatica.Accounts.JournalEntryModel;
import com.fullertonfinnovatica.Accounts.LoginModel;
import com.fullertonfinnovatica.Inventory.InventoryAdd;
import com.fullertonfinnovatica.Inventory.InventoryCategories;
import com.fullertonfinnovatica.R;
import com.fullertonfinnovatica.SignUpAPI;
import com.fullertonfinnovatica.SignUpModel;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class
Transaction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private double totalAmount = 0;

    private String type;
    private String subType;
    private String itemName;
    private String itemQuantity;
    private String itemRate;
    private String creditName;
    private String creditNumber;
    private String product;
    private String typeOfTrans;
    private String modeOfTrans = "Cash";
    private String[] products;


    private AutoCompleteTextView name;
    private EditText rate;
    private EditText quantity;
    private EditText nameCredit;
    private EditText numberCredit;
    private EditText amount;
    private EditText subTypeName;

    private TextView total;
    private TextView subTypeText;

    private LinearLayout purchaseLayout;
    private LinearLayout amountLayout;
    private LinearLayout subTypeLayout;
    private LinearLayout commissionLayout;
    private LinearLayout creditCredentials;
    private LinearLayout subTypeNameLayout;

    private RelativeLayout progressParent;

    private static DataAdapter dataAdapter;

    private ListView listView;

    private Button doneButton;

    private RadioButton cashSelected;
    private RadioButton creditSelected;

    private boolean credit = true;
    private boolean amountLayoutStatus = false;
    private boolean creditLayoutStatus = false;
    private boolean nameLayoutStatus = false;

    private Spinner spinner;
    private Spinner subTypesSpinner;

    private RadioGroup radioCommission;
    private RadioButton commissionType;

    ArrayList<DataRow> dataRows = new ArrayList<>();

    Call<JournalEntryModel> entryCall;
    Call<JsonObject> ledgerPostCall;
    Call<JsonObject> pnlPostCall;
    Call<JsonObject> trialPostCall;
    Call<LoginModel> loginCall;
    TransactionAPIs apiInterface;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Transaction</font>"));

        progressParent = findViewById(R.id.progressParent);
        progressParent.setVisibility(View.GONE);
        final CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progress);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient cleint = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        retrofit = new Retrofit.Builder().baseUrl(AccountsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleint)
                .build();

        apiInterface = retrofit.create(TransactionAPIs.class);

        product = prefs.getString("products", "Milk,");
        products = product.split(",");

        Log.e("Products", product);

        ArrayAdapter<String> products_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, products);

        name = (AutoCompleteTextView) findViewById(R.id.name);
        name.setThreshold(1);
        name.setAdapter(products_adapter);

        creditCredentials = (LinearLayout) findViewById(R.id.credit_view);
        creditCredentials.setVisibility(View.GONE);
        amountLayout = (LinearLayout) findViewById(R.id.amountInput);
        amountLayout.setVisibility(View.GONE);
        subTypeLayout = (LinearLayout) findViewById(R.id.chooseSubType);
        subTypeLayout.setVisibility(View.GONE);
        commissionLayout = (LinearLayout) findViewById(R.id.chooseCommissionType);
        commissionLayout.setVisibility(View.GONE);
        subTypeNameLayout = (LinearLayout) findViewById(R.id.sub_type_name_layout);
        subTypeNameLayout.setVisibility(View.GONE);
        purchaseLayout = (LinearLayout) findViewById(R.id.purchase);

        rate = (EditText) findViewById(R.id.rate);
        quantity = (EditText) findViewById(R.id.quantity);
        nameCredit = (EditText) findViewById(R.id.credit_name);
        numberCredit = (EditText) findViewById(R.id.credit_number);
        amount = (EditText) findViewById(R.id.amount);
        subTypeName = (EditText) findViewById(R.id.sub_type_name);

        total = (TextView) findViewById(R.id.total);
        subTypeText = (TextView) findViewById(R.id.subTypeText);

        doneButton = (Button) findViewById(R.id.done);

        cashSelected = (RadioButton) findViewById(R.id.cash);
        creditSelected = (RadioButton) findViewById(R.id.credit);
        cashSelected.setText("Cash");

        spinner = (Spinner) findViewById(R.id.types_spinner);
        subTypesSpinner = (Spinner) findViewById(R.id.sub_types_spinner);

        listView = (ListView) findViewById(R.id.purchase_list);

        spinner.setOnItemSelectedListener(this);
        subTypesSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        dataAdapter = new DataAdapter(dataRows, this);
        listView.setAdapter(dataAdapter);


        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
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
                if (!hasFocus) {
                    String inputName = name.getText().toString();
                    int c = 0;
                    for (String i : products) {
                        if (i.compareTo(inputName) == 0) {
                            c++;
                            break;
                        }
                    }
                    if (c == 0) {
                        name.setError("Name error");
                        Toast.makeText(getBaseContext(), "Enter a product name that exists in inventory, or add that item in inventory and proceed", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        rate.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
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

        quantity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
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

                if (amountLayoutStatus) {
                    if (amount.getText().toString().matches("")) {
                        Toast.makeText(getBaseContext(), "Please enter amount and then proceed",Toast.LENGTH_SHORT).show();
                        return;
                    } else
                        totalAmount = Double.parseDouble(amount.getText().toString());
                } else if (totalAmount == 0) {
                    Toast.makeText(getBaseContext(), "Please enter all required fields and then proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (creditLayoutStatus == false) {
                    creditName = null;
                    creditNumber = null;
                } else {

                    if(nameCredit.getText().toString().matches(""))
                    {
                        nameCredit.setError("Required");
                        return;
                    }

                    if(numberCredit.getText().toString().matches(""))
                    {
                        nameCredit.setError("Required");
                        return;
                    }

                    creditName = nameCredit.getText().toString();
                    creditNumber = numberCredit.getText().toString();
                }

                if (nameLayoutStatus == true) {

                    if(subTypeName.getText().toString().matches(""))
                    {
                        subTypeName.setError("Required");
                        return;
                    }
                    creditName = subTypeName.getText().toString();
                }

                String pattern = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                final String dateee = simpleDateFormat.format(new Date());

                //Toast.makeText(getBaseContext(), "Date: " + dateee + "Type of Trans: " + typeOfTrans + "Sub type: " + subType + "Total amt: " + totalAmount + "Mode of transaction: " + modeOfTrans + "Credit Name: " + creditName + "Credit no: " + creditNumber, Toast.LENGTH_LONG).show();
                Log.e("Done click: ", "Date: " + dateee + "Type of Trans: " + typeOfTrans + "Sub type: " + subType + "Total amt: " + totalAmount + "Mode of transaction: " + modeOfTrans + "Credit Name: " + creditName + "Credit no: " + creditNumber);

                loginCall = apiInterface.login("demouserid", "demo");

                progressParent.setVisibility(View.VISIBLE);
                circularProgressBar.enableIndeterminateMode(true);
                doneButton.setVisibility(View.GONE);

                loginCall.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                        entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                typeOfTrans, modeOfTrans, dateee, String.valueOf((int) totalAmount),
                                String.valueOf((int) totalAmount), creditName+":"+subType);
                        ledgerPostCall = apiInterface.ledgerPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);
                        pnlPostCall = apiInterface.pnlPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);
                        trialPostCall = apiInterface.trialPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);

                        /*
                        // TODO: Make entry with type and sub-type of transaction
                        if (typeOfTrans.contains("purchase")) {
                            entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);
                            ledgerPostCall = apiInterface.ledgerPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);
                            pnlPostCall = apiInterface.pnlPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);
                            trialPostCall = apiInterface.trialPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    "Purchase", modeOfTrans, dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being purchased for " + modeOfTrans);

                        } else {
                            entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    modeOfTrans, "Sales", dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being sold for " + modeOfTrans);
                            ledgerPostCall = apiInterface.ledgerPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    modeOfTrans, "Sales", dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being sold for " + modeOfTrans);
                            pnlPostCall = apiInterface.pnlPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    modeOfTrans, "Sales", dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being sold for " + modeOfTrans);
                            trialPostCall = apiInterface.trialPost(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                                    modeOfTrans, "Sales", dateee, String.valueOf((int) totalAmount), String.valueOf((int) totalAmount), "Goods being sold for " + modeOfTrans);
                        }
                        */


                        entryCall.enqueue(new Callback<JournalEntryModel>() {
                            @Override
                            public void onResponse(Call<JournalEntryModel> call, Response<JournalEntryModel> response) {
                                Toast.makeText(getBaseContext(), "Entry successfully made..", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<JournalEntryModel> call, Throwable t) {
                                Toast.makeText(getBaseContext(), "An error occured: " + t.toString(), Toast.LENGTH_LONG).show();
                                doneButton.setVisibility(View.VISIBLE);
                                progressParent.setVisibility(View.GONE);
                            }
                        });

                        ledgerPostCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Log.e("apiCheck", "Ledger post success");
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.e("apiCheck", "Ledger post fail " + t.toString());
                            }
                        });

                        pnlPostCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Log.e("apiCheck", "P&L post success");
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.e("apiCheck", "P&L post fail " + t.toString());
                            }
                        });

                        trialPostCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Log.e("apiCheck", "Trial post success");
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.e("apiCheck", "Trial post fail " + t.toString());
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {

                    }
                });

                //finish();


            }
        });

    }


    private void addItem() {
        itemName = name.getText().toString();
        itemRate = rate.getText().toString();
        itemQuantity = quantity.getText().toString();

        if (itemName.length() != 0 && itemRate.length() != 0 && itemQuantity.length() != 0) {
            addItem(itemName, Double.parseDouble(itemRate), Double.parseDouble(itemQuantity));
            name.getText().clear();
            rate.getText().clear();
            quantity.getText().clear();
        } else
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

        if (parent.getId() == R.id.types_spinner) {
            type = parent.getItemAtPosition(pos).toString();

            if (pos == 0) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;

                typeOfTrans = "Purchase";

            } else if (pos == 1) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;

                typeOfTrans = "Sale";

            } else if (pos == 2) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;

                typeOfTrans = "Purchase Return";

            } else if (pos == 3) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;

                typeOfTrans = "Sale Return";

            } else if (pos == 4) {

                Log.e("Error", "Payment Done");

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Payment Done";

                subTypeText.setText("Choose payment type");
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.pay_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subTypesSpinner.setAdapter(adapter);

            } else if (pos == 5) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Payment Received";

                subTypeText.setText("Choose payment type");

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.pay_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subTypesSpinner.setAdapter(adapter);

            } else if (pos == 6) {

                subTypeNameLayout.setVisibility(View.VISIBLE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);

                nameLayoutStatus = true;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Commission";
                subType = "Received";

            } else {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
                commissionLayout.setVisibility(View.GONE);

                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Drawings";

                subTypeText.setText("Choose drawings type");
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.drawing_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subTypesSpinner.setAdapter(adapter);

            }
        }

        if (parent.getId() == R.id.sub_types_spinner) {

            subType = parent.getItemAtPosition(pos).toString();

            if ((typeOfTrans == "Payment Done" || typeOfTrans == "Payment Received") && pos == 2) {

                subTypeNameLayout.setVisibility(View.VISIBLE);
                nameLayoutStatus = true;

            } else if (typeOfTrans == "Drawings" && pos == 2) {

                purchaseLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.GONE);
                amountLayoutStatus = false;
            } else {
                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void addItem(String itemName, double itemRate, double itemQuantity) {
        totalAmount += itemQuantity * itemRate;
        dataRows.add(new DataRow(itemName, itemRate, itemQuantity));
        total.setText("Rs. " + String.valueOf(totalAmount));
        // TODO: Update inventory here
        dataAdapter.notifyDataSetChanged();
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.cash:
                if (checked) {
                    cashSelected.setText("CASH");
                    creditSelected.setText("");
                    creditCredentials.setVisibility(View.GONE);
                    modeOfTrans = "Cash";
                    creditLayoutStatus = false;
                }
                break;
            case R.id.credit:
                if (checked && credit) {
                    creditSelected.setText("CREDIT");
                    cashSelected.setText("");
                    creditCredentials.setVisibility(View.VISIBLE);
                    modeOfTrans = "Credit";
                    creditLayoutStatus = true;
                } else {
                    creditSelected.setText("CHEQUE");
                    cashSelected.setText("");
                    modeOfTrans = "Cheque";
                    creditLayoutStatus = false;
                }
                break;
            case R.id.given:
                subType = "Given";
                break;
            case R.id.received:
                subType = "Received";
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();

        product = prefs.getString("products", "Milk,");
        products = product.split(",");

        Log.e("Products", product);

        ArrayAdapter<String> products_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, products);
        name.setAdapter(products_adapter);
    }

    public static String getAuthToken(String userName, String password) {
        byte[] data = new byte[0];
        try {
            data = (userName + ":" + password).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("chekin2", "Basic " + Base64.encodeToString(data, Base64.NO_WRAP));
        return "Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }

}
