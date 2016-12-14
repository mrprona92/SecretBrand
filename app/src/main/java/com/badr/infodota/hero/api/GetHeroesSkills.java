package com.badr.infodota.hero.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * User: Histler
 * Date: 16.01.14
 */
public class GetHeroesSkills {
    @SerializedName("abilitydata")
    Map<String, Skill> abilities;

    public GetHeroesSkills() {
    }

    public Map<String, Skill> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<String, Skill> abilities) {
        this.abilities = abilities;
    }
}
