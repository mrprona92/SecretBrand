package com.mrprona.dota2assitant.player.adapter.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.fragment.PlayerCommonStats;

/**
 * User: ABadretdinov
 * Date: 27.03.14
 * Time: 18:11
 */
public class PlayerCommonStatsPagerAdapter extends FragmentPagerAdapter {
    private static final String[] metrics = {"total", "minute"};
    private String[] titles;
    private Bundle args;
    private Unit account;

    public PlayerCommonStatsPagerAdapter(FragmentManager fm, Context context, Bundle args, Unit account) {
        super(fm);
        this.args = args;
        this.account = account;
        titles = context.getResources().getStringArray(R.array.player_common_stats_titles);
    }

    @Override
    public Fragment getItem(int position) {
        return PlayerCommonStats.newInstance(account, args, metrics[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
