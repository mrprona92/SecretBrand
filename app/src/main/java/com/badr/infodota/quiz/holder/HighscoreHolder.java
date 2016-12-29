package com.badr.infodota.quiz.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;
import com.badr.infodota.quiz.model.HighScore;

/**
 * Created by ABadretdinov
 * 17.12.2014
 * 18:39
 */
public class HighscoreHolder extends RecyclerView.ViewHolder  {
    public TextView lblRanking;
    public TextView lblName;
    public ImageView imgFlag;
    public TextView lblTextFlag;
    public TextView lblScore;
    public TextView lblDate;

    public HighscoreHolder(View itemView) {
        super(itemView);
        lblRanking = (TextView) itemView.findViewById(R.id.lblRanking);
        lblName= (TextView) itemView.findViewById(R.id.lblName);
        imgFlag = (ImageView) itemView.findViewById(R.id.imgFlag);
        lblTextFlag = (TextView) itemView.findViewById(R.id.lblTextFlag);
        lblScore = (TextView) itemView.findViewById(R.id.lblScore);
        lblDate = (TextView) itemView.findViewById(R.id.lblDate);
    }

    public void bindToHighscore(HighScore post) {
        lblRanking.setText(post.ranking);
        lblName.setText(post.name);
        //imgFlag.setText(post.author);
        //lblTextFlag.setText(String.valueOf(post.starCount));
        lblScore.setText(post.score+"");
        lblDate.setText(post.date);
    }

}
