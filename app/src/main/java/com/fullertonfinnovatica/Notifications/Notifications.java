package com.fullertonfinnovatica.Notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.View;

import com.fullertonfinnovatica.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationsAdapter dataAdapter;
    NotificationsModel model, model2;
    List<NotificationsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Notifications</font>"));
        View view = findViewById(R.id.holder);
        model = new NotificationsModel();
        list = new ArrayList<>();

        model.setBody("GoodDay is approaching expiry");
        model.setTitle("EXPIRY ALERT");
        model.setImg("expiry");
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);

        recyclerView = findViewById(R.id.recycler);
        dataAdapter = new NotificationsAdapter(list, getBaseContext(), view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(dataAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(dataAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}
