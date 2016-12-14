package com.badr.infodota.item.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * User: Histler
 * Date: 18.01.14
 */
public class ItemsHolder {
    @SerializedName("itemdata")
    Map<String, Item> items;

    public ItemsHolder() {
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }
}
