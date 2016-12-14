package com.badr.infodota.news.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:27
 */
public class AppNews implements Serializable {
    @SerializedName("appid")
    private Long appId;
    @SerializedName("newsitems")
    private List<NewsItem> newsItems;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }
}
