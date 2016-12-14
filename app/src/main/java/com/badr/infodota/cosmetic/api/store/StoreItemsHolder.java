package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 18:09
 */
public class StoreItemsHolder implements Serializable {
    @SerializedName("result")
    private StoreItems storeItems;

    public StoreItems getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(StoreItems storeItems) {
        this.storeItems = storeItems;
    }
}
