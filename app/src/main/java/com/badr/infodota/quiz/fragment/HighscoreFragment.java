package com.badr.infodota.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.SCBaseFragment;
import com.badr.infodota.quiz.holder.HighscoreHolder;
import com.badr.infodota.quiz.model.HighScore;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public abstract class HighscoreFragment extends SCBaseFragment {
    private static final String TAG = "HighscoreFragment";
    // [START define_database_reference]
    public DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<HighScore, HighscoreHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public HighscoreFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_highscore, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.recyclerHighscore);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase).orderByChild("score");
        mAdapter = new FirebaseRecyclerAdapter<HighScore, HighscoreHolder>(HighScore.class, R.layout.highscore_row,
                HighscoreHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final HighscoreHolder viewHolder, final HighScore model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

              /*  viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });*/

                // Determine if the current user has liked this post and set UI accordingly
               /* if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToHighscore(model);
            }
        };
        mRecycler.setAdapter(mAdapter);
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

