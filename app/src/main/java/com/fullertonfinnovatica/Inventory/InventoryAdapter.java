package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullertonfinnovatica.Networking.NetworkingModel;
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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_networking, viewGroup, false);
        InventoryAdapter.RecyclerViewHolder recyclerViewHolder = new InventoryAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        InventoryModel modelList = list.get(i);

        recyclerViewHolder.name.setText(modelList.getInventory_name());
        recyclerViewHolder.dist.setText(modelList.getInventory_cost());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name,dist,desc;

        public RecyclerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.shop_name);
            dist= (TextView) view.findViewById(R.id.distance);

        }
    }

}
