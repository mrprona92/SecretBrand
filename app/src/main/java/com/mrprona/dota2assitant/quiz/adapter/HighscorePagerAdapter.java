package com.mrprona.dota2assitant.quiz.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.quiz.fragment.RecentHighscoreFragment;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 15:20
 */
public class HighscorePagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    // Three simple fragments
    RecentHighscoreFragment fragA;
    RecentHighscoreFragment fragB;
    RecentHighscoreFragment fragC;


    public HighscorePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
        setFragments(context);
    }



    public void setFragments(Context c) {
        // Set up the simple base fragments
        fragA = RecentHighscoreFragment.newInstance(0);
        fragB = RecentHighscoreFragment.newInstance(1);
        fragC = RecentHighscoreFragment.newInstance(2);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragA;
            case 1:
                return fragB;
            case 2:
                return fragC;
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
