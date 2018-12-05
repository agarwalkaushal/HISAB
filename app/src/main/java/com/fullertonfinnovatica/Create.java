package com.fullertonfinnovatica;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "PhoneAuthActivity";

    private String date;
    private String fydate;
    private String booksdate;
    private String name;
    private String number;
    private String address;
    private String email;
    private String phone_no;

    private Button fy;
    private Button books;
    private Button verfiy;
    private Button verfiyCode;
    private Button resendCode;

    private TextInputLayout nameField;
    private TextInputLayout numberField;
    private TextInputLayout addressField;
    private TextInputLayout emailField;
    private TextInputLayout mVerificationField;

    private TextInputEditText nameFieldEdit;
    private TextInputEditText numberFieldEdit;
    private TextInputEditText addressFieldEdit;
    private TextInputEditText emailFieldEdit;
    private TextInputEditText mVerificationFieldEdit;

    private int c = 0;
    private int d = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_create);

        phone_no = getIntent().getStringExtra("user_number");

        fy = (Button) findViewById(R.id.fydate);
        books = (Button) findViewById(R.id.bookdate);
        verfiy = (Button) findViewById(R.id.verify);
        verfiyCode = (Button) findViewById(R.id.verifycode);
        resendCode = (Button) findViewById(R.id.resendcode);

        nameField = (TextInputLayout) findViewById(R.id.nameinput);
        numberField = findViewById(R.id.phoneinput);
        addressField = (TextInputLayout) findViewById(R.id.addressinput);
        emailField = (TextInputLayout) findViewById(R.id.emailinput);
        mVerificationField = (TextInputLayout) findViewById(R.id.entercode);

        nameFieldEdit = (TextInputEditText) findViewById(R.id.nameinputedit);
        numberFieldEdit = (TextInputEditText) findViewById(R.id.phoneinputedit);
        addressFieldEdit = (TextInputEditText) findViewById(R.id.addressinputedit);
        emailFieldEdit = (TextInputEditText) findViewById(R.id.emailinputedit);
        mVerificationFieldEdit = (TextInputEditText) findViewById(R.id.entercodeedit);

        //numberField.setHint(phone_no);
        //numberField.setEnabled(false);

        disableViews(verfiyCode, resendCode, mVerificationField);

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

        c = 0;
        d = 0;

        verfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameFieldEdit.getText().toString();
                number = numberFieldEdit.getText().toString();

                if (name.length() == 0)
                    nameField.setError("Business name is required!");
                else
                    c++;

                if (number.length() == 0)
                    numberField.setError("Number is required!");
                else
                    d++;

                if (c >= 1 && d >= 1) {
                    Toast.makeText(Create.this, "You might receive an SMS message for verification and standard sms rates may apply", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Create.this, Dashboard.class);
                    i.putExtra("name", name);
                    i.putExtra("number", number);
                    Intent i2 = new Intent(Create.this, PhoneVerify.class);
                    i2.putExtra("PhoneNo", number);
                    startActivity(i2);
                    finish();
                } else
                    return;

                /*
                name = nameField.getEditText().toString();
                number = numberField.getEditText().toString();
                address = addressField.getEditText().toString();
                email = emailField.getEditText().toString();





                if (address.length() == 0)
                    addressField.setError("Name is required!");
                else
                    count++;

                if(count == 0)
                {

                    phoneSignIn(number);
                    disableViews(nameField,addressField,emailField,verfiy);
                    enableViews(verfiyCode,resendCode,mVerificationField);
                }
                else
                    return;
                    */

            }
        });
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
}
