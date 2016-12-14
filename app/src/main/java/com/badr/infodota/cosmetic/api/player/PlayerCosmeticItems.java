package com.badr.infodota.cosmetic.api.player;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:46
 */
public class PlayerCosmeticItems implements Serializable {
    private int status;
    @SerializedName("num_backpack_slots")
    private long numBackpackSlots;
    private List<PlayerCosmeticItem> items;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getNumBackpackSlots() {
        return numBackpackSlots;
    }

    public void setNumBackpackSlots(long numBackpackSlots) {
        this.numBackpackSlots = numBackpackSlots;
    }

    public List<PlayerCosmeticItem> getItems() {
        return items;
    }

    public void setItems(List<PlayerCosmeticItem> items) {
        this.items = items;
    }
}
