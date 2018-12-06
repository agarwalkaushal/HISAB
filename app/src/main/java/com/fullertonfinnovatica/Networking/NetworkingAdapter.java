package com.fullertonfinnovatica.Networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fullertonfinnovatica.R;

import java.util.List;

public class NetworkingAdapter extends RecyclerView.Adapter<NetworkingAdapter.RecyclerViewHolder> {

    List<NetworkingModel> list;
    Context context;

    public NetworkingAdapter(List<NetworkingModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_networking, viewGroup, false);
        NetworkingAdapter.RecyclerViewHolder recyclerViewHolder = new NetworkingAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkingAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        NetworkingModel modelList = list.get(i);

        recyclerViewHolder.name.setText(modelList.getBname());
        recyclerViewHolder.dist.setText(modelList.getPno());



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
