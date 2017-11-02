package com.mrprona.dota2assitant.ranking.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Spinner;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.base.fragment.SearchableFragment;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;
import com.mrprona.dota2assitant.ranking.adapter.RankingPlayerAdapter;
import com.mrprona.dota2assitant.ranking.adapter.RankingTeamAdapter;
import com.mrprona.dota2assitant.ranking.service.RankingServiceImpl;
import com.mrprona.dota2assitant.ranking.task.PlayerRankingLoadRequest;
import com.mrprona.dota2assitant.ranking.task.TeamRankingLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlayerRankingFragment extends SCBaseFragment implements SearchableFragment, RequestListener {
    private static final String TAG = "HighscoreFragment";
    // [START define_database_reference]
    // [END define_database_reference]

    private RankingPlayerAdapter mAdapter;
    private List<PlayerRanking> mPlayerRanking;
    private RankingServiceImpl mRankingService;

    @BindView(R.id.recyclerRankingPlayer)
    RecyclerView recyclerRankingPlayer;

    @BindView(R.id.spinnerTypeRanking)
    Spinner spinnerTypeRanking;

    ProgressDialog mProgressDialog;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    private boolean isFirstTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // [START create_database_reference]
        // [END create_database_reference]
        isFirstTime = true;

        if (mView == null) {
            mView = inflater.inflate(getViewContent(), container, false);
            mUnbinder = ButterKnife.bind(this, mView);
            mIsViewInitialized = false;
        } else {
            mIsViewInitialized = true;
            if (mView.getParent() != null) {
                ((ViewGroup) mView.getParent()).removeView(mView);
            }
        }
        if (!mIsViewInitialized) {
            onInitializeView();
            mIsViewInitialized = true;
        }
        return mView;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.team_ranking;
    }

    @Override
    public String getToolbarTitleString() {
        return "TEAM RANKINGS";
    }


    public PlayerRankingFragment() {
    }


    @Override
    protected int getViewContent() {
        return R.layout.fragment_rankingplayer;
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
    public void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerRankingPlayer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerRankingPlayer.setHasFixedSize(false);
        recyclerRankingPlayer.setNestedScrollingEnabled(false);

        spinnerTypeRanking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if (!isFirstTime) {
                if (mActivity != null) {
                    mActivity.startMyTask(new JsoupListView());
                }
                // }
                //isFirstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set up Layout Manager, reverse layout


          /*  mAdapter = new FirebaseRecyclerAdapter<HighScore, HighscoreHolder>(HighScore.class, R.layout.highscore_row,
                HighscoreHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final HighscoreHolder viewHolder, final HighScore model, final int position) {


                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

              *//*  viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });*//*

                // Determine if the current user has liked this post and set UI accordingly
               *//* if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*//*

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                if(!maxTrue){
                    maxValue=position;
                    maxTrue=true;
                }
                viewHolder.bindToHighscore(model,maxValue-position);
            }
        };
        mRecycler.setAdapter(mAdapter);*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    String mSearchString;

    @Override
    public void onTextSearching(String text) {
        if (!TextUtils.isEmpty(mSearchString) || !TextUtils.isEmpty(text)) {
            this.mSearchString = text;
        }
        PlayerRankingFilter();
    }

    @SuppressWarnings("unchecked")
    private void PlayerRankingFilter() {
        Activity activity = getActivity();
        if (activity != null) {
            mSpiceManager.execute(new PlayerRankingLoadRequest(activity.getApplicationContext(), null, mSearchString), this);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {

    }

    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof PlayerRanking.List) {
            PlayerRanking.List result = (PlayerRanking.List) o;
            RankingPlayerAdapter adapter = new RankingPlayerAdapter(result, mActivity);
            recyclerRankingPlayer.setAdapter(adapter);
        }
    }

    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Object, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mRankingService = new RankingServiceImpl();
            mProgressDialog = new ProgressDialog(mActivity);
            // Set progressdialog title
            //mProgressDialog.setTitle("Android Jsoup ListView Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {
            // Create an array
            mPlayerRanking = mRankingService.getAllPlayerRanked(spinnerTypeRanking.getSelectedItemPosition());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Pass the results into ListViewAdapter.java
            mProgressDialog.dismiss();

            mAdapter = new RankingPlayerAdapter(mPlayerRanking, mAppContext);

            mAdapter.notifyDataSetChanged();

            recyclerRankingPlayer.setAdapter(mAdapter);

            Filter filter = mAdapter.getFilter();
            filter.filter(mSearchString);

            // Close the progressdialog

        }
    }

}

