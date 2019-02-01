package com.fullertonfinnovatica.Inventory;

import android.widget.ImageView;

public class InventoryCategoriesModel {

    String Inventory_name, pic_name, background_color;

    public String getInventory_name() {
        return Inventory_name;
    }

    public void setInventory_name(String inventory_name) {
        Inventory_name = inventory_name;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getBackground_color() {
        return background_color;
    }

    public  void setBackground_color(String background_color)
    {
        this.background_color = background_color;
    }
}
