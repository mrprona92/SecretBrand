package com.mrprona.dota2assitant.youtube;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.Video;
import com.mrprona.dota2assitant.BuildConfig;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.youtube.model.PlaylistVideos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * <p/>
 * YouTubeRecyclerViewFragment contains a RecyclerView that shows a list of YouTube video cards.
 * <p/>
 */
public class YouTubeRecyclerPlaylistFragment extends SCBaseFragment {
    // the fragment initialization parameter
    private static final String ARG_YOUTUBE_PLAYLIST_IDS = "YOUTUBE_PLAYLIST_IDS";
    public static final String ARG_PLAYLIST_IDS = "ARRAY_PLAYLIST_IDS";
    private static final int SPINNER_ITEM_LAYOUT_ID = android.R.layout.simple_spinner_item;
    private static final int SPINNER_ITEM_DROPDOWN_LAYOUT_ID = android.R.layout.simple_spinner_dropdown_item;

    private String[] mPlaylistIds;
    private ArrayList<String> mPlaylistTitles;
    private RecyclerView mRecyclerView;
    private PlaylistVideos mPlaylistVideos;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner mPlaylistSpinner;
    private PlaylistCardAdapter mPlaylistCardAdapter;
    private YouTube mYouTubeDataApi;
    private InterstitialAd mInterstitialAd;

    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param youTubeDataApi
     * @param playlistIds    A String array of YouTube Playlist IDs
     * @return A new instance of fragment YouTubeRecyclerViewFragment.
     */
    public static YouTubeRecyclerPlaylistFragment newInstance(YouTube youTubeDataApi, String[] playlistIds) {
        YouTubeRecyclerPlaylistFragment fragment = new YouTubeRecyclerPlaylistFragment();
        fragment.setYouTubeDataApi(youTubeDataApi);
        return fragment;
    }

    public YouTubeRecyclerPlaylistFragment() {
        // Required empty public constructor
    }

    public void setYouTubeDataApi(YouTube api) {
        mYouTubeDataApi = api;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mYouTubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                .setApplicationName(getResources().getString(R.string.app_name))
                .build();

        mInterstitialAd = new InterstitialAd(mActivity);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0").build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0").build());
            }

        });

        if (getArguments() != null) {
            mPlaylistIds = getArguments().getStringArray(ARG_PLAYLIST_IDS);
        }

        // start fetching the playlist titles
        new GetPlaylistTitlesAsyncTask(mYouTubeDataApi) {
            @Override
            protected void onPostExecute(PlaylistListResponse playlistListResponse) {
                // if we didn't receive a response for the playlist titles, then there's nothing to update
                if (playlistListResponse == null)
                    return;

                mPlaylistTitles = new ArrayList();
                for (com.google.api.services.youtube.model.Playlist playlist : playlistListResponse.getItems()) {
                    mPlaylistTitles.add(playlist.getSnippet().getTitle());
                }
                // update the spinner adapter with the titles of the playlists
                if(mActivity==null){
                    return;
                }
                ArrayAdapter<List<String>> spinnerAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_spinner_item, mPlaylistTitles);
                spinnerAdapter.setDropDownViewResource(SPINNER_ITEM_DROPDOWN_LAYOUT_ID);
                mPlaylistSpinner.setAdapter(spinnerAdapter);
            }
        }.execute(mPlaylistIds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // set the Picasso debug indicator only for debug builds
        Picasso.with(getActivity()).setIndicatorsEnabled(BuildConfig.DEBUG);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.youtube_recycler_view_channel_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.youtube_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        Resources resources = getResources();
        if (resources.getBoolean(R.bool.isTablet)) {
            // use a staggered grid layout if we're on a large screen device
            mLayoutManager = new StaggeredGridLayoutManager(resources.getInteger(R.integer.columns), StaggeredGridLayoutManager.VERTICAL);
        } else {
            // use a linear layout on phone devices
            mLayoutManager = new LinearLayoutManager(getActivity());
        }

        mRecyclerView.setLayoutManager(mLayoutManager);

        mPlaylistSpinner = (Spinner) rootView.findViewById(R.id.youtube_playlist_spinner);

        return rootView;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.ic_menu4_youtube_title;
    }

    @Override
    public String getToolbarTitleString() {
        return "";
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

        // if we have a playlist in our retained fragment, use it to populate the UI
        if (mPlaylistVideos != null) {
            // reload the UI with the existing playlist.  No need to fetch it again
            reloadUi(mPlaylistVideos, false);
        } else {
            // otherwise create an empty playlist using the first item in the playlist id's array
            mPlaylistVideos = new PlaylistVideos(mPlaylistIds[0]);
            // and reload the UI with the selected playlist and kick off fetching the playlist content
            reloadUi(mPlaylistVideos, true);
        }

        ArrayAdapter<List<String>> spinnerAdapter;
        // if we don't have the playlist titles yet
        if (mPlaylistTitles == null || mPlaylistTitles.isEmpty()) {
            // initialize the spinner with the playlist ID's so that there's something in the UI until the GetPlaylistTitlesAsyncTask finishes
            spinnerAdapter = new ArrayAdapter(getContext(), SPINNER_ITEM_LAYOUT_ID, Arrays.asList(mPlaylistIds));
        } else {
            // otherwise use the playlist titles for the spinner
            spinnerAdapter = new ArrayAdapter(getContext(), SPINNER_ITEM_LAYOUT_ID, mPlaylistTitles);
        }

        spinnerAdapter.setDropDownViewResource(SPINNER_ITEM_DROPDOWN_LAYOUT_ID);
        mPlaylistSpinner.setAdapter(spinnerAdapter);

        // set up the onItemSelectedListener for the spinner
        mPlaylistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // reload the UI with the playlist video list of the selected playlist
                mPlaylistVideos = new PlaylistVideos(mPlaylistIds[position]);
                reloadUi(mPlaylistVideos, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void reloadUi(final PlaylistVideos playlistVideos, boolean fetchPlaylist) {
        // initialize the cards adapter
        initCardAdapter(playlistVideos);

        if (fetchPlaylist) {
            // start fetching the selected playlistVideos contents
            new GetPlaylistAsyncTask(mYouTubeDataApi) {
                @Override
                public void onPostExecute(Pair<String, List<Video>> result) {
                    handleGetPlaylistResult(playlistVideos, result);
                }
            }.execute(playlistVideos.playlistId, playlistVideos.getNextPageToken());
        }
    }

    private void initCardAdapter(final PlaylistVideos playlistVideos) {
        // create the adapter with our playlistVideos and a callback to handle when we reached the last item
        mPlaylistCardAdapter = new PlaylistCardAdapter(playlistVideos, new PlaylistCardAdapter.LastItemReachedListener() {
            @Override
            public void onLastItem(int position, String nextPageToken) {
                new GetPlaylistAsyncTask(mYouTubeDataApi) {
                    @Override
                    public void onPostExecute(Pair<String, List<Video>> result) {
                        handleGetPlaylistResult(playlistVideos, result);
                    }
                }.execute(playlistVideos.playlistId, playlistVideos.getNextPageToken());
            }
        }, new PlaylistCardAdapter.ClickOnItemListener() {
            @Override
            public void onItemClick(int position) {
                int rand = new Random().nextInt(2);
                if (rand % 2 == 0) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mPlaylistCardAdapter);
    }

    private void handleGetPlaylistResult(PlaylistVideos playlistVideos, Pair<String, List<Video>> result) {
        if (result == null) return;
        final int positionStart = playlistVideos.size();
        playlistVideos.setNextPageToken(result.first);
        playlistVideos.addAll(result.second);
        mPlaylistCardAdapter.notifyItemRangeInserted(positionStart, result.second.size());
    }


}
