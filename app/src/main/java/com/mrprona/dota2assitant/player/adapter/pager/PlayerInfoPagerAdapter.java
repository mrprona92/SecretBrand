package com.mrprona.dota2assitant.player.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.match.fragment.MatchHistory;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.fragment.FriendsList;
import com.mrprona.dota2assitant.player.fragment.PlayerByHeroStatsFilter;
import com.mrprona.dota2assitant.player.fragment.PlayerCommonStatsFilter;
import com.mrprona.dota2assitant.player.fragment.PlayerCosmeticItemsList;

/**
 * User: ABadretdinov
 * Date: 18.02.14
 * Time: 16:50
 */
public class PlayerInfoPagerAdapter extends FragmentPagerAdapter {
    private Unit account;
    private String[] titles;

    public PlayerInfoPagerAdapter(Context context, FragmentManager fm, Unit account) {
        super(fm);
        this.account = account;
        titles = context.getResources().getStringArray(R.array.player_info_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FriendsList.newInstance(account);
            case 1:
                return PlayerCosmeticItemsList.newInstance(account);
            case 2:
                return MatchHistory.newInstance(account);
            case 3:
                return PlayerCommonStatsFilter.newInstance(account);
            default:
                return PlayerByHeroStatsFilter.newInstance(account);
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
