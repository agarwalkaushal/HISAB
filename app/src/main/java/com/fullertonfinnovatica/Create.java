package com.fullertonfinnovatica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Inventory.InventoryAPI;
import com.fullertonfinnovatica.Inventory.InventoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Callback<SignUpModel> {

    private String date;
    private String fydate;
    private String booksdate;
    private String name;
    private String number;
    private String address;
    private String email = null;
    private String type;

    private Button fy;
    private Button books;
    private Button verfiy;

    private RadioButton typeSelected;
    private RadioGroup businessType;

    private TextInputLayout nameField;
    private TextInputLayout numberField;
    private TextInputLayout addressField;
    private TextInputLayout emailField;

    private TextInputEditText nameFieldEdit;
    private TextInputEditText numberFieldEdit;
    private TextInputEditText addressFieldEdit;
    private TextInputEditText emailFieldEdit;
    private int c = 0;

    SignUpAPI apiInterface;
    JSONObject paramObject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_create);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fy = (Button) findViewById(R.id.fydate);
        books = (Button) findViewById(R.id.bookdate);
        verfiy = (Button) findViewById(R.id.verify);

        businessType = (RadioGroup) findViewById(R.id.radioType);

        nameField = (TextInputLayout) findViewById(R.id.nameinput);
        numberField = findViewById(R.id.phoneinput);
        addressField = (TextInputLayout) findViewById(R.id.addressinput);
        emailField = (TextInputLayout) findViewById(R.id.emailinput);

        nameFieldEdit = (TextInputEditText) findViewById(R.id.nameinputedit);
        numberFieldEdit = (TextInputEditText) findViewById(R.id.phoneinputedit);
        addressFieldEdit = (TextInputEditText) findViewById(R.id.addressinputedit);
        emailFieldEdit = (TextInputEditText) findViewById(R.id.emailinputedit);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SignUpAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(SignUpAPI.class);

        fy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = 1;
                showDatePickerDialog();
                fydate = date;
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = 2;
                showDatePickerDialog();
                booksdate = date;
            }
        });

        verfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = businessType.getCheckedRadioButtonId();

                name = nameFieldEdit.getText().toString();
                number = numberFieldEdit.getText().toString();
                address = addressFieldEdit.getText().toString();
                email = emailFieldEdit.getText().toString();
                type = ((RadioButton) findViewById(selectedId)).getText().toString();

                if (name.length() != 0) {
                    if(address.length()!= 0) {
                        if (number.length() == 10) {

                            List<String> data = new ArrayList<String>();
                            data.add(type);
                            data.add(email);
                            data.add(address);
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Profile",json);
                            Log.e("Json: ",json);
                            editor.commit();
                            Toast.makeText(Create.this, "You might receive an SMS message for verification and standard sms rates may apply", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Create.this, PhoneVerify.class);
                            i.putExtra("PhoneNo", number);
                            i.putExtra("name", name);

                            try {
                                paramObject = new JSONObject();
                                paramObject.put("bname", name);
                                paramObject.put("btype", type);
                                paramObject.put("pno", number);
                                paramObject.put("email", email);
                                paramObject.put("baddress", address);
                                paramObject.put("fyear", fydate);
                                paramObject.put("booksdate", booksdate);
                                sendData(paramObject);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(i);
                            finish();
                        } else {
                            numberField.setError("Please enter 10 digit number!");
                            return;
                        }
                    }
                    else
                    {
                        addressField.setError("Business Address is required!");
                        return;
                    }
                }
                else {
                    nameField.setError("Business name is required!");
                    return;
                }
            }
        });
    }

    @Override
    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
    }

    @Override
    public void onFailure(Call<SignUpModel> call, Throwable t) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (c == 1) {
            date = "Financial Year: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            fy.setText(date);
        } else if (c == 2) {
            date = "Books: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            books.setText(date);
        }

    }



    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(Create.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    void sendData(JSONObject j){


        Call<SignUpModel> userCall = apiInterface.getUse(j.toString());
        userCall.enqueue(this);
        Toast.makeText(getBaseContext(), "Sent data", Toast.LENGTH_LONG).show();

    }

    /*
    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
            v.setVisibility(View.VISIBLE);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
            v.setVisibility(View.INVISIBLE);
        }
    }
    */
}
