package com.mrprona.dota2assitant.ranking;

import java.util.ArrayList;

/**
 * Created by BinhTran on 3/15/17.
 */

public class PlayerRanking {

    private String rankCurrent;
    private String numberRanking;
    private String playerName;
    private String flagID;

    public PlayerRanking(String rankCurrent, String numberRanking, String playerName, String flagID) {
        this.rankCurrent= rankCurrent;
        this.numberRanking = numberRanking;
        this.playerName = playerName;
        this.flagID = flagID;
    }

    public PlayerRanking() {
    }

    public String getNumberRanking() {
        return numberRanking;
    }

    public void setNumberRanking(String numberRanking) {
        this.numberRanking = numberRanking;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getFlagID() {
        return flagID;
    }

    public void setFlagID(String flagID) {
        this.flagID = flagID;
    }

    public String getRankCurrent() {
        return rankCurrent;
    }

    public void setRankCurrent(String rankCurrent) {
        this.rankCurrent = rankCurrent;
    }

    public static class List extends ArrayList<PlayerRanking> {

    }
}
