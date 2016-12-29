package com.badr.infodota.quiz.fragment;

import android.util.Log;

import com.badr.infodota.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecentHighscoreFragment extends HighscoreFragment {

    private int typeHighScore;

    public RecentHighscoreFragment() {}

    public static RecentHighscoreFragment newInstance(int postion) {
        RecentHighscoreFragment fragment = new RecentHighscoreFragment();
        fragment.typeHighScore = postion;
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference ref= getDatabaseReference(mDatabase);

        //Value event listener for realtime data update
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.d("BINH", "onDataChange() called with: snapshot = [" + snapshot + "]");

                    //Getting the data from snapshot
                    //Person person = postSnapshot.getValue(Person.class);

                    //Adding it to a string
                   // String string = "Name: "+person.getName()+"\nAddress: "+person.getAddress()+"\n\n";

                    //Displaying it on textview
                   // textViewPersons.setText(string);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


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
        if(typeHighScore==0){
            recentPostsQuery = databaseReference.child("score_item")
                    .limitToFirst(100);
        }else if(typeHighScore==1){
            recentPostsQuery = databaseReference.child("score_hero")
                    .limitToFirst(100);
        }else{
            recentPostsQuery = databaseReference.child("score_random")
                    .limitToFirst(100);
        }
        // [END recent_posts_query]

        return recentPostsQuery;
    }

    @Override
    public DatabaseReference getDatabaseReference(DatabaseReference databaseReference) {
        DatabaseReference returnReference;
        if(typeHighScore==0){
            returnReference= databaseReference.child("score_item");
        }else if(typeHighScore==1){
            returnReference= databaseReference.child("score_hero");
        }else{
            returnReference= databaseReference.child("score_random");
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
