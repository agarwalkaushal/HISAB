package com.fullertonfinnovatica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    private EditText businessName;
    private EditText businessContact;
    private EditText businessEmail;
    private EditText businessAddress;

    private String type;
    private String email = "";
    private String address = "";

    private RadioGroup businessType;
    private RadioButton retailer;
    private RadioButton wholesaler;

    private ImageButton placePicker;

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Edit Profile</font>"));

        placePicker = findViewById(R.id.placePicker);
        businessName = findViewById(R.id.businessName);
        businessContact = findViewById(R.id.businessContact);
        businessEmail = findViewById(R.id.businessEmail);
        businessAddress = findViewById(R.id.businessAddress);
        businessType = findViewById(R.id.businessType);
        retailer = findViewById(R.id.retail);
        wholesaler = findViewById(R.id.wholesaler);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        businessName.setText(prefs.getString("name", "User"));
        businessContact.setText(prefs.getString("number", "0000000000"));

        String data = prefs.getString("Profile", "[\"Retailer\",\"\",\"India\"]");

        type = data.substring(data.indexOf("[") + 2, data.indexOf(",") - 1);
        email = data.substring(data.indexOf(",") + 1, data.indexOf(",", data.indexOf(",") + 1));
        address = data.substring(data.indexOf(",", data.indexOf(",") + 1) + 2, data.indexOf("]") - 1);
        Log.e("Email:", email);

        if (email.matches("\"\""))
            businessEmail.setText("");
        else
            businessEmail.setText(email.substring(1,email.length()-1));
        businessAddress.setText(address);

        if (type.matches("Retailer"))
            retailer.setChecked(true);
        else
            wholesaler.setChecked(true);

        placePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(Profile.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.updateProfile) {

            if (businessName.getText().toString().length() != 0) {
                if (businessAddress.getText().toString().length() != 0) {
                    if (businessContact.getText().toString().length() != 0) {

                        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        int selectedId = businessType.getCheckedRadioButtonId();
                        type = ((RadioButton) findViewById(selectedId)).getText().toString();
                        email = businessEmail.getText().toString();
                        address = businessAddress.getText().toString();

                        List<String> data = new ArrayList<String>();
                        data.add(type);
                        data.add(email);
                        data.add(address);
                        Gson gson = new Gson();
                        String json = gson.toJson(data);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Profile", json);
                        editor.commit();
                        sharedPreferences.edit().putString("name", businessName.getText().toString()).apply();
                        sharedPreferences.edit().putString("number", businessContact.getText().toString()).apply();

                        Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                        return true;

                    } else {
                        businessContact.setError("Please enter 10 digit number!");
                        return true;
                    }
                } else {
                    businessAddress.setError("Business Address is required!");
                    return true;
                }
            } else {
                businessName.setError("Business name is required!");
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String address = String.format("Place: %s", place.getName());
                businessAddress.setText(address);
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
