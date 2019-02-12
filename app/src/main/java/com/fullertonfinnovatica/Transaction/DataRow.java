package com.fullertonfinnovatica.Transaction;

public class DataRow {

    String itemName;
    double rateItem;
    double quantityItem;

    public DataRow(String itemName, double rateItem, double quantityItem)
    {
        this.itemName = itemName;
        this.rateItem = rateItem;
        this.quantityItem = quantityItem;
    }

    public String getItemName()
    {
        return itemName;
    }

    public double getRateItem()
    {
        return rateItem;
    }

    public  double getQuantityItem()
    {
        return quantityItem;
    }
}
