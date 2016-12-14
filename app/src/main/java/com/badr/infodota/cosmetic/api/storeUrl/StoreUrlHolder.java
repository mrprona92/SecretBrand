package com.badr.infodota.cosmetic.api.storeUrl;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 30.12.2014
 * 14:30
 */
public class StoreUrlHolder implements Serializable {
    @SerializedName("result")
    private StoreUrl storeUrl;

    public StoreUrl getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(StoreUrl storeUrl) {
        this.storeUrl = storeUrl;
    }
}
