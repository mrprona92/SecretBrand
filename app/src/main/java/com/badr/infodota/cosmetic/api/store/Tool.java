package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:24
 */
public class Tool implements Serializable {
    private String type;
    @SerializedName("use_string")
    private String useString;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseString() {
        return useString;
    }

    public void setUseString(String useString) {
        this.useString = useString;
    }
}
