package com.badr.infodota.match.api.history;

import com.badr.infodota.match.api.Player;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 14:58
 */
public class HistoryMatch implements Serializable {
    @SerializedName("match_id")
    private long id;
    //the match's sequence number - the order in which matches are recorded
    @SerializedName("match_seq_num")
    private long seqNum;
    // date in UTC seconds since Jan 1, 1970 (unix time format)
    @SerializedName("start_time")
    private long startTime;
    /*
    * 8 = 1 vs 1
    * 7 = Ranking matchmaking
    * 6 = Solo Queue
    * 5 = Team match.
    * 4 = Co-op with bots.
    * 3 = Tutorial.
    * 2 = Tournament.
    * 1 = Practice.
    * 0 = Public matchmaking.
    * -1 = Invalid.
    * */
    @SerializedName("lobby_type")
    private int lobbyType;

    private List<Player> players;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(long seqNum) {
        this.seqNum = seqNum;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(int lobbyType) {
        this.lobbyType = lobbyType;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
