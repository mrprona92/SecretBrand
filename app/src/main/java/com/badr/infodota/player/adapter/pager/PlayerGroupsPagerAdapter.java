package com.badr.infodota.player.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.fragment.GroupPlayersList;
import com.badr.infodota.player.fragment.SearchedPlayersList;

/**
 * User: ABadretdinov
 * Date: 04.02.14
 * Time: 19:56
 */
public class PlayerGroupsPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private SparseArray<GroupPlayersList> mGroupMap = new SparseArray<>();

    public PlayerGroupsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.match_history_title);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new SearchedPlayersList();
            case 1:
                GroupPlayersList friend = mGroupMap.get(i);
                if (friend == null) {
                    friend = GroupPlayersList.newInstance(Unit.Groups.FRIEND);
                    mGroupMap.put(i, friend);
                }
                return friend;
            default:
            case 2:
                GroupPlayersList pro = mGroupMap.get(i);
                if (pro == null) {
                    pro = GroupPlayersList.newInstance(Unit.Groups.PRO);
                    mGroupMap.put(i, pro);
                }
                return pro;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mGroupMap.remove(position);
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

    public void update() {
        for (int index = 0, size = mGroupMap.size(); index < size; index++) {
            int key = mGroupMap.keyAt(index);
            mGroupMap.get(key).onRefresh();
        }
    }
}
