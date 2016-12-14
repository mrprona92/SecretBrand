package com.badr.infodota.stream.api.douyu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:52
 */
public class DouyuResult implements Serializable {
    @SerializedName("data")
    private Room room;
    private int error;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
