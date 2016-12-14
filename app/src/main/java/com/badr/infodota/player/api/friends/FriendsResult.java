package com.badr.infodota.player.api.friends;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 16:46
 */
public class FriendsResult implements Serializable {
    @SerializedName("friendslist")
    private FriendsHolder friendsHolder;

    public FriendsHolder getFriendsHolder() {
        return friendsHolder;
    }

    public void setFriendsHolder(FriendsHolder friendsHolder) {
        this.friendsHolder = friendsHolder;
    }
}
