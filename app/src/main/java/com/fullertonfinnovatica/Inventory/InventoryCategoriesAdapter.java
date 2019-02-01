package com.fullertonfinnovatica.Inventory;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/COMIC.TTF");
        recyclerViewHolder.name.setTypeface(font);
        recyclerViewHolder.name.setText(modelList.getInventory_name());
        Glide.with(context).load(context.getResources().getIdentifier(modelList.getPic_name(), "drawable", context.getPackageName()))
                .error(R.drawable.back)
                .into(recyclerViewHolder.img);

        Log.e("Color: ", String.valueOf(context.getResources().getIdentifier(modelList.getBackground_color(),"color",context.getPackageName())));
        recyclerViewHolder.card.setCardBackgroundColor(
                context.getResources().getIdentifier(modelList.getBackground_color(),"color",context.getPackageName()));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;
        CardView card;

        public RecyclerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.catName);
            img = view.findViewById(R.id.imgCategory);
            card = (CardView) view.findViewById(R.id.card_view);


        }
    }

}
