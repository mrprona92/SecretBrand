package com.badr.infodota.hero.api;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 16:32
 */
public class HeroesListHolder {
    private List<Hero> heroes;

    public HeroesListHolder(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public HeroesListHolder() {
        super();
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
