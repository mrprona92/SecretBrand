package com.badr.infodota.quiz.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.quiz.fragment.RecentHighscoreFragment;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 15:20
 */
public class HighscorePagerAdapter extends FragmentPagerAdapter {
    private Context context;


    public HighscorePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RecentHighscoreFragment.newInstance(position);
            case 1:
                return RecentHighscoreFragment.newInstance(position);
            case 2:
                return RecentHighscoreFragment.newInstance(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.quiz_high_score_item);
            case 1:
                return context.getString(R.string.quiz_high_score_abilities);
            case 2:
                return context.getString(R.string.quiz_high_score_random);
            default:
                return "";
        }
    }
}
