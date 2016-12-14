package com.util.infoparser.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 11:15
 */
public class CosmeticItemAutograph implements Serializable {
    private String id;
    private String name;
    private String autograph;
    private String workshoplink;
    @SerializedName("icon_path")
    private String iconPath;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getWorkshoplink() {
        return workshoplink;
    }

    public void setWorkshoplink(String workshoplink) {
        this.workshoplink = workshoplink;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
