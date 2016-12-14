package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:22
 */
public class Capability implements Serializable {

    @SerializedName("can_craft_mark")
    private boolean canCraftMark;
    @SerializedName("can_be_restored")
    private boolean canBeRestored;
    @SerializedName("strange_parts")
    private boolean strangeParts;
    @SerializedName("paintable_unusual")
    private boolean paintableUnusual;
    private boolean autograph;
    @SerializedName("can_consume")
    private boolean canConsume;

    public boolean isCanCraftMark() {
        return canCraftMark;
    }

    public void setCanCraftMark(boolean canCraftMark) {
        this.canCraftMark = canCraftMark;
    }

    public boolean isCanBeRestored() {
        return canBeRestored;
    }

    public void setCanBeRestored(boolean canBeRestored) {
        this.canBeRestored = canBeRestored;
    }

    public boolean isStrangeParts() {
        return strangeParts;
    }

    public void setStrangeParts(boolean strangeParts) {
        this.strangeParts = strangeParts;
    }

    public boolean isPaintableUnusual() {
        return paintableUnusual;
    }

    public void setPaintableUnusual(boolean paintableUnusual) {
        this.paintableUnusual = paintableUnusual;
    }

    public boolean isAutograph() {
        return autograph;
    }

    public void setAutograph(boolean autograph) {
        this.autograph = autograph;
    }

    public boolean isCanConsume() {
        return canConsume;
    }

    public void setCanConsume(boolean canConsume) {
        this.canConsume = canConsume;
    }
}
