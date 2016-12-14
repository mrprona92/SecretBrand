package com.util.infoparser.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 11:50
 */
public class GameCosmetics implements Serializable{
    @SerializedName("items")
    private Map<String,CosmeticItem> items;
    @SerializedName("item_sets")
    private Map<String,CosmeticItemSet> sets;
    @SerializedName("items_autographs")
    private Map<String,CosmeticItemAutograph> autographs;

    public Map<String, CosmeticItem> getItems() {
        return items;
    }

    public void setItems(Map<String, CosmeticItem> items) {
        this.items = items;
    }

    public Map<String, CosmeticItemSet> getSets() {
        return sets;
    }

    public void setSets(Map<String, CosmeticItemSet> sets) {
        this.sets = sets;
    }

    public Map<String, CosmeticItemAutograph> getAutographs() {
        return autographs;
    }

    public void setAutographs(Map<String, CosmeticItemAutograph> autographs) {
        this.autographs = autographs;
    }
}
