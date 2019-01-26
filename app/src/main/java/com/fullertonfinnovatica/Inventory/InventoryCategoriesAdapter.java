package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fullertonfinnovatica.R;

import java.util.List;

public class InventoryCategoriesAdapter extends RecyclerView.Adapter<InventoryCategoriesAdapter.RecyclerViewHolder> {

    List<InventoryCategoriesModel> list;
    Context context;

    public InventoryCategoriesAdapter(List<InventoryCategoriesModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inventory_categories_card, viewGroup, false);
        InventoryCategoriesAdapter.RecyclerViewHolder recyclerViewHolder = new InventoryCategoriesAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryCategoriesAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        InventoryCategoriesModel modelList = list.get(i);

        recyclerViewHolder.name.setText(modelList.getInventory_name());
        Glide.with(context).load(modelList.getPic_name())
                .error(R.drawable.back)
                .into(recyclerViewHolder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;

        public RecyclerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.catName);
            img = view.findViewById(R.id.imgCategory);

        }
    }

}
