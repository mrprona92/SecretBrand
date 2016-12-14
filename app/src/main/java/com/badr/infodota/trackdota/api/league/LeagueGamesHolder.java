package com.badr.infodota.trackdota.api.league;

import com.badr.infodota.trackdota.api.game.League;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 15:36
 */
public class LeagueGamesHolder implements Serializable {
    private League league;
    private int count;
    private List<LeagueGame> games;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LeagueGame> getGames() {
        return games;
    }

    public void setGames(List<LeagueGame> games) {
        this.games = games;
    }
}
