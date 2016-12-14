package com.badr.infodota.cosmetic.api.player;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:47
 */
public class PlayerCosmeticItemsHolder implements Serializable {
    @SerializedName("result")
    private PlayerCosmeticItems playerCosmeticItems;

    public PlayerCosmeticItems getPlayerCosmeticItems() {
        return playerCosmeticItems;
    }

    public void setPlayerCosmeticItems(PlayerCosmeticItems playerCosmeticItems) {
        this.playerCosmeticItems = playerCosmeticItems;
    }
}
