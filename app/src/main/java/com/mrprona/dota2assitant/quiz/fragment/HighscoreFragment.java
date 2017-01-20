package com.mrprona.dota2assitant.quiz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment_HS;
import com.mrprona.dota2assitant.quiz.holder.HighscoreHolder;
import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.ButterKnife;


public abstract class HighscoreFragment extends SCBaseFragment_HS {
    private static final String TAG = "HighscoreFragment";
    // [START define_database_reference]
    public DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<HighScore, HighscoreHolder> mAdapter;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    private int maxValue=0;
    private boolean maxTrue=false;

    public HighscoreFragment() {}



    @Override
    protected int getViewContent() {
        return R.layout.fragment_highscore;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maxTrue=false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    // [START post_stars_transaction]
   /* private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }*/
    // [END post_stars_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

    public abstract DatabaseReference getDatabaseReference(DatabaseReference databaseReference);


}

