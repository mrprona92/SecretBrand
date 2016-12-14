package com.badr.infodota.cosmetic.api.price;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:28
 */
public class ItemsPricesHolder implements Serializable {
    @SerializedName("result")
    private ItemsPrices itemsPrices;

    public ItemsPrices getItemsPrices() {
        return itemsPrices;
    }

    public void setItemsPrices(ItemsPrices itemsPrices) {
        this.itemsPrices = itemsPrices;
    }
}
