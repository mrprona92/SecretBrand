package com.badr.infodota.hero.api;

import com.google.gson.annotations.SerializedName;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 16:33
 */
public class GetHeroes {
    @SerializedName("result")
    private HeroesListHolder heroesListHolder;

    public GetHeroes(HeroesListHolder heroesListHolder) {
        this.heroesListHolder = heroesListHolder;
    }

    public HeroesListHolder getHeroesListHolder() {
        return heroesListHolder;
    }

    public void setHeroesListHolder(HeroesListHolder heroesListHolder) {
        this.heroesListHolder = heroesListHolder;
    }
}
