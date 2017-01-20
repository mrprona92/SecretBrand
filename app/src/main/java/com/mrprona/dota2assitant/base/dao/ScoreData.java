package com.mrprona.dota2assitant.base.dao;

/**
 * Created by BinhTran on 12/26/16.
 */

public class ScoreData {

    private String name;
    private String country;
    private String score;
    private String date;
    private String id;

    public ScoreData(String name, String country, String score, String date, String id) {
        this.name = name;
        this.country = country;
        this.score = score;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
