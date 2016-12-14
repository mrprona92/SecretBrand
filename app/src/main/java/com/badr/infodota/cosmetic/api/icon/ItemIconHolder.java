package com.badr.infodota.cosmetic.api.icon;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 12:16
 */
public class ItemIconHolder implements Serializable {
    @SerializedName("result")
    private ItemIcon itemIcon;

    public ItemIcon getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(ItemIcon itemIcon) {
        this.itemIcon = itemIcon;
    }
}
