package com.badr.infodota.hero.api.guide;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 12:57
 */
public class TitleOnly implements Serializable {
    @SerializedName("Title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
