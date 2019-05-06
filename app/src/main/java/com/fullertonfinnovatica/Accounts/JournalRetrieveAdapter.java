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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_journal_retrieve, viewGroup, false);
        JournalRetrieveAdapter.RecyclerViewHolder recyclerViewHolder = new JournalRetrieveAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRetrieveAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        JournalEntryModel modelList = list.get(i);

        recyclerViewHolder.to.setText(modelList.getTo());
        recyclerViewHolder.from.setText(modelList.getFrom());
        recyclerViewHolder.date.setText(modelList.getDate());
        recyclerViewHolder.credit.setText(modelList.getCredit());
        recyclerViewHolder.debit.setText(modelList.getDebit());
        recyclerViewHolder.narration.setText(modelList.getNarration());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView to, from, date, credit, debit, narration;

        public RecyclerViewHolder(View view) {
            super(view);

            to = (TextView) view.findViewById(R.id.to);
            from = (TextView) view.findViewById(R.id.from);
            date = view.findViewById(R.id.date);
            credit = view.findViewById(R.id.credit);
            debit = view.findViewById(R.id.debit);
            narration = view.findViewById(R.id.narration);

        }
    }


}
