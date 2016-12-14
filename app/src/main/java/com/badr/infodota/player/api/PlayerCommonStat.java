package com.badr.infodota.player.api;

import com.badr.infodota.hero.api.Hero;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ABadretdinov
 * 20.03.2015
 * 17:59
 */
public class PlayerCommonStat implements Serializable {
    private Hero hero;
    private String header;
    private String matchId;
    private boolean won;
    private String dateTime;
    private String result;

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class List extends ArrayList<PlayerCommonStat> {

    }
}
