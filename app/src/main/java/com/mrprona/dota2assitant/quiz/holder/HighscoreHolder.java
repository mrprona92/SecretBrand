package com.mrprona.dota2assitant.quiz.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.heetch.countrypicker.Utils;

import java.util.Locale;

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

    public void bindToHighscore(HighScore post,int position) {
        lblRanking.setText(position+1+"");
        lblName.setText(post.name);

        if(post.getState()!=null){
            String drawableName = post.getState().toLowerCase(Locale.ENGLISH) + "_flag";
            imgFlag.setImageResource(Utils.getMipmapResId(ListHolderActivity.getAppContext(), drawableName));
            lblTextFlag.setText(new Locale(ListHolderActivity.getAppContext().getResources().getConfiguration().locale.getLanguage(),post.getState()).getDisplayCountry() +"");
        }
        //imgFlag.setText(post.author);
        //lblTextFlag.setText(String.valueOf(post.starCount));

        lblScore.setText(post.score+"");

        lblDate.setText(post.date.substring(0,10));
    }

}
