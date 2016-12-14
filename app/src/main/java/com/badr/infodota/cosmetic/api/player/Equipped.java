package com.badr.infodota.cosmetic.api.player;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:01
 */
public class Equipped implements Serializable {
    @SerializedName("class")
    private int classId;
    private int slot;

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
