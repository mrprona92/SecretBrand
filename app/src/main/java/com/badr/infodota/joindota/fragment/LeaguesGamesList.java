package com.badr.infodota.joindota.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.fragment.UpdatableListFragment;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.base.view.EndlessScrollListener;
import com.badr.infodota.joindota.activity.LeagueGameActivity;
import com.badr.infodota.joindota.adapter.LeaguesGamesAdapter;
import com.badr.infodota.joindota.api.MatchItem;
import com.badr.infodota.joindota.task.JoindotaMatchesLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:30
 */
public class LeaguesGamesList extends UpdatableListFragment implements RequestListener<MatchItem.List> {
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    private String mExtraParams;

    public static LeaguesGamesList newInstance(String extraParams) {
        LeaguesGamesList fragment = new LeaguesGamesList();
        fragment.mExtraParams = extraParams;
        return fragment;
    }


    //TEST

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            loadGames(1);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.pinned_section_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setListAdapter(new LeaguesGamesAdapter(getActivity(), null));
        getListView().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadGames(page);
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        MatchItem item = ((LeaguesGamesAdapter) getListAdapter()).getItem(position);
        if (!item.isSection()) {
            Intent intent = new Intent(getActivity(), LeagueGameActivity.class);
            intent.putExtra("matchItem", item);
            startActivity(intent);
        }
    }

    private void loadGames(final int page) {
        Activity activity = getActivity();
        if (activity != null) {
            setRefreshing(true);
            mSpiceManager.execute(new JoindotaMatchesLoadRequest(activity.getApplicationContext(), mExtraParams, page), this);
        }
    }

    @Override
    public void onRefresh() {
        loadGames(1);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(MatchItem.List matchItems) {
        setRefreshing(false);
        ((LeaguesGamesAdapter) getListAdapter()).addMatchItems(matchItems);
    }


}
