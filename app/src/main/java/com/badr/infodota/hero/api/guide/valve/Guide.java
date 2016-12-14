package com.badr.infodota.hero.api.guide.valve;

import com.badr.infodota.hero.api.guide.GuideItems;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 19.01.14
 */
public class Guide implements Serializable {
    private String author;
    private String hero;
    @SerializedName("Title")
    private String title;
    @SerializedName("Items")
    private GuideItems items;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GuideItems getItems() {
        return items;
    }

    public void setItems(GuideItems items) {
        this.items = items;
    }
}
