package com.badr.infodota.trackdota;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.badr.infodota.match.activity.MatchPlayerDetailsActivity;
import com.badr.infodota.match.api.AbilityUpgrade;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.api.detailed.AdditionalUnit;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.trackdota.api.live.Ability;
import com.badr.infodota.trackdota.api.live.LivePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ABadretdinov
 * 21.08.2015
 * 11:07
 */


public class OnLivePlayerClickListener implements View.OnClickListener {
    private LivePlayer mLivePlayer;
    private String mPlayerName;

    public OnLivePlayerClickListener(LivePlayer livePlayer, String playerName) {
        this.mLivePlayer = livePlayer;
        this.mPlayerName = playerName;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Player matchPlayer = new Player();
        matchPlayer.setHeroId((int) mLivePlayer.getHeroId());
        matchPlayer.setKills(mLivePlayer.getKills());
        matchPlayer.setDeaths(mLivePlayer.getDeath());
        matchPlayer.setAssists(mLivePlayer.getAssists());
        matchPlayer.setGold(mLivePlayer.getGold());
        matchPlayer.setLastHits(mLivePlayer.getLastHits());
        matchPlayer.setDenies(mLivePlayer.getDenies());
        matchPlayer.setXpPerMin(mLivePlayer.getXpm());
        matchPlayer.setGoldPerMin(mLivePlayer.getGpm());
        matchPlayer.setLevel(mLivePlayer.getLevel());
        List<Ability> abilities = mLivePlayer.getAbilities();
                                /*this shit with level may not work, that's why we need dummy */
        AbilityUpgrade[] upgrades = new AbilityUpgrade[mLivePlayer.getLevel()];
        for (int i = 0, size = abilities.size(); i < size; i++) {
            Ability ability = abilities.get(i);
            int[] abBuild = ability.getBuild();
            for (int index = 0; index < abBuild.length; index++) {
                if (abBuild[index] == 1) {
                    AbilityUpgrade upgrade = new AbilityUpgrade();
                    upgrade.setAbility(ability.getId());
                    upgrade.setLevel(index + 1);
                    if (upgrades.length <= index) {
                        AbilityUpgrade[] dummy = new AbilityUpgrade[index + 1];
                        System.arraycopy(upgrades, 0, dummy, 0, upgrades.length);
                        upgrades = dummy;
                    }
                    upgrades[index] = upgrade;
                }
            }
        }
                            /*delete empty upgrades (if player has extra level points)*/
        int newSize = upgrades.length;
        while (newSize > 0 && upgrades[newSize - 1] == null) {
            newSize--;
        }
        if (newSize != upgrades.length) {
            upgrades = Arrays.copyOf(upgrades, newSize);
        }
        List<AbilityUpgrade> abilityUpgrades = Arrays.asList(upgrades);

        matchPlayer.setAbilityUpgrades(abilityUpgrades);
        AdditionalUnit au = new AdditionalUnit();
        long[] itemIds = mLivePlayer.getItemIds();
        switch (itemIds.length) {
            default:
            case 12:
                au.setItem5((int) itemIds[11]);
            case 11:
                au.setItem4((int) itemIds[10]);
            case 10:
                au.setItem3((int) itemIds[9]);
            case 9:
                au.setItem2((int) itemIds[8]);
            case 8:
                au.setItem1((int) itemIds[7]);
            case 7:
                au.setItem0((int) itemIds[6]);
                List<AdditionalUnit> additionalUnits = new ArrayList<AdditionalUnit>();
                matchPlayer.setAdditionalUnits(additionalUnits);
            case 6:
                matchPlayer.setItem5((int) itemIds[5]);
            case 5:
                matchPlayer.setItem4((int) itemIds[4]);
            case 4:
                matchPlayer.setItem3((int) itemIds[3]);
            case 3:
                matchPlayer.setItem2((int) itemIds[2]);
            case 2:
                matchPlayer.setItem1((int) itemIds[1]);
            case 1:
                matchPlayer.setItem0((int) itemIds[0]);
        }
        matchPlayer.setRespawnTimer(mLivePlayer.getRespawnTimer());
        matchPlayer.setUltCooldown(mLivePlayer.getUltCooldown());
        matchPlayer.setUltState(mLivePlayer.getUltState());
        matchPlayer.setNetWorth(mLivePlayer.getNetWorth());
        matchPlayer.setLeaverStatus(0);
        Unit account = new Unit();
        account.setAccountId(mLivePlayer.getAccountId());
        account.setName(mPlayerName);
        matchPlayer.setAccount(account);
        matchPlayer.setAccountId(mLivePlayer.getAccountId());

        Intent intent = new Intent(context, MatchPlayerDetailsActivity.class);
        intent.putExtra("player", matchPlayer);
        context.startActivity(intent);
    }
}
