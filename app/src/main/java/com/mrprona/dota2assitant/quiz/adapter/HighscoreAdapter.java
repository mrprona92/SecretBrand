package com.mrprona.dota2assitant.quiz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.quiz.holder.HighscoreHolder;
import com.mrprona.dota2assitant.quiz.model.HighScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Matteo on 24/08/2015.
 */
public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreHolder> {
    private ArrayList<HighScore> items;
    private Context mContext;

    public HighscoreAdapter(ArrayList<HighScore> mItems,Context mListHolder) {
        this.mContext= mListHolder;
        this.items=mItems;
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                items.clear();
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    HighScore menu = postSnapshot.getValue(HighScore.class);
//                    items.add(menu);
//                }
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
    }

    public void setItems(ArrayList<HighScore> listItem){
        items.clear();
        items.addAll(listItem);
        Collections.sort(items, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore bean1, HighScore bean2) {
                long value = bean2.getScore() - bean1.getScore();
                return value == 0 ? 0 : value > 0 ? 1 : -1;
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public HighscoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.highscore_row, parent, false);
        return new HighscoreHolder(view);
    }

    @Override
    public void onBindViewHolder(HighscoreHolder holder, int position) {
        HighScore item = items.get(position);
        holder.bindToHighscore(item,position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


  };