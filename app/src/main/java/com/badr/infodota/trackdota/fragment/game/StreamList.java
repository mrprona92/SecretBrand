package com.badr.infodota.trackdota.fragment.game;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.base.util.Updatable;
import com.badr.infodota.stream.StreamUtils;
import com.badr.infodota.stream.adapter.TwitchStreamsAdapter;
import com.badr.infodota.stream.adapter.holder.StreamHolder;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.fragment.TwitchGamesAdapter;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.live.LiveGame;
import com.badr.infodota.trackdota.task.TrackdotaStreamsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by Badr on 18.04.2015.
 */
public class StreamList extends UpdatableRecyclerFragment<Stream, StreamHolder> implements RequestListener<Stream.List>, TwitchGamesAdapter, Updatable<Pair<CoreResult, LiveGame>> {
    public static final int PLAYER_TYPE = 1403;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private CoreResult coreResult;
    private Refresher refresher;

    public static StreamList newInstance(Refresher refresher, CoreResult coreResult) {
        StreamList fragment = new StreamList();
        fragment.refresher = refresher;
        fragment.coreResult = coreResult;
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            Activity activity = getActivity();
            if (activity != null) {
                mSpiceManager.start(activity);
                if (coreResult != null) {
                    mSpiceManager.execute(new TrackdotaStreamsLoadRequest(activity.getApplicationContext(), coreResult.getStreams()), this);
                }
            }
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
    public void onRefresh() {
        if (refresher != null) {
            setRefreshing(true);
            refresher.onRefresh();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (Build.VERSION.SDK_INT > 14) {
            int currentPlayer = preferences.getInt("player_type", 0);
            MenuItem playerType = menu.add(1, PLAYER_TYPE, 1, getResources().getStringArray(R.array.player_types)[currentPlayer]);
            MenuItemCompat.setShowAsAction(playerType, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        } else {
            preferences.edit().putInt("player_type", 1).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == PLAYER_TYPE) {
            final MenuItem player = item;
            PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(item.getItemId()));
            final Menu menu = popup.getMenu();
            String[] playerTypes = getResources().getStringArray(R.array.player_types);
            for (int i = 0; i < playerTypes.length; i++) {
                menu.add(2, i, 0, playerTypes[i]);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    player.setTitle(menuItem.getTitle());
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    preferences.edit().putInt("player_type", menuItem.getItemId()).commit();
                    return true;
                }
            });
            popup.show();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onItemClick(View view, int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Stream stream = getAdapter().getItem(position);
        switch (preferences.getInt("player_type", 0)) {
            case 0: {
                StreamUtils.openActivity(getActivity(), stream);
                break;
            }
            case 1: {
                StreamUtils.openInSpecialApp(getActivity(), stream);
                break;
            }
            default: {
                StreamUtils.openInVideoStreamApp(getActivity(), stream);
            }
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestSuccess(Stream.List streams) {
        setRefreshing(false);
        TwitchStreamsAdapter adapter = new TwitchStreamsAdapter(this, streams);
        setAdapter(adapter);
    }

    @Override
    public void updateList() {
        onRefresh();
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        coreResult = entity.first;
        Activity activity = getActivity();
        if (activity != null) {
            setRefreshing(true);
            if (coreResult != null) {
                mSpiceManager.execute(new TrackdotaStreamsLoadRequest(activity.getApplicationContext(), coreResult.getStreams()), this);
            }
        }
    }
}
