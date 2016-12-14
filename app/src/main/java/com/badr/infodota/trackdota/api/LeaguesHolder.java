package com.badr.infodota.trackdota.api;

import com.badr.infodota.trackdota.api.game.League;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 05.06.2015
 * 18:26
 */
public class LeaguesHolder implements Serializable {
    private int count;
    private List<League> leagues;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
}
