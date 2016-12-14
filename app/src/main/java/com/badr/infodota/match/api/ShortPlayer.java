package com.badr.infodota.match.api;

import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.player.api.Unit;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:18
 */
public class ShortPlayer implements Serializable {
    //the player's 32-bit Steam ID - will be set to "4294967295" if the player has set their account to private.
    @SerializedName("account_id")
    private long accountId;

    private Unit account;

    //an 8-bit unsigned int: if the left-most bit is set, the player was on dire. the two right-most bits represent the player slot (0-4).
    @SerializedName("player_slot")
    private int playerSlot;

    @SerializedName("hero_id")
    private int heroId;

    private Hero hero;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public Unit getAccount() {
        return account;
    }

    public void setAccount(Unit account) {
        this.account = account;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
