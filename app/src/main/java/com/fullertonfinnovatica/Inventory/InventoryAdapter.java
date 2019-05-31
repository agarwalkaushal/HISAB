package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fullertonfinnovatica.R;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.RecyclerViewHolder> {

    List<InventoryModel> list;
    Context context;

    public InventoryAdapter(List<InventoryModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_inventory, viewGroup, false);
        InventoryAdapter.RecyclerViewHolder recyclerViewHolder = new InventoryAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        InventoryModel modelList = list.get(i);


        recyclerViewHolder.name.setText(modelList.getInventory_name().substring(1, modelList.getInventory_name().length()-1));
        recyclerViewHolder.cost.setText("₹ " + modelList.getInventory_cost());
        recyclerViewHolder.qty.setText(modelList.getInventory_qty() + "units");


        String[] inventoryCat = context.getResources().getStringArray(R.array.inventory_categories);
        String[] inventoryTags = context.getResources().getStringArray(R.array.inventory_tags);

        int c=0;
        for (String temp: inventoryCat) {
            if(modelList.getInventory_category().substring(1,modelList.getInventory_category().length()-1).matches(temp.trim().toLowerCase()))
            {
                break;
            }
            c++;
        }
        Glide.with(context).load(context.getResources().getIdentifier(inventoryTags[c], "drawable", context.getPackageName()))
                .error(R.drawable.back)
                .into(recyclerViewHolder.cat);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name,qty,cost;
        ImageView cat;

        public RecyclerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.prodName);
            qty= (TextView) view.findViewById(R.id.prodqty);
            cost = view.findViewById(R.id.prodCost);
            cat = (ImageView) view.findViewById(R.id.prodCat);

        }
    }

}
