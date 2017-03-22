package com.mrprona.dota2assitant.ranking;

import com.mrprona.dota2assitant.hero.api.CarouselHero;

import java.util.ArrayList;

/**
 * Created by BinhTran on 3/15/17.
 */

public class TeamRanking {

    private String rankCurrent;

    public String getRankCurrent() {
        return rankCurrent;
    }

    public void setRankCurrent(String rankCurrent) {
        this.rankCurrent = rankCurrent;
    }

    private String numberRanking;
    private String teamName;
    private String flagID;
    private String changeRanking;

    public String getChangeRanking() {
        return changeRanking;
    }

    public void setChangeRanking(String changeRanking) {
        this.changeRanking = changeRanking;
    }

    public TeamRanking(String rankCurrent,String numberRanking, String teamName, String flagID, String change) {
        this.rankCurrent= rankCurrent;
        this.numberRanking = numberRanking;
        this.teamName = teamName;
        this.flagID = flagID;
        this.changeRanking=change;
    }

    public TeamRanking() {
    }

    public String getNumberRanking() {
        return numberRanking;
    }

    public void setNumberRanking(String numberRanking) {
        this.numberRanking = numberRanking;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFlagID() {
        return flagID;
    }

    public void setFlagID(String flagID) {
        this.flagID = flagID;
    }

    public static class List extends ArrayList<TeamRanking> {

    }

}
