package com.badr.infodota.cosmetic.api.price;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:27
 */
public class ItemsPrices implements Serializable {
    private boolean success;
    @SerializedName("assets")
    private List<ItemPrice> itemPrices;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ItemPrice> getItemPrices() {
        return itemPrices;
    }

    public void setItemPrices(List<ItemPrice> itemPrices) {
        this.itemPrices = itemPrices;
    }
}
