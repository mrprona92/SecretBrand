package com.badr.infodota.player.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 16.04.14
 */
public class PlayersResult implements Serializable {
    @SerializedName("response")
    private PlayersHolder playersHolder;

    public PlayersHolder getPlayersHolder() {
        return playersHolder;
    }

    public void setPlayersHolder(PlayersHolder playersHolder) {
        this.playersHolder = playersHolder;
    }
}
