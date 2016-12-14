package com.badr.infodota.trackdota.fragment.game;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.UpdatableListFragment;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.base.util.Updatable;
import com.badr.infodota.trackdota.adapter.LogsAdapter;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.live.LiveGame;

/**
 * Created by ABadretdinov
 * 17.04.2015
 * 14:21
 */
public class LogList extends UpdatableListFragment implements Updatable<Pair<CoreResult, LiveGame>> {
    private Refresher refresher;
    private LiveGame liveGame;
    private CoreResult coreResult;

    public static LogList newInstance(Refresher refresher, CoreResult coreResult, LiveGame liveGame) {
        LogList fragment = new LogList();
        fragment.refresher = refresher;
        fragment.coreResult = coreResult;
        fragment.liveGame = liveGame;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.pinned_section_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRefreshing(false);
        setLogs();
    }

    @Override
    public void onRefresh() {
        if (refresher != null) {
            setRefreshing(true);
            refresher.onRefresh();
        }
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        setRefreshing(false);
        coreResult = entity.first;
        liveGame = entity.second;
        setLogs();
    }

    private void setLogs() {
        if (liveGame != null && coreResult != null) {
            setListAdapter(new LogsAdapter(liveGame.getLog(), coreResult.getRadiant(), coreResult.getDire()));
        } else {
            setListAdapter(new LogsAdapter(null, null, null));
        }
    }
}
