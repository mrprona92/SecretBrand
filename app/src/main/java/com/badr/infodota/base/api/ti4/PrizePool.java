package com.badr.infodota.base.api.ti4;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 14.05.14
 * Time: 18:46
 */
public class PrizePool implements Serializable {
    @SerializedName("prize_pool")
    private long prizePool;
    @SerializedName("league_id")
    private long leagueId;

    public long getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(long prizePool) {
        this.prizePool = prizePool;
    }

    public long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }
}
