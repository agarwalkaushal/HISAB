package com.fullertonfinnovatica.Accounts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

import java.util.List;

public class JournalRetrieveAdapter extends RecyclerView.Adapter<JournalRetrieveAdapter.RecyclerViewHolder>{

    List<JournalEntryModel> list;
    Context context;

    public JournalRetrieveAdapter(List<JournalEntryModel> arrayList2, Context context)
    {

        this.list=arrayList2;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_journal, viewGroup, false);
        JournalRetrieveAdapter.RecyclerViewHolder recyclerViewHolder = new JournalRetrieveAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRetrieveAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        JournalEntryModel modelList = list.get(i);

        String[] dateArray = modelList.getDate().split(" ");
        recyclerViewHolder.date.setText(dateArray[1] + " " + dateArray[2]);
        recyclerViewHolder.year.setText(dateArray[3]);
        recyclerViewHolder.credit_amt.setText(modelList.getCredit());
        recyclerViewHolder.debit_amt.setText(modelList.getDebit());
        recyclerViewHolder.first.setText(modelList.getTo());
        recyclerViewHolder.second.setText(modelList.getFrom());




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date, year, credit_amt, debit_amt, first, second;

        public RecyclerViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.date);
            year = view.findViewById(R.id.year);
            credit_amt = view.findViewById(R.id.amount_credit);
            debit_amt = view.findViewById(R.id.amount_debit);
            first = view.findViewById(R.id.first);
            second = view.findViewById(R.id.second);


        }
    }


}
