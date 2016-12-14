package com.badr.infodota.hero.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.fragment.HeroDefaultItemBuild;
import com.badr.infodota.hero.fragment.HeroResponses;
import com.badr.infodota.hero.fragment.HeroSkills;
import com.badr.infodota.hero.fragment.HeroStatInfo;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 15:20
 */
public class HeroPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private Hero hero;

    public HeroPagerAdapter(FragmentManager fragmentManager, Context context, Hero hero) {
        super(fragmentManager);
        this.hero = hero;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HeroStatInfo.newInstance(hero);
            case 1:
                return HeroSkills.newInstance(hero);
            case 2:
                return HeroDefaultItemBuild.newInstance(hero);
            case 3:
                return HeroResponses.newInstance(hero);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.info);
            case 1:
                return context.getString(R.string.skills);
            case 2:
                return context.getString(R.string.default_guide);
            case 3:
                return context.getString(R.string.responses);
            default:
                return "";
        }
    }
}
