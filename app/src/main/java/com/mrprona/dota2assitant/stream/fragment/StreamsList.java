package com.mrprona.dota2assitant.stream.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.mrprona.dota2assitant.stream.StreamUtils;
import com.mrprona.dota2assitant.stream.adapter.TwitchStreamsAdapter;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.task.TwitchStreamsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

/**
 * User: Histler
 * Date: 25.02.14
 */
public class StreamsList extends TwitchMatchListHolder implements RequestListener<Stream.List> {
    private TwitchGamesAdapter holderAdapter;
    private List<Stream> channels;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    public static StreamsList newInstance(TwitchGamesAdapter holderAdapter, List<Stream> channels) {
        StreamsList fragment = new StreamsList();
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
        this.channels = channels;
    }

    @Override
    public void updateList(List<Stream> channels) {
        this.channels = channels;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        mSpiceManager.execute(new TwitchStreamsLoadRequest(channels), this);
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
        TwitchStreamsAdapter adapter = new TwitchStreamsAdapter(holderAdapter, streams);
        setAdapter(adapter);
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
}
