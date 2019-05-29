package com.fullertonfinnovatica.Transaction;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.fullertonfinnovatica.Inventory.InventoryAPI;
import com.fullertonfinnovatica.Inventory.InventoryAdd;
import com.fullertonfinnovatica.Inventory.InventoryCategories;
import com.fullertonfinnovatica.Inventory.InventoryModel;
import com.fullertonfinnovatica.R;
import com.fullertonfinnovatica.SignUpAPI;
import com.fullertonfinnovatica.SignUpModel;
import com.google.gson.JsonArray;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Transaction extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private double totalAmount = 0;

    String qtyString;
    private String type;
    private String subType;
    private String itemName;
    private String itemQuantity;
    private String itemRate;
    private String creditName;
    private String creditNumber;
    private String product = "milk,sugar,eggs";
    private String typeOfTrans;
    private String modeOfTrans = "Cash";
    private String[] products, qty;


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
    private LinearLayout transaction_mode;
    private LinearLayout suggestionsUI;

    private RelativeLayout progressParent;

    private static DataAdapter dataAdapter;

    private ListView listView;

    private Button suggestion1, suggestion2, suggestion3, suggestion4, suggestion5;

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
    ArrayAdapter<String> products_adapter;

    Call<JournalEntryModel> entryCall;
    Call<JsonObject> ledgerPostCall;
    Call<JsonObject> pnlPostCall;
    Call<JsonObject> trialPostCall;
    Call<JsonObject> inventoryCall;
    Call<LoginModel> loginCall;
    TransactionAPIs apiInterface;
    InventoryAPI apiInterface_inventory;
    Retrofit retrofit, retrofit_inventory;
    InventoryModel inventoryModel;
    List<InventoryModel> list;

    CircularProgressBar circularProgressBar;

    List<String> words;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech t1;

    private boolean isNameFromInventory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Transaction</font>"));

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        words = new ArrayList<String>();

        progressParent = findViewById(R.id.progressParent);
        progressParent.setVisibility(View.GONE);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progress);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();
        list = new ArrayList<>();

        name = (AutoCompleteTextView) findViewById(R.id.name);
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
        transaction_mode = (LinearLayout) findViewById(R.id.transaction_mode);
        suggestionsUI = findViewById(R.id.suggestionsUI);

        rate = (EditText) findViewById(R.id.rate);
        quantity = (EditText) findViewById(R.id.quantity);
        nameCredit = (EditText) findViewById(R.id.credit_name);
        numberCredit = (EditText) findViewById(R.id.credit_number);
        amount = (EditText) findViewById(R.id.amount);
        subTypeName = (EditText) findViewById(R.id.sub_type_name);

        total = (TextView) findViewById(R.id.total);
        subTypeText = (TextView) findViewById(R.id.subTypeText);

        cashSelected = (RadioButton) findViewById(R.id.cash);
        creditSelected = (RadioButton) findViewById(R.id.credit);
        cashSelected.setText("Cash");

        spinner = (Spinner) findViewById(R.id.types_spinner);
        subTypesSpinner = (Spinner) findViewById(R.id.sub_types_spinner);

        listView = (ListView) findViewById(R.id.purchase_list);

        suggestion1 = findViewById(R.id.suggestion1);
        suggestion2 = findViewById(R.id.suggestion2);
        suggestion3 = findViewById(R.id.suggestion3);
        suggestion4 = findViewById(R.id.suggestion4);
        suggestion5 = findViewById(R.id.suggestion5);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        spinner.setOnItemSelectedListener(this);
        subTypesSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        dataAdapter = new DataAdapter(dataRows, this);
        listView.setAdapter(dataAdapter);

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

        Button doneButton = findViewById(R.id.doneButtonTrans);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneClicked();
            }
        });

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient cleint = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(AccountsAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleint)
                .build();

        retrofit_inventory = new Retrofit.Builder().baseUrl(InventoryAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleint)
                .build();

        products = product.split(",");
        products_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.select_dialog_item, products);

        apiInterface = retrofit.create(TransactionAPIs.class);
        apiInterface_inventory = retrofit_inventory.create(InventoryAPI.class);

        loginCall = apiInterface_inventory.login("demoadminid", "demo");
        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                inventoryCall = apiInterface_inventory.getInventoryy(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"));

                inventoryCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.body() != null) {
                            JsonObject bodyyy = response.body();
                            JsonArray bodyy = bodyyy.getAsJsonArray("inventory");
                            product="";
                            for (int i = 0; i < bodyy.size(); i++) {

                                inventoryModel = new InventoryModel();
                                JsonObject jsonObject = (JsonObject) bodyy.get(i);

                                inventoryModel.setInventory_category(jsonObject.get("category").toString());
                                inventoryModel.setInventory_cost(jsonObject.get("cost").toString());
                                inventoryModel.setInventory_name(jsonObject.get("name").toString());
                                inventoryModel.setInventory_qty(jsonObject.get("quantity").toString());
                                list.add(inventoryModel);
                                product += inventoryModel.getInventory_name().substring(1,inventoryModel.getInventory_name().length()-1) + ",";
//                                qtyString+=inventoryModel.getInventory_qty()+",";

                            }

                            Toast.makeText(getBaseContext(), product, Toast.LENGTH_LONG).show();
                            products = product.split(",");
                            products_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.select_dialog_item, products);
                            name.setAdapter(products_adapter);

                            name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (!hasFocus) {
                                        String inputName = name.getText().toString();
                                        int c = 0;
                                        if (products != null) {
                                            for (String i : products) {
                                                if (i.compareTo(inputName) == 0) {
                                                    isNameFromInventory = true;
                                                    c++;
                                                    break;
                                                }
                                            }
                                        }
                                        if (c == 0) {
                                            name.setError("Name error! Item not in inventory");
                                            isNameFromInventory = false;
                                            //Toast.makeText(getBaseContext(), "Enter a product name that exists in inventory, or add that item in inventory and proceed", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });

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


                        } else {
                            Toast.makeText(getBaseContext(), "Servers are down "+response.toString(), Toast.LENGTH_LONG).show();
                            //finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                        //finish();

                    }
                });
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Servers are down", Toast.LENGTH_LONG).show();
                //finish();

            }
        });


//        name.setThreshold(1);
//        name.setAdapter(products_adapter);
//                        qty = qtyString.split(",");



        suggestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(suggestion1.getText().toString().toUpperCase());
            }
        });

        suggestion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(suggestion2.getText().toString().toUpperCase());
            }
        });

        suggestion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(suggestion3.getText().toString().toUpperCase());
            }
        });

        suggestion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(suggestion4.getText().toString().toUpperCase());
            }
        });

        suggestion5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(suggestion5.getText().toString().toUpperCase());
            }
        });


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void doneClicked() {

        if (amountLayoutStatus) {
            if (amount.getText().toString().matches("")) {
                Toast.makeText(getBaseContext(), "Please enter amount and then proceed", Toast.LENGTH_SHORT).show();
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

            if (nameCredit.getText().toString().matches("")) {
                nameCredit.setError("Required");
                return;
            }

            if (numberCredit.getText().toString().matches("")) {
                nameCredit.setError("Required");
                return;
            }

            creditName = nameCredit.getText().toString();
            creditNumber = numberCredit.getText().toString();
        }

        if (nameLayoutStatus == true) {

            if (subTypeName.getText().toString().matches("")) {
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


        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                entryCall = apiInterface.journalEntry(getAuthToken("adhikanshmittalcool@gmail.com", "adhikansh/123"),
                        typeOfTrans, modeOfTrans, dateee, String.valueOf((int) totalAmount),
                        String.valueOf((int) totalAmount), creditName + ":" + subType);
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

    private void addItem() {

        itemName = name.getText().toString();

        //TODO: Check if itemName exists in inventory and accordingly change isNameFromInventory

        try {
            itemRate = rate.getText().toString();
            itemQuantity = quantity.getText().toString();
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
            nfe.printStackTrace();
            return;
        }


        if (isNameFromInventory) {

            if (itemName.length() > 1) {

                if (itemQuantity.length() != 0 && Double.parseDouble(itemQuantity) > 0.0) {

                    if (itemRate.length() != 0 && Double.parseDouble(itemRate) > 0.0) {
                        addItem(itemName, Double.parseDouble(itemRate), Double.parseDouble(itemQuantity));
                        setListViewHeightBasedOnChildren(listView);
                        changeSuggestions(itemName);
                        name.getText().clear();
                        rate.getText().clear();
                        quantity.getText().clear();
                    } else {
                        rate.setError("Enter correct value");
                    }
                } else {
                    quantity.setError("Enter correct value");
                }
            } else {
                name.setError("Name error! Length cannot be 1");
            }
        } else {
            name.setError("Name error! Item not in inventory");
        }

    }

    public void addItem(String itemName, double itemRate, double itemQuantity) {
        totalAmount += itemQuantity * itemRate;
        dataRows.add(new DataRow(itemName, itemRate, itemQuantity));
        total.setText("Rs. " + String.valueOf(totalAmount));
        dataAdapter.notifyDataSetChanged();
    }

    private void changeSuggestions(String suggestion) {
        words.add(suggestion);

        suggestion1.setText("Pencil");
        suggestion2.setText("Eraser");
        suggestion3.setText("Sharpner");
        suggestion4.setText("Ruler");
        suggestion5.setText("Notepad");

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
        } else if (id == R.id.done) {
            doneClicked();

        }else if (id == R.id.voice_input){
            t1.speak("Please speak out the entries", TextToSpeech.QUEUE_FLUSH, null);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    promptSpeechInput();
                }
            },1500);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using

        if (parent.getId() == R.id.types_spinner) {
            type = parent.getItemAtPosition(pos).toString();

            cashSelected.setChecked(true);
            cashSelected.setText("CASH");
            creditSelected.setText("");
            creditCredentials.setVisibility(View.GONE);
            modeOfTrans = "Cash";
            creditLayoutStatus = false;

            if (pos == 0) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;

                typeOfTrans = "Purchase";
                suggestionsUI.setVisibility(View.VISIBLE);

            } else if (pos == 1) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;
                suggestionsUI.setVisibility(View.VISIBLE);
                typeOfTrans = "Sales";

            } else if (pos == 2) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;
                suggestionsUI.setVisibility(View.VISIBLE);
                typeOfTrans = "Purchase Return";

            } else if (pos == 3) {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.VISIBLE);
                subTypeLayout.setVisibility(View.GONE);
                amountLayout.setVisibility(View.GONE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = false;
                credit = true;
                suggestionsUI.setVisibility(View.VISIBLE);
                typeOfTrans = "Sales Return";

            } else if (pos == 4) {

                Log.e("Error", "Payment Done");

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Payment Done";
                suggestionsUI.setVisibility(View.GONE);
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
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;
                suggestionsUI.setVisibility(View.GONE);
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
                transaction_mode.setVisibility(View.VISIBLE);
                nameLayoutStatus = true;
                amountLayoutStatus = true;
                credit = false;
                suggestionsUI.setVisibility(View.GONE);
                typeOfTrans = "Commission";
                subType = "Received";

            } else {

                subTypeNameLayout.setVisibility(View.GONE);
                purchaseLayout.setVisibility(View.GONE);
                subTypeLayout.setVisibility(View.VISIBLE);
                amountLayout.setVisibility(View.VISIBLE);
                commissionLayout.setVisibility(View.GONE);
                transaction_mode.setVisibility(View.GONE);
                nameLayoutStatus = false;
                amountLayoutStatus = true;
                credit = false;

                typeOfTrans = "Drawings";
                suggestionsUI.setVisibility(View.GONE);
                subTypeText.setText("Choose drawings type");
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.drawing_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subTypesSpinner.setAdapter(adapter);

            }
        }

        if (parent.getId() == R.id.sub_types_spinner) {

            subType = parent.getItemAtPosition(pos).toString();

            if (typeOfTrans.matches("Drawings"))
                modeOfTrans = subType;

            if ((typeOfTrans == "Payment Done" || typeOfTrans == "Payment Received") && pos == 2) {

                subTypeNameLayout.setVisibility(View.VISIBLE);
                nameLayoutStatus = true;

            } else if (typeOfTrans == "Drawings" && pos == 2) {
                purchaseLayout.setVisibility(View.VISIBLE);
                suggestionsUI.setVisibility(View.VISIBLE);
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
                    creditSelected.setText("BANK");
                    cashSelected.setText("");
                    modeOfTrans = "Bank";
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

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor prefEditor = prefs.edit();
//
//        product = prefs.getString("products", "Milk,");
//        products = product.split(",");

//        Log.e("Products", product);
//
//        ArrayAdapter<String> products_adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, products);
//        name.setAdapter(products_adapter);
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

    @Override
    public void onBackPressed() {

        AlertDialog b = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null).show();

        Button nbutton = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = b.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.BLACK);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //txtSpeechInput.setText(result.get(0));
//                    Toast.makeText(getBaseContext(), result.get(0), Toast.LENGTH_LONG).show();
                    String userInputString = result.get(0);
                    String[] user_input = result.get(0).split(" ");
                    if(user_input.length<3){
//                        Toast.makeText(getBaseContext(), "Please say the product name, quantity and cost", Toast.LENGTH_LONG).show();
                        t1.speak("Need 3 fields. Please try again", TextToSpeech.QUEUE_FLUSH, null);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                promptSpeechInput();
                            }
                        },1500);
                    }
                    else{
                        name.setText("");
                        int j=0;
                        String prodName = "";
                        String prodRate = "";
                        String prodQty = "";
                        for(int i=0;i<user_input.length;i++){
                            if(!user_input[i].toLowerCase().equals("rate")){
                                 if(!user_input[i].toLowerCase().equals("item"))
                                     prodName+=user_input[i];
                            }
                            else {
                                j=i;
                                break;
                            }
                        }
                        for(int i=0;i<user_input.length;i++){
                            if(user_input[i].equals("rate")){
                                prodRate = user_input[i+1];
                            }
                            if(user_input[i].equals("quantity")){
                                prodQty = user_input[i+1];
                            }
                        }
//                        String[] tTypes = getResources().getStringArray(R.array.types_array);
//                        Log.e("lala", tTypes[1]);
                        //name.setText(tTypes[1]);
                        name.setText(prodName);
                        rate.setText(prodRate);
                        quantity.setText(prodQty);

                        t1.speak(prodName+" added!", TextToSpeech.QUEUE_FLUSH, null);

                        if(userInputString.toLowerCase().contains("delete")){
                            name.setText("");
                            rate.setText("");
                            quantity.setText("");
                            t1.speak("Last entry cleared.", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
                break;
            }

        }
    }

}
