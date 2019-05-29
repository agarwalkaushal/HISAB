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

public class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.RecyclerViewHolder> {

    List<TrialModel> list;
    Context context;

    public TrialAdapter(List<TrialModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public TrialAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_trialbalance, viewGroup, false);
        TrialAdapter.RecyclerViewHolder recyclerViewHolder = new TrialAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrialAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        TrialModel modelList = list.get(i);
        recyclerViewHolder.accountName.setText(modelList.getName().substring(1, modelList.getName().length()-1).toUpperCase());
        if(modelList.getType().toLowerCase().contains("credit")){
            recyclerViewHolder.creditAmt.setText(modelList.getAmount());
            recyclerViewHolder.debitAmt.setText("");
        }else{
            recyclerViewHolder.debitAmt.setText(modelList.getAmount());
            recyclerViewHolder.creditAmt.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView accountName, debitAmt, creditAmt;

        public RecyclerViewHolder(View view) {
            super(view);

            accountName = view.findViewById(R.id.accountName);
            debitAmt = view.findViewById(R.id.debitAmount);
            creditAmt = view.findViewById(R.id.creditAmount);


        }
    }

}
