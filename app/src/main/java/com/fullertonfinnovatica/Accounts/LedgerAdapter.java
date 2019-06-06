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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_ledger, viewGroup, false);
        LedgerAdapter.RecyclerViewHolder recyclerViewHolder = new LedgerAdapter.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LedgerAdapter.RecyclerViewHolder recyclerViewHolder, int i) {

        LedgerModel modelList = list.get(i);
        recyclerViewHolder.accountName.setText(modelList.getAccount_name().substring(1,modelList.getAccount_name().length()-1).toUpperCase() + " A/C");
        recyclerViewHolder.toDebitFields.setText("");
        recyclerViewHolder.toCreditField.setText("");

        String[] debit_name, debit_amt, credit_name, credit_amt;
        int debit_size,credit_size;

        debit_name = modelList.getDebit_name();
        debit_amt = modelList.getDebit_amt();
        credit_name = modelList.getCredit_name();
        credit_amt = modelList.getCredit_amt();
        debit_size = modelList.getDebitSize();
        credit_size = modelList.getCreditSize();

        float total1 = 0, total2 = 0;
        for(int j=0;j<debit_size;j++){
            String temp = "To " + debit_name[j].substring(1,debit_name[j].length()-1)+ " - "+debit_amt[j]+"\n";
            recyclerViewHolder.toDebitFields.setText(String.valueOf(recyclerViewHolder.toDebitFields.getText())+temp);
            total1 += Float.valueOf(debit_amt[j]);
        }

        for(int j=0;j<credit_size;j++){
            String temp = "By " + credit_name[j].substring(1,credit_name[j].length()-1) + " - "+credit_amt[j] + "\n";
            recyclerViewHolder.toCreditField.setText(String.valueOf(recyclerViewHolder.toCreditField.getText())+temp);
            total2 += Float.valueOf(credit_amt[j]);
        }

        if(total1>total2) {
            recyclerViewHolder.maxAmt1.setText(String.valueOf(total1));
            recyclerViewHolder.maxamt2.setText(String.valueOf(total1));
            recyclerViewHolder.bdDebit.setText("To Bal b/d "+Math.abs(total1-total2));
            recyclerViewHolder.cdCredit.setText("By Bal c/d "+Math.abs(total1-total2));
            recyclerViewHolder.bdCredit.setText("");
            recyclerViewHolder.cdDebit.setText("");
        }
        else
        {
            recyclerViewHolder.maxAmt1.setText(String.valueOf(total2));
            recyclerViewHolder.maxamt2.setText(String.valueOf(total2));
            recyclerViewHolder.bdCredit.setText("By Bal b/d "+Math.abs(total1-total2));
            recyclerViewHolder.cdDebit.setText("To Bal c/d "+Math.abs(total1-total2));
            recyclerViewHolder.bdDebit.setText("");
            recyclerViewHolder.cdCredit.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView accountName, toDebitFields, cdDebit, maxAmt1, bdDebit, toCreditField, cdCredit, maxamt2, bdCredit;

        public RecyclerViewHolder(View view) {
            super(view);

            accountName = view.findViewById(R.id.accountName);
            toDebitFields = view.findViewById(R.id.toDebitFields);
            cdDebit = view.findViewById(R.id.cdDebit);
            maxAmt1 = view.findViewById(R.id.maxAmount1);
            bdDebit = view.findViewById(R.id.bdDebit);
            toCreditField = view.findViewById(R.id.toCreditFields);
            cdCredit = view.findViewById(R.id.cdCredit);
            maxamt2 = view.findViewById(R.id.maxAmount2);
            bdCredit = view.findViewById(R.id.bdCredit);

        }
    }

}
