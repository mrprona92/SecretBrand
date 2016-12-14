package com.badr.infodota.stream.api.twitch;

import com.badr.infodota.base.entity.HasId;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchStream implements HasId, Serializable {
    @SerializedName("_id")
    private long id;
    @SerializedName("_links")
    private Map<String, String> links;
    private TwitchChannel channel;
    private String game;
    private Map<String, String> preview;
    private int viewers;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public TwitchChannel getChannel() {
        return channel;
    }

    public void setChannel(TwitchChannel channel) {
        this.channel = channel;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Map<String, String> getPreview() {
        return preview;
    }

    public void setPreview(Map<String, String> preview) {
        this.preview = preview;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    @Override
    public String toString() {
        if (channel != null) {
            return channel.toString();
        }
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitchStream stream = (TwitchStream) o;

        if (id != stream.id) return false;
        if (!channel.getName().equals(stream.channel.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + channel.hashCode();
        return result;
    }

    public static class List extends ArrayList<TwitchStream> {
    }
}
