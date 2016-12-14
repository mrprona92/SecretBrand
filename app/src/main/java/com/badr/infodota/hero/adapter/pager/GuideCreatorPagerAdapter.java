package com.badr.infodota.hero.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.fragment.guide.GuideHolder;
import com.badr.infodota.hero.fragment.guide.edit.AbilityBuildPartEdit;
import com.badr.infodota.hero.fragment.guide.edit.ItemPartEdit;
import com.badr.infodota.hero.fragment.guide.edit.OnAfterEditListener;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Histler
 * Date: 14.02.14
 */
public class GuideCreatorPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private long heroId;
    private Guide guide;
    private List<GuideHolder> guideHolders = new ArrayList<GuideHolder>();

    public GuideCreatorPagerAdapter(FragmentManager fragmentManager, Context context, long heroId, Guide guide) {
        super(fragmentManager);
        this.context = context;
        this.guide = guide;
        this.heroId = heroId;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ItemPartEdit itemPartFragment = ItemPartEdit.newInstance(heroId, guide);
                guideHolders.add(itemPartFragment);
                return itemPartFragment;
            case 1:
                AbilityBuildPartEdit abilityBuildPartEdit = AbilityBuildPartEdit.newInstance(heroId, guide);
                guideHolders.add(abilityBuildPartEdit);
                return abilityBuildPartEdit;
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
                return context.getString(R.string.item_build);
            default:
                return context.getString(R.string.skill_build);
        }
    }

    public void updateWith(Guide guide) {
        this.guide = guide;
        for (GuideHolder holder : guideHolders) {
            holder.updateWith(guide);
        }
    }

    public void saveGuide() {
        for (GuideHolder guideHolder : guideHolders) {
            OnAfterEditListener listener = (OnAfterEditListener) guideHolder;
            listener.onSave();
        }
    }
}
