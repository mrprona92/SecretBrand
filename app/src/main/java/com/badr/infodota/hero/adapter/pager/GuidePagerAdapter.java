package com.badr.infodota.hero.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.fragment.guide.AbilityBuildPart;
import com.badr.infodota.hero.fragment.guide.GuideHolder;
import com.badr.infodota.hero.fragment.guide.ItemPart;
import com.badr.infodota.hero.fragment.guide.TooltipPart;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 14:29
 */
public class GuidePagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private long heroId;
    private Guide guide;
    private List<GuideHolder> guideHolders = new ArrayList<GuideHolder>();

    public GuidePagerAdapter(FragmentManager fm, Context context, long heroId, Guide guide) {
        super(fm);
        this.context = context;
        this.heroId = heroId;
        this.guide = guide;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ItemPart itemPartFragment = ItemPart.newInstance(heroId, guide != null ? guide.getItemBuild() : null);
                guideHolders.add(itemPartFragment);
                return itemPartFragment;
            case 1:
                AbilityBuildPart abilityBuildFragment = AbilityBuildPart.newInstance(heroId, guide != null ? guide.getAbilityBuild() : null);
                guideHolders.add(abilityBuildFragment);
                return abilityBuildFragment;
            case 2:
                TooltipPart tooltipPartFragment = TooltipPart.newInstance(heroId, guide);
                guideHolders.add(tooltipPartFragment);
                return tooltipPartFragment;
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
                return context.getString(R.string.item_build);
            case 1:
                return context.getString(R.string.skill_build);
            default:
                return context.getString(R.string.tooltips);
        }
    }

    public void updateWith(Guide guide) {
        this.guide = guide;
        //notifyDataSetChanged();
        for (GuideHolder holder : guideHolders) {
            holder.updateWith(guide);
        }
    }
}
