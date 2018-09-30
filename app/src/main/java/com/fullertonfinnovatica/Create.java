package com.fullertonfinnovatica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class Create extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String date;
    private String fydate;
    private String booksdate;
    private String name;
    private String number;
    private String address;
    private String email;

    private Button fy;
    private Button books;
    private Button verfiy;

    private EditText nameField;
    private EditText numberField;
    private EditText addressField;
    private EditText emailField;

    private int c=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_create);

        fy = (Button) findViewById(R.id.fydate);
        books = (Button) findViewById(R.id.bookdate);
        verfiy = (Button) findViewById(R.id.verify);

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
                //TODO
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

    public void showDatePickerDialog()
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


}
