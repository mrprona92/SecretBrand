package com.badr.infodota.hero.api.responses;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 19.06.2015
 * 16:03
 */
public class HeroResponse implements Serializable {
    private String title;
    private List<String> items;
    private List<String> heroes;
    private String rune;
    private String url;
    private String localUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public List<String> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<String> heroes) {
        this.heroes = heroes;
    }

    public String getRune() {
        return rune;
    }

    public void setRune(String rune) {
        this.rune = rune;
    }
}
