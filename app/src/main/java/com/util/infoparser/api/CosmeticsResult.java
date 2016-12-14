package com.util.infoparser.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 11:51
 */
public class CosmeticsResult implements Serializable {
    @SerializedName("items_game")
    private GameCosmetics cosmetics;

    public GameCosmetics getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(GameCosmetics cosmetics) {
        this.cosmetics = cosmetics;
    }
}
