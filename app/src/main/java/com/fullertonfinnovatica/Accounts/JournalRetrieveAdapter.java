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

public class JournalRetrieveAdapter extends RecyclerView.Adapter<JournalRetrieveAdapter.RecyclerViewHolder> {

    List<JournalEntryModel> list;
    Context context;

    public JournalRetrieveAdapter(List<JournalEntryModel> arrayList2, Context context) {

        this.list = arrayList2;
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


        //modelList.getTo().toLowerCase().equals("cash")

        //typeOfTransaction.substring(0, 1).toUpperCase() + typeOfTransaction.substring(1).toLowerCase() + " A/c"
        //subTypeOfTransaction.substring(0, 1).toUpperCase() + subTypeOfTransaction.substring(1).toLowerCase() + " A/c"
        //modelList.getTo().substring(0, 1).toUpperCase() + modelList.getTo().substring(1).toLowerCase()

//        if (typeOfTransaction.contains("purchase") || typeOfTransaction.contains("sales")) {
//
//            recyclerViewHolder.first.setText(typeOfTransaction.substring(0, 1).toUpperCase() + typeOfTransaction.substring(1).toLowerCase() + " A/c");
//            if (modelList.getTo().toLowerCase().equals("cash")) {
//                recyclerViewHolder.second.setText(modelList.getTo().substring(0, 1).toUpperCase() + modelList.getTo().substring(1).toLowerCase() + " A/c");
//            } else {
//                recyclerViewHolder.second.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase() + " A/c");
//            }
//        } else if (typeOfTransaction.contains("payment")) {
//            recyclerViewHolder.first.setText(subTypeOfTransaction.substring(0, 1).toUpperCase() + subTypeOfTransaction.substring(1).toLowerCase() + " A/c");
//            if (subTypeOfTransaction.contains("settlement")) {
//                recyclerViewHolder.second.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase() + " A/c");
//            } else {
//                recyclerViewHolder.second.setText(modelList.getTo().substring(0, 1).toUpperCase() + modelList.getTo().substring(1).toLowerCase() + " A/c");
//            }
//        } else if (typeOfTransaction.contains("commission")) {
//            recyclerViewHolder.first.setText(modelList.getTo().substring(0, 1).toUpperCase() + modelList.getTo().substring(1).toLowerCase() + "A/c");
//            if(subTypeOfTransaction.contains("given"))
//                recyclerViewHolder.second.setText(typeOfTransaction.substring(0, 1).toUpperCase() + typeOfTransaction.substring(1).toLowerCase() + " Given");
//            else
//                recyclerViewHolder.second.setText(typeOfTransaction.substring(0, 1).toUpperCase() + typeOfTransaction.substring(1).toLowerCase() + " Received");
//        } else if (typeOfTransaction.contains("drawing")) {
//            recyclerViewHolder.first.setText(typeOfTransaction.substring(0, 1).toUpperCase() + typeOfTransaction.substring(1).toLowerCase() + " A/c");
//            recyclerViewHolder.second.setText(modelList.getTo().substring(0, 1).toUpperCase() + modelList.getTo().substring(1).toLowerCase() + " A/c");
//        }


        recyclerViewHolder.first.setText(modelList.getTo()+" a/c");
        recyclerViewHolder.second.setText(modelList.getFrom()+" a/c");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date, year, credit_amt, debit_amt, first, second, tofrom;

        public RecyclerViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.date);
            year = view.findViewById(R.id.year);
            credit_amt = view.findViewById(R.id.amount_credit);
            debit_amt = view.findViewById(R.id.amount_debit);
            first = view.findViewById(R.id.first);
            second = view.findViewById(R.id.second);
            tofrom = view.findViewById(R.id.tofrom);


        }
    }


}
