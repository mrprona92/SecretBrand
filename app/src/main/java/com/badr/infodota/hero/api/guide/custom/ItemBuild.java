package com.badr.infodota.hero.api.guide.custom;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class ItemBuild implements Serializable {
    //private GuideItems items;
    @SerializedName("Items")
    private Map<String, List<String>> items;
    @SerializedName("ItemTooltips")
    private Map<String, String> tooltips;

    public Map<String, String> getTooltips() {
        return tooltips;
    }

    public void setTooltips(Map<String, String> tooltips) {
        this.tooltips = tooltips;
    }

    public Map<String, List<String>> getItems() {
        return items;
    }

    public void setItems(Map<String, List<String>> items) {
        this.items = items;
    }
}
