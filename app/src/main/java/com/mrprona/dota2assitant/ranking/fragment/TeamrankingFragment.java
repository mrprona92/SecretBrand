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
import android.widget.Filter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment_HS;
import com.mrprona.dota2assitant.base.fragment.SearchableFragment;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.quiz.holder.HighscoreHolder;
import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.mrprona.dota2assitant.ranking.TeamRanking;
import com.mrprona.dota2assitant.ranking.adapter.RankingTeamAdapter;
import com.mrprona.dota2assitant.ranking.service.RankingServiceImpl;
import com.mrprona.dota2assitant.ranking.task.TeamRankingLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TeamrankingFragment extends SCBaseFragment implements SearchableFragment, RequestListener {
    private static final String TAG = "HighscoreFragment";
    // [START define_database_reference]
    // [END define_database_reference]

    private RankingTeamAdapter mAdapter;
    private List<TeamRanking> mTeamRanking;
    private RankingServiceImpl mRankingService;

    @BindView(R.id.recyclerRankingTeam)
    RecyclerView recyclerRankingTeam;
    ProgressDialog mProgressDialog;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // [START create_database_reference]
        // [END create_database_reference]
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


    public TeamrankingFragment() {
    }


    @Override
    protected int getViewContent() {
        return R.layout.fragment_rankingteam;
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

        if(mActivity!=null){
            mActivity.startMyTask(new JsoupListView());
        }

        recyclerRankingTeam.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerRankingTeam.setHasFixedSize(false);
        recyclerRankingTeam.setNestedScrollingEnabled(false);

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
        TeamRankingFilter();
    }

    @SuppressWarnings("unchecked")
    private void TeamRankingFilter() {
        Activity activity = getActivity();
        if (activity != null) {
            mSpiceManager.execute(new TeamRankingLoadRequest(activity.getApplicationContext(), null, mSearchString), this);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {

    }

    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof TeamRanking.List) {
            TeamRanking.List result = (TeamRanking.List) o;
            RankingTeamAdapter adapter = new RankingTeamAdapter(result, mActivity);
            recyclerRankingTeam.setAdapter(adapter);
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
            mTeamRanking = mRankingService.getAllTeamRanked();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Pass the results into ListViewAdapter.java
            mProgressDialog.dismiss();

            mAdapter = new RankingTeamAdapter(mTeamRanking, mAppContext);

            mAdapter.notifyDataSetChanged();

            recyclerRankingTeam.setAdapter(mAdapter);

            Filter filter = mAdapter.getFilter();
            filter.filter(mSearchString);

            // Close the progressdialog

        }
    }


}

