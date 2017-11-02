package com.mrprona.dota2assitant.youtube.model;

/**
 * Created by Admin on 7/8/2017.
 */

public class ChannelInfo {
    public ChannelInfo() {
    }

    private String description;
    private String name;
    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public ChannelInfo(String description, String name, int imageID, String urlID) {
        this.description = description;
        this.name = name;
        this.imageID = imageID;
        this.urlID = urlID;
    }

    public String getUrlID() {

        return urlID;
    }

    public void setUrlID(String urlID) {
        this.urlID = urlID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String urlID;


}
