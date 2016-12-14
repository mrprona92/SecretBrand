package com.badr.infodota.counter.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 01.09.14
 */
public class TruepickerReccomends implements Serializable {
    @SerializedName("recommendsforteama")
    private String recommendsForRadiant;
    @SerializedName("recommendsforteamb")
    private String recommendsForDire;

    public String getRecommendsForRadiant() {
        return recommendsForRadiant;
    }

    public void setRecommendsForRadiant(String recommendsForRadiant) {
        this.recommendsForRadiant = recommendsForRadiant;
    }

    public String getRecommendsForDire() {
        return recommendsForDire;
    }

    public void setRecommendsForDire(String recommendsForDire) {
        this.recommendsForDire = recommendsForDire;
    }
}
