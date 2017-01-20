package com.mrprona.dota2assitant.quiz.holder;

/**
 * Created by BinhTran on 1/4/17.
 */

import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class HighScoreHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<HighScore> spacecrafts=new ArrayList<>();

    public HighScoreHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(HighScore spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }else {

            try
            {
                db.child("Spacecraft").push().setValue(spacecraft);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }

        }

        return saved;
    }

    //READ
    public ArrayList<HighScore> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //spacecrafts.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //spacecrafts.clear();
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return spacecrafts;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        HighScore name=dataSnapshot.getValue(HighScore.class);
        spacecrafts.add(name);

    }

}