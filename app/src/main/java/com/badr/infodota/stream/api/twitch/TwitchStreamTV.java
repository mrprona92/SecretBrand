package com.badr.infodota.stream.api.twitch;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class TwitchStreamTV implements Serializable {
    @SerializedName("_links")
    private Map<String, String> links;
    private TwitchStream stream;

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public TwitchStream getStream() {
        return stream;
    }

    public void setStream(TwitchStream stream) {
        this.stream = stream;
    }
}
