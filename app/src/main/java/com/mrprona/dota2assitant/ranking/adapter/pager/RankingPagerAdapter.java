package com.mrprona.dota2assitant.ranking.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.util.Refresher;
import com.mrprona.dota2assitant.base.util.Updatable;
import com.mrprona.dota2assitant.ranking.fragment.PlayerRankingFragment;
import com.mrprona.dota2assitant.ranking.fragment.TeamrankingFragment;
import com.mrprona.dota2assitant.trackdota.api.game.GamesResult;
import com.mrprona.dota2assitant.trackdota.fragment.FeaturedGamesList;
import com.mrprona.dota2assitant.trackdota.fragment.LeaguesList;
import com.mrprona.dota2assitant.trackdota.fragment.LiveGamesList;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:29
 */
public class RankingPagerAdapter extends FragmentPagerAdapter {
    public static final int TEAM_RANKING = 0;
    public static final int PLAYER_RANKING = 1;

    private String[] titles;
    private SparseArray<Object> fragmentsMap = new SparseArray<>();

    public RankingPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.rankingtitle);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentsMap.indexOfKey(position) >= 0) {
            return (Fragment) fragmentsMap.get(position);
        }
        switch (position) {
            case TEAM_RANKING:
                TeamrankingFragment mRankingFragment = new TeamrankingFragment();
                fragmentsMap.put(TEAM_RANKING, mRankingFragment);
                return mRankingFragment;
            case PLAYER_RANKING:
                PlayerRankingFragment mPlayerRankingFragment = new PlayerRankingFragment();
                fragmentsMap.put(PLAYER_RANKING, mPlayerRankingFragment);
                return mPlayerRankingFragment;
            default:
                return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentsMap.remove(position);
        super.destroyItem(container, position, object);
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
