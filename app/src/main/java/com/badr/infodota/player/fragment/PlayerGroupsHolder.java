package com.badr.infodota.player.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.player.adapter.pager.PlayerGroupsPagerAdapter;

/**
 * User: ABadretdinov
 * Date: 04.02.14
 * Time: 19:48
 */
public class PlayerGroupsHolder extends Fragment {
    private PlayerGroupsPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_history_holder, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ((ListHolderActivity) getActivity()).getActionMenuView().setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initPager();
    }

    public PlayerGroupsPagerAdapter getAdapter() {
        return adapter;
    }

    private void initPager() {
        adapter = new PlayerGroupsPagerAdapter(getChildFragmentManager(), getActivity());

        ViewPager pager = (ViewPager) getView().findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }
}
