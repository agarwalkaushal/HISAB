package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.Networking.NetworkingDetailsScreen;
import com.fullertonfinnovatica.Networking.NetworkingMain;
import com.fullertonfinnovatica.Networking.NetworkingModel;
import com.fullertonfinnovatica.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InventoryCategories extends AppCompatActivity {

    RecyclerView recyclerView;
    List<InventoryCategoriesModel> list = new ArrayList<>();
    InventoryCategoriesAdapter dataAdapter;
    LinearLayout emptyInventory;
    TextView inventoryEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_categories);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ff0000'>Inventory</font>"));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = findViewById(R.id.invCategoryRecycler);
        emptyInventory = (LinearLayout) findViewById(R.id.emptyInventory);
        inventoryEmpty = (TextView) findViewById(R.id.inventoryEmpty);

        if(prefs.getBoolean("empty",true)==true){

            prefs.edit().putBoolean("empty",false).apply();
            recyclerView.setVisibility(View.INVISIBLE);
            emptyInventory.setVisibility(View.VISIBLE);
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/COMIC.TTF");
            inventoryEmpty.setTypeface(font);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            emptyInventory.setVisibility(View.INVISIBLE);

            addCategories();

            dataAdapter = new InventoryCategoriesAdapter(list, getBaseContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
            recyclerView.setAdapter(dataAdapter);

            recyclerView.addOnItemTouchListener(new InventoryCategories.RecyclerTouchListener(getBaseContext(),
                    recyclerView, new InventoryCategories.ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well

                    InventoryCategoriesModel pojo;
                    pojo = list.get(position);

                    Intent in = new Intent(InventoryCategories.this, InventoryView.class);
                    in.putExtra("b_name", pojo.getInventory_name());
                    startActivity(in);
                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(InventoryCategories.this, "Long press on position :" + position,
                            Toast.LENGTH_LONG).show();
                }
            }));
        }

    }

    void addCategories(){

        InventoryCategoriesModel c1 = new InventoryCategoriesModel();
        c1.Inventory_name = "Food";
        c1.pic_name = "abc";

        InventoryCategoriesModel c2 = new InventoryCategoriesModel();
        c2.Inventory_name = "Stationary";
        c2.pic_name = "abc";

        InventoryCategoriesModel c3 = new InventoryCategoriesModel();
        c3.Inventory_name = "Cosmetics";
        c3.pic_name = "abc";

        InventoryCategoriesModel c4 = new InventoryCategoriesModel();
        c4.Inventory_name = "Others";
        c4.pic_name = "abc";

        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);

    }

    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final InventoryCategories.ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
