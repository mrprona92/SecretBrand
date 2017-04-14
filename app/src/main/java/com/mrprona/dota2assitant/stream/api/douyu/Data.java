package com.mrprona.dota2assitant.stream.api.douyu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:49
 */
public class Data implements Serializable {
    @SerializedName("count")
    private int id;

    @SerializedName("room")
    private List<Room> mRoom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Room> getmRoom() {
        return mRoom;
    }

    public void setmRoom(List<Room> mRoom) {
        this.mRoom = mRoom;
    }
}
