package com.badr.infodota.stream.api.twitch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchGameStreams implements Serializable {
    @SerializedName("_links")
    private Map<String, String> links;
    private TwitchStream.List streams;

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public TwitchStream.List getStreams() {
        return streams;
    }

    public void setStreams(TwitchStream.List streams) {
        this.streams = streams;
    }
}
