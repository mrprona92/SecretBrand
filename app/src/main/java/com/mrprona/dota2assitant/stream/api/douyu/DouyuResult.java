package com.mrprona.dota2assitant.stream.api.douyu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:52
 */
public class DouyuResult implements Serializable {
    @SerializedName("data")
    private Data data;
    private int error;

    public Room getRoom() {
        return data.getmRoom().get(0);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
