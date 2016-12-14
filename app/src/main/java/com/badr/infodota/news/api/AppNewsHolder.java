package com.badr.infodota.news.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:27
 */
public class AppNewsHolder implements Serializable {
    @SerializedName("appnews")
    private AppNews appNews;

    public AppNews getAppNews() {
        return appNews;
    }

    public void setAppNews(AppNews appNews) {
        this.appNews = appNews;
    }
}
