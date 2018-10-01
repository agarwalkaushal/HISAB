package com.fullertonfinnovatica;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static android.R.attr.phoneNumber;

public class Create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "PhoneAuthActivity";

    private String date;
    private String fydate;
    private String booksdate;
    private String name;
    private String number;
    private String address;
    private String email;
    private String mVerificationId;

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

    private int c=0;
    private int count=0;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_create);

        fy = (Button) findViewById(R.id.fydate);
        books = (Button) findViewById(R.id.bookdate);
        verfiy = (Button) findViewById(R.id.verify);
        verfiyCode = (Button) findViewById(R.id.verifycode);
        resendCode = (Button) findViewById(R.id.resendcode);
        nameField = (TextInputLayout) findViewById(R.id.nameinput);
        numberField = (TextInputLayout) findViewById(R.id.phoneinput);
        addressField = (TextInputLayout) findViewById(R.id.addressinput);
        emailField = (TextInputLayout) findViewById(R.id.emailinput);
        mVerificationField = (TextInputLayout) findViewById(R.id.entercode);

        disableViews(verfiyCode,resendCode,mVerificationField);

        fy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                showDatePickerDialog();
                fydate = date;
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=2;
                showDatePickerDialog();
                booksdate = date;
            }
        });

        verfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.length() == 0)
                    nameField.setError("Name is required!");
                else
                    count++;

                if (number.length() == 0)
                    numberField.setError("Name is required!");
                else
                    count++;

                if(count==0) {
                    Toast.makeText(Create.this, "You might receive an SMS message for verification and standard sms rates may apply", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Create.this, Dashboard.class);
                    startActivity(i);
                    i.putExtra("Business name",name);
                    i.putExtra("Phone number",number);
                }
                else
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

        if(c==1) {
            date = "Financial Year: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            fy.setText(date);
        }
        else if(c==2) {
            date = "Books: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            books.setText(date);
        }

    }

    private void phoneSignIn(String number)
    {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void showDatePickerDialog()
    {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(Create.this ,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
    }

    private void hideStatusBar()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN );
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
