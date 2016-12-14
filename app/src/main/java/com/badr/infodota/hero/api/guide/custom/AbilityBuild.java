package com.badr.infodota.hero.api.guide.custom;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class AbilityBuild implements Serializable {
    @SerializedName("AbilityOrder")
    private Map<String, String> order;
    @SerializedName("AbilityTooltips")
    private Map<String, String> tooltips;

    public Map<String, String> getOrder() {
        return order;
    }

    public void setOrder(Map<String, String> order) {
        this.order = order;
    }

    public Map<String, String> getTooltips() {
        return tooltips;
    }

    public void setTooltips(Map<String, String> tooltips) {
        this.tooltips = tooltips;
    }
}
