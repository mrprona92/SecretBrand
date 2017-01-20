package com.mrprona.dota2assitant.quiz.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class HighScore {

    public String ranking;
    public String name;
    public String state ;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long score;
    public String date;

    public HighScore() {
        // Default constructor required for calls to DataSnapshot.getValue(HighScore.class)
    }

    public HighScore(String ranking, String name, String state, long score, String date) {
        this.ranking = ranking;
        this.name = name;
        this.state = state;
        this.score = score;
        this.date = date;
    }

    // [START post_to_map]
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id", ranking);
            result.put("name", name);
            result.put("state", state);
            result.put("score", score);
            result.put("date", date);
            return result;
    }
    // [END post_to_map]

}
// [END post_class]
