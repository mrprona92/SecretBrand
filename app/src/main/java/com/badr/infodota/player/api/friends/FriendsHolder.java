package com.badr.infodota.player.api.friends;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 16:46
 */
public class FriendsHolder implements Serializable {
    private List<Friend> friends;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }
}
