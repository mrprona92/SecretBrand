package com.badr.infodota.trackdota.api.league;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 15:30
 */
public class LeagueGame implements Serializable {
    @SerializedName("dire_team")
    private String direTeamName;
    @SerializedName("dire_score")
    private int direScore;
    @SerializedName("dire_series_score")
    private int direSeriesScore;
    @SerializedName("dire_team_id")
    private long direTeamId;

    @SerializedName("radiant_team")
    private String radiantTeamName;
    @SerializedName("radiant_score")
    private int radiantScore;
    @SerializedName("radiant_series_score")
    private int radiantSeriesScore;
    @SerializedName("radiant_team_id")
    private long radiantTeamId;

    private long id;
    private long duration;
    private int status;
    private long viewers;
    @SerializedName("series_type")
    private int seriesType;
    private long time;
    /*
    * 0 - radiant
    * 1 - dire
    * */
    private int winner;

    public String getDireTeamName() {
        return direTeamName;
    }

    public void setDireTeamName(String direTeamName) {
        this.direTeamName = direTeamName;
    }

    public int getDireScore() {
        return direScore;
    }

    public void setDireScore(int direScore) {
        this.direScore = direScore;
    }

    public int getDireSeriesScore() {
        return direSeriesScore;
    }

    public void setDireSeriesScore(int direSeriesScore) {
        this.direSeriesScore = direSeriesScore;
    }

    public long getDireTeamId() {
        return direTeamId;
    }

    public void setDireTeamId(long direTeamId) {
        this.direTeamId = direTeamId;
    }

    public String getRadiantTeamName() {
        return radiantTeamName;
    }

    public void setRadiantTeamName(String radiantTeamName) {
        this.radiantTeamName = radiantTeamName;
    }

    public int getRadiantScore() {
        return radiantScore;
    }

    public void setRadiantScore(int radiantScore) {
        this.radiantScore = radiantScore;
    }

    public int getRadiantSeriesScore() {
        return radiantSeriesScore;
    }

    public void setRadiantSeriesScore(int radiantSeriesScore) {
        this.radiantSeriesScore = radiantSeriesScore;
    }

    public long getRadiantTeamId() {
        return radiantTeamId;
    }

    public void setRadiantTeamId(long radiantTeamId) {
        this.radiantTeamId = radiantTeamId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public int getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(int seriesType) {
        this.seriesType = seriesType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
