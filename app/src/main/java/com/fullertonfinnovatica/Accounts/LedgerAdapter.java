package com.fullertonfinnovatica.Accounts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

import java.util.List;

public class LedgerAdapter extends RecyclerView.Adapter<LedgerAdapter.RecyclerViewHolder>{

    List<LedgerModel> list;
    Context context;

    public LedgerAdapter(List<LedgerModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public LedgerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ledger_card, viewGroup, false);
        LedgerAdapter.RecyclerViewHolder recyclerViewHolder = new LedgerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LedgerAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        LedgerModel modelList = list.get(i);
        recyclerViewHolder.byField.setText(modelList.getBalance_type());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView byField;

        public RecyclerViewHolder(View view) {
            super(view);

            byField = view.findViewById(R.id.bybalance);


        }
    }

}
