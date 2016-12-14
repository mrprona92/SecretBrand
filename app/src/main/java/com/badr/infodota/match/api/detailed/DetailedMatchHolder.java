package com.badr.infodota.match.api.detailed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:15
 */
public class DetailedMatchHolder implements Serializable {
    @SerializedName("result")
    private DetailedMatch detailedMatch;

    public DetailedMatch getDetailedMatch() {
        return detailedMatch;
    }

    public void setDetailedMatch(DetailedMatch detailedMatch) {
        this.detailedMatch = detailedMatch;
    }
}
