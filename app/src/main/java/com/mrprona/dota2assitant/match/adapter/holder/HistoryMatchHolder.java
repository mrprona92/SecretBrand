package com.mrprona.dota2assitant.match.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.OnItemClickListener;
import com.mrprona.dota2assitant.base.adapter.holder.BaseViewHolder;

/**
 * Created by Badr on 22.03.2015.
 */
public class HistoryMatchHolder extends BaseViewHolder {
    public ImageView heroImg;
    public TextView gameStartTime;
    public TextView heroName;
    public TextView gameType;

    public HistoryMatchHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        heroImg = (ImageView) itemView.findViewById(R.id.hero_img);
        heroName = (TextView) itemView.findViewById(R.id.hero_name);
        gameStartTime = (TextView) itemView.findViewById(R.id.game_start_time);
        gameType = (TextView) itemView.findViewById(R.id.game_type);
    }
}
