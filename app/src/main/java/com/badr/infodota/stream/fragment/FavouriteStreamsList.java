package com.badr.infodota.stream.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.stream.StreamUtils;
import com.badr.infodota.stream.adapter.TwitchStreamsAdapter;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.task.FavStreamsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 07.03.14
 * Time: 15:22
 */
public class FavouriteStreamsList extends TwitchMatchListHolder implements RequestListener<Stream.List> {
    FavStreamsLoadRequest request;
    private List<Stream> channels;
    private TwitchGamesAdapter holderAdapter;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    public static FavouriteStreamsList newInstance(TwitchGamesAdapter holderAdapter, List<Stream> channels) {
        FavouriteStreamsList fragment = new FavouriteStreamsList();
        fragment.setHolderAdapter(holderAdapter);
        fragment.setChannels(channels);
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            onRefresh();
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

    public void setHolderAdapter(TwitchGamesAdapter holderAdapter) {
        this.holderAdapter = holderAdapter;
    }

    public void setChannels(List<Stream> channels) {
        this.channels = channels != null ? channels : new ArrayList<Stream>();
    }

    @Override
    public void updateList(List<Stream> channels) {
        this.channels = channels;
        onRefresh();
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
    public void onRefresh() {
        setRefreshing(true);
        if (request != null) {
            mSpiceManager.cancel(request);
        }
        request = new FavStreamsLoadRequest(channels);
        mSpiceManager.execute(request, this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Stream.List streams) {
        setRefreshing(false);
        setAdapter(new TwitchStreamsAdapter(holderAdapter, streams));
    }
}
