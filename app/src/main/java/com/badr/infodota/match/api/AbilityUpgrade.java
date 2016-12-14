package com.badr.infodota.match.api;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:13
 */
public class AbilityUpgrade implements Serializable {
    // the numeric ID of the ability that the point was spent on.
    private long ability;
    //the time this ability point was spent - in number of seconds since the start of the match.
    private long time;
    //the level of the hero when the ability was leveled.
    private int level;

    public long getAbility() {
        return ability;
    }

    public void setAbility(long ability) {
        this.ability = ability;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return String.valueOf(ability);
    }
}
