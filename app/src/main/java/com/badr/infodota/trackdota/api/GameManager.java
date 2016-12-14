package com.badr.infodota.trackdota.api;

import android.support.v4.util.LongSparseArray;

import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.trackdota.api.core.Player;

/**
 * Created by ABadretdinov
 * 16.04.2015
 * 14:45
 */
public class GameManager {
    private static GameManager instance = null;
    private LongSparseArray<Player> players = new LongSparseArray<>();
    private LongSparseArray<Hero> heroes = new LongSparseArray<>();
    private LongSparseArray<Item> items = new LongSparseArray<>();

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public Player getPlayer(long accountId) {
        return players.get(accountId);
    }

    public Hero getHero(long heroId) {
        return heroes.get(heroId);
    }

    public Item getItem(long itemId) {
        return items.get(itemId);
    }

    public boolean containsPlayer(long accountId) {
        return players.indexOfKey(accountId) >= 0;
    }

    public boolean containsHero(long heroId) {
        return heroes.indexOfKey(heroId) >= 0;
    }

    public boolean containsItem(long itemId) {
        return items.indexOfKey(itemId) >= 0;
    }

    public void addPlayer(Player player) {
        players.put(player.getAccountId(), player);
    }

    public void addHero(Hero hero) {
        heroes.put(hero.getId(), hero);
    }

    public void addItem(Item item) {
        items.put(item.getId(), item);
    }
}
