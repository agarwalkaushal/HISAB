package com.fullertonfinnovatica.Transaction;

public class DataRow {

    String itemName;
    int rateItem;
    int quantityItem;

    public DataRow(String itemName, int rateItem, int quantityItem)
    {
        this.itemName = itemName;
        this.rateItem = rateItem;
        this.quantityItem = quantityItem;
    }

    public String getItemName()
    {
        return itemName;
    }

    public int getRateItem()
    {
        return rateItem;
    }

    public  int getQuantityItem()
    {
        return quantityItem;
    }
}
