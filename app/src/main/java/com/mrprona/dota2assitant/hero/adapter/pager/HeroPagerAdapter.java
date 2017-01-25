package com.mrprona.dota2assitant.hero.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.TalentTree;
import com.mrprona.dota2assitant.hero.fragment.HeroDefaultItemBuild;
import com.mrprona.dota2assitant.hero.fragment.HeroResponses;
import com.mrprona.dota2assitant.hero.fragment.HeroSkills;
import com.mrprona.dota2assitant.hero.fragment.HeroStatInfo;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 15:20
 */
public class HeroPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private Hero hero;
    private TalentTree talentTree;

    public HeroPagerAdapter(FragmentManager fragmentManager, Context context, Hero hero,TalentTree mTalentTree) {
        super(fragmentManager);
        this.hero = hero;
        this.context = context;
        this.talentTree= mTalentTree;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HeroStatInfo.newInstance(hero,talentTree);
            case 1:
                return HeroSkills.newInstance(hero);
            case 2:
                return HeroDefaultItemBuild.newInstance(hero);
            case 3:
                return HeroResponses.newInstance(hero,context);
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
