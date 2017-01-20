package com.mrprona.dota2assitant.quiz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.quiz.adapter.HighscoreAdapter;
import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;

public class RecentHighscoreFragment extends HighscoreFragment {

    private int typeHighScore;


    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private HighscoreAdapter mMyAdapter;

    public static RecentHighscoreFragment newInstance(int postion) {
        RecentHighscoreFragment fragment = new RecentHighscoreFragment();
        fragment.typeHighScore= postion;
        return fragment;
    }

    private DatabaseReference ref;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery;
        if (typeHighScore == 0) {
            recentPostsQuery = databaseReference.child("score_item")
                    .limitToFirst(100);
        } else if (typeHighScore == 1) {
            recentPostsQuery = databaseReference.child("score_hero")
                    .limitToFirst(100);
        } else {
            recentPostsQuery = databaseReference.child("score_random")
                    .limitToFirst(100);
        }
        // [END recent_posts_query]

        return recentPostsQuery;
    }

    @Override
    public DatabaseReference getDatabaseReference(DatabaseReference databaseReference) {
        DatabaseReference returnReference;
        if (typeHighScore == 0) {
            returnReference = databaseReference.child("score_item");
        } else if (typeHighScore == 1) {
            returnReference = databaseReference.child("score_hero");
        } else {
            returnReference = databaseReference.child("score_random");
        }
        return returnReference;
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
        return R.layout.fragment_highscore;
    }

    @Override
    protected void initUI() {
        mRecycler.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mManager);
        mMyAdapter =new HighscoreAdapter(items,getActivity());
        mRecycler.setAdapter(mMyAdapter);
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



    private LinearLayoutManager mManager;
    private ArrayList<HighScore> items= new ArrayList<>();



    @BindView(R.id.recyclerHighscore)
    RecyclerView mRecycler;

    @BindView(R.id.llProgress)
    LinearLayout mllProgress;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ref = getDatabaseReference(mDatabase);
        mQuery= getQuery(ref);
        mllProgress.setVisibility(View.VISIBLE);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HighScore> tempArray= new ArrayList<HighScore>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HighScore menu = postSnapshot.getValue(HighScore.class);
                    tempArray.add(menu);
                }
                mMyAdapter.setItems(tempArray);
                mllProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("BINH", "onCancelled() called with: databaseError = [" + databaseError + "]");
            }
        });


        //Value event listener for realtime data update

    }


    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
           // mComments = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
          //  mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
           // mComments = new ArrayList<HighScore>();
            //mAdapterKeys = new ArrayList<String>();
        }
    }


    // Saving the list of items and keys of the items on rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mMyAdapter.getItems()));
       // outState.putStringArrayList(SAVED_ADAPTER_KEYS, mMyAdapter.getKeys());
    }


  }
