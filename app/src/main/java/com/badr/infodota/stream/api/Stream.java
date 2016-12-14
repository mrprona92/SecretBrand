package com.badr.infodota.stream.api;

import com.badr.infodota.base.entity.HasId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Badr on 17.04.2015.
 */
public class Stream implements Serializable, HasId, Comparable {
    @Expose
    private long id;
    @Expose
    private String channel;

    private String image;

    @Expose
    private long viewers;
    @Expose
    @SerializedName("embed_id")
    private String embedId;
    @Expose
    private String title;
    @Expose
    private String language;
    @Expose
    private String provider = "twitch";

    @Expose
    @SerializedName("hls_streams")
    private java.util.List<StreamQuality> qualities;
    @Expose
    @SerializedName("lang_confirmed")
    private boolean isLangConfirmed;
    @Expose
    @SerializedName("hls_enabled")
    private boolean isHlsEnabled;

    private boolean isFavourite;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public long getViewers() {
        return viewers;
    }

    public void setViewers(long viewers) {
        this.viewers = viewers;
    }

    public String getEmbedId() {
        return embedId;
    }

    public void setEmbedId(String embedId) {
        this.embedId = embedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public java.util.List<StreamQuality> getQualities() {
        return qualities;
    }

    public void setQualities(java.util.List<StreamQuality> qualities) {
        this.qualities = qualities;
    }

    public boolean isLangConfirmed() {
        return isLangConfirmed;
    }

    public void setLangConfirmed(boolean isLangConfirmed) {
        this.isLangConfirmed = isLangConfirmed;
    }

    public boolean isHlsEnabled() {
        return isHlsEnabled;
    }

    public void setHlsEnabled(boolean isHlsEnabled) {
        this.isHlsEnabled = isHlsEnabled;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stream stream = (Stream) o;

        if (channel != null ? !channel.equals(stream.channel) : stream.channel != null)
            return false;
        return !(provider != null ? !provider.equals(stream.provider) : stream.provider != null);

    }

    @Override
    public int hashCode() {
        int result = channel != null ? channel.hashCode() : 0;
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Stream) {
            return (int) (viewers - ((Stream) another).viewers);
        }
        return -1;
    }

    public static class List extends ArrayList<Stream> {
    }
}
