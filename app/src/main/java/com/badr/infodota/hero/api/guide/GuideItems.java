package com.badr.infodota.hero.api.guide;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 19.01.14
 */
public class GuideItems implements Serializable {
    private String[] startingItems;
    @SerializedName("startingItems_Secondary")
    private String[] startingItemsSecondary;
    private String[] earlyGame;
    @SerializedName("earlyGame_Secondary")
    private String[] earlyGameSecondary;
    private String[] coreItems;
    @SerializedName("coreItems_Secondary")
    private String[] coreItemsSecondary;
    private String[] luxury;

    public String[] getStartingItemsSecondary() {
        return startingItemsSecondary;
    }

    public void setStartingItemsSecondary(String[] startingItemsSecondary) {
        this.startingItemsSecondary = startingItemsSecondary;
    }

    public String[] getEarlyGameSecondary() {
        return earlyGameSecondary;
    }

    public void setEarlyGameSecondary(String[] earlyGameSecondary) {
        this.earlyGameSecondary = earlyGameSecondary;
    }

    public String[] getCoreItemsSecondary() {
        return coreItemsSecondary;
    }

    public void setCoreItemsSecondary(String[] coreItemsSecondary) {
        this.coreItemsSecondary = coreItemsSecondary;
    }

    public String[] getStartingItems() {
        return startingItems;
    }

    public void setStartingItems(String[] startingItems) {
        this.startingItems = startingItems;
    }

    public String[] getEarlyGame() {
        return earlyGame;
    }

    public void setEarlyGame(String[] earlyGame) {
        this.earlyGame = earlyGame;
    }

    public String[] getCoreItems() {
        return coreItems;
    }

    public void setCoreItems(String[] coreItems) {
        this.coreItems = coreItems;
    }

    public String[] getLuxury() {
        return luxury;
    }

    public void setLuxury(String[] luxury) {
        this.luxury = luxury;
    }
}
