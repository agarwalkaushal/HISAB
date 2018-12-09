package com.fullertonfinnovatica.Transaction;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<DataRow> {

    private ArrayList<DataRow> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView itemName;
        TextView itemRate;
        TextView itemQuantity;
    }

    public DataAdapter(ArrayList<DataRow> data, Context context) {
        super(context, R.layout.item, data);
        this.dataSet = data;
        this.mContext=context;

    }


    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataRow dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.itemRate = (TextView) convertView.findViewById(R.id.item_rate);
            viewHolder.itemQuantity = (TextView) convertView.findViewById(R.id.item_quantity);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        lastPosition = position;

        viewHolder.itemName.setText(dataModel.getItemName());
        viewHolder.itemRate.setText(dataModel.getQuantityItem());
        viewHolder.itemQuantity.setText(dataModel.getRateItem());

        // Return the completed view to render on screen
        return convertView;
    }
}
