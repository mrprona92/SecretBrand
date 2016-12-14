package com.util.infoparser.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 11:06
 */
public class CosmeticItemSet implements Serializable {
    private String code;
    private String name;
    private Map<String,String> items;
    @SerializedName("store_bundle")
    private String bundleItemName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public String getBundleItemName() {
        return bundleItemName;
    }

    public void setBundleItemName(String bundleItemName) {
        this.bundleItemName = bundleItemName;
    }
}
