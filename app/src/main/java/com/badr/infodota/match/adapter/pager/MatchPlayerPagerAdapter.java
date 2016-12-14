package com.badr.infodota.match.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.fragment.MatchPlayerSkillBuild;
import com.badr.infodota.match.fragment.MatchPlayerSummary;

/**
 * User: Histler
 * Date: 23.01.14
 */
public class MatchPlayerPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private Player player;
    //workaround for random ability draft
    private boolean randomSkills;

    public MatchPlayerPagerAdapter(FragmentManager fm, Context context, boolean isRandomSkills, Player player) {
        super(fm);
        this.context = context;
        this.player = player;
        this.randomSkills = isRandomSkills;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return MatchPlayerSummary.newInstance(player);
            case 1:
                return MatchPlayerSkillBuild.newInstance(randomSkills, player);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.common);
            default:
            case 1:
                return context.getString(R.string.skill_build);
        }
    }
}
