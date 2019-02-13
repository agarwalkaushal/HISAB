package com.fullertonfinnovatica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Inventory.InventoryCategories;
import com.fullertonfinnovatica.Networking.NetworkingMain;
import com.fullertonfinnovatica.Networking.NetworkingShopsViewMap;
import com.fullertonfinnovatica.Transaction.Transaction;
import com.fullertonfinnovatica.Transaction.TransactionView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView businessname;
    private TextView phonenumber;
    private String businessName;
    private String phoneNumber;

    private CardView transcation;
    private CardView transactionView;
    private CardView support;
    private CardView messages;
    private ActionMode mActionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        FirebaseApp.initializeApp(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("login",false)==false){
            prefs.edit().putBoolean("login",true).apply();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Dashboard</font>"));
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View parentView = navigationView.getHeaderView(0);

        businessname = (TextView) parentView.findViewById(R.id.businessname);
        phonenumber = (TextView) parentView.findViewById(R.id.phonenumber);
        transcation = (CardView) findViewById(R.id.transactionButton);
        transactionView = (CardView) findViewById(R.id.transactionView);
        support = (CardView) findViewById(R.id.support);
        messages = (CardView) findViewById(R.id.messages);


        transcation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, Transaction.class);
                startActivity(i);
            }
        });

        transactionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, TransactionView.class);
                startActivity(i);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.support, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.sms:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "8077510286", null)));
                                return true;
                            case R.id.email:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("message/rfc822");
                                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"nexus@finovatica.com"});
                                startActivity(Intent.createChooser(intent, "Send email..."));
                                return true;
                            default:
                                return false;
                        }
                    } } );

                popup.show();
               //registerForContextMenu(v);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Messages View, activity not created
                Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_SHORT).show();
            }
        });

        businessName = prefs.getString("name","User");
        phoneNumber = prefs.getString("number","Mobile");

        businessname.setText(businessName);
        phonenumber.setText("+91-" + phoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifications) {
            //TODO: Notifications View, activity not created
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // TODO: Intent to Accounts activity, package created but not activity
            Toast.makeText(this, "Accounts", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_finance) {
            // TODO: Intent to Finances activity, package created but not activity
            Toast.makeText(this, "Finance", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_inventory) {
            Intent intent = new Intent(this, InventoryCategories.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_network) {
            Intent intent = new Intent(this, NetworkingShopsViewMap.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_profile) {
            // TODO: Open Profile activity
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();

        }else if (id == R.id.nav_general) {
            // TODO: Open Settings activity
            Toast.makeText(this, "General", Toast.LENGTH_SHORT).show();

        }  else if (id == R.id.nav_singout) {

            Intent i = new Intent(Dashboard.this, Initial.class);
            prefs.edit().putBoolean("login",false).apply();
            FirebaseAuth.getInstance().signOut();
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
