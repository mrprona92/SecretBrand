package com.mrprona.dota2assitant.youtube;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.configs.ScreenIDs;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.youtube.model.ChannelInfo;

import java.util.ArrayList;
import java.util.List;

import static com.mrprona.dota2assitant.youtube.YouTubeRecyclerPlaylistFragment.ARG_PLAYLIST_IDS;


/**
 * User: ABadretdinov
 * Date: 16.01.14
 * Time: 15:57
 */
public class YoutubeFragment extends SCBaseFragment implements ChannelAdapter.OnClickImageChannel {


    private List<ChannelInfo> mListChannel = new ArrayList<>();

    private ChannelAdapter mChannelAdapter;

    private RecyclerView mRecycleView;

    private static final String[] YOUTUBE_PLAYLISTS = {
            "UCooVYzDxdwTtGYAkcPmOgOw"
    };

    private ArrayList<String[]> listChannel = new ArrayList<>();

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();


    public static YoutubeFragment newInstance() {
        YoutubeFragment fragment = new YoutubeFragment();
        return fragment;
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
        return R.layout.recycler_content_youtube;
    }

    @Override
    protected void initUI() {
        mRecycleView = (RecyclerView) findViewById(R.id.list);
        mChannelAdapter = new ChannelAdapter(getContext());
        mChannelAdapter.setOnItemClickListener(this);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mChannelAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setNestedScrollingEnabled(false);
        String[] shortfilm = {"PLQK8cHMx8f7InCZrOnleZfK7ouh62oXRW", "PL7OZaS8h-uRZu9I6CcEIraAJx8ZYvOY_L", "PLbV51Efs_tCzgHs6K46UFUSaKKXIqtf6O"};
        String[] wtf = {"PLzyMmqKNxWSs9AX7Gzm1IUhhLSOhGwBv0"};
        String[] fails = {"PLE14F2057980335BE"};
        listChannel.add(shortfilm);
        listChannel.add(wtf);
        listChannel.add(fails);
        prepareChannels();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    private void prepareChannels() {
        int[] covers = new int[]{
                R.drawable.channel_introduce,
                R.drawable.channel_wtf,
                R.drawable.channel_fail
        };
        ChannelInfo a = new ChannelInfo("Dota 2 Short Film Contest 2017", "Dota 2 Short Film Contest 2017", covers[0], "ABC");
        ChannelInfo b = new ChannelInfo("Dota 2 WTF Moments", "Dota Watafak", covers[1], "ABC");
        ChannelInfo c = new ChannelInfo("Dota 2 Fails of the Week", "DotaCinema", covers[2], "ABC");

        mListChannel.add(a);
        mListChannel.add(b);
        mListChannel.add(c);
        mChannelAdapter.setListAdapter(mListChannel);
    }


    @Override
    public void OnClickImage(View v, int position) {
      /*  /if (position == 1) {
            Bundle mBundle = new Bundle();
            mBundle.putString(ARG_PLAYLIST_IDS, YOUTUBE_PLAYLISTS[0]);
            openScreen(ScreenIDs.ScreenTab.LIVE, YouTubeRecyclerViewFragment.class, mBundle, true, true);
        } else {*/
        Bundle mBundle = new Bundle();
        mBundle.putStringArray(ARG_PLAYLIST_IDS, listChannel.get(position));
        openScreen(ScreenIDs.ScreenTab.LIVE, YouTubeRecyclerPlaylistFragment.class, mBundle, true, true);
        //}
    }
}
