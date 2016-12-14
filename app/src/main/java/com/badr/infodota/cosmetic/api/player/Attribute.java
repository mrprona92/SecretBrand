package com.badr.infodota.cosmetic.api.player;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:52
 */
public class Attribute implements Serializable {
    private long defindex;
    private Object value;
    @SerializedName("float_value")
    private float floatValue;

    public long getDefindex() {
        return defindex;
    }

    public void setDefindex(long defindex) {
        this.defindex = defindex;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }
}
