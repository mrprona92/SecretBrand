package com.mrprona.dota2assitant.trackdota.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.fragment.UpdatableListFragment;
import com.mrprona.dota2assitant.base.util.Refresher;
import com.mrprona.dota2assitant.base.util.Updatable;
import com.mrprona.dota2assitant.trackdota.activity.TrackdotaGameInfoActivity;
import com.mrprona.dota2assitant.trackdota.adapter.LiveGamesAdapter;
import com.mrprona.dota2assitant.trackdota.api.game.EnhancedGame;
import com.mrprona.dota2assitant.trackdota.api.game.EnhancedMatch;

import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 18:40
 */
public class LiveGamesList extends UpdatableListFragment implements Updatable<List<EnhancedMatch>> {
    private Refresher refresher;

    public static LiveGamesList newInstance(Refresher refresher) {
        LiveGamesList fragment = new LiveGamesList();
        fragment.refresher = refresher;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.pinned_section_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getToolbarTitle() {
        return 0;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }

    @Override
    protected int getViewContent() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListener() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new LiveGamesAdapter(getActivity(), null));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Object object = getListAdapter().getItem(position);
        if (object instanceof EnhancedGame) {
            EnhancedGame game = (EnhancedGame) object;
            Intent intent = new Intent(getActivity(), TrackdotaGameInfoActivity.class);
            intent.putExtra("id", game.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        if (refresher != null) {
            setRefreshing(true);
            refresher.onRefresh();
        }

    }

    @Override
    public void onUpdate(List<EnhancedMatch> entity) {
        setRefreshing(false);
        setListAdapter(new LiveGamesAdapter(getActivity(), entity));
    }
}
