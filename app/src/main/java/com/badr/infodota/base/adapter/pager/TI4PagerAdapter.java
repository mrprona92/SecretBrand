package com.badr.infodota.base.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.ti4.MatchResults;
import com.badr.infodota.joindota.fragment.LeaguesGamesList;

/**
 * User: ABadretdinov
 * Date: 15.05.14
 * Time: 13:03
 */
public class TI4PagerAdapter extends FragmentPagerAdapter {
    public static final long TI_4_LEAGUE_ID = 600L;
    private Context context;

    public TI4PagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            /*case 0:
                return new CompendiumInfo();*/
            case 0:
                return LeaguesGamesList.newInstance("&c2=7057&c1=2390");
            case 1:
                return MatchResults.newInstance(TI_4_LEAGUE_ID);
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
                return context.getString(R.string.upcoming_matches);
            case 1:
                return context.getString(R.string.match_history);
            default:
                return "";
        }
    }
}
