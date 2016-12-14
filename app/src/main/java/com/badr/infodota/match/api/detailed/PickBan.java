package com.badr.infodota.match.api.detailed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 13:44
 */
public class PickBan implements Serializable {
    @SerializedName("is_pick")
    private boolean isPick;
    @SerializedName("hero_id")
    private int heroId;
    private int team;
    private int order;

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        this.isPick = pick;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
