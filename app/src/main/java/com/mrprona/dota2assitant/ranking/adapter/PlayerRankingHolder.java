package com.mrprona.dota2assitant.ranking.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.OnItemClickListener;
import com.mrprona.dota2assitant.base.adapter.holder.BaseViewHolder;

/**
 * Created by BinhTran on 3/15/17.
 */

public class PlayerRankingHolder extends BaseViewHolder {
    public TextView rank;
    public TextView name;
    public ImageView image;
    public TextView scoreranking;
    public ImageView rankingchange;

    public PlayerRankingHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        rank= (TextView) itemView.findViewById(R.id.lblRanking);
        name = (TextView) itemView.findViewById(R.id.lblTeamName);
        image = (ImageView) itemView.findViewById(R.id.imgFlag);
        scoreranking = (TextView) itemView.findViewById(R.id.lblNumberRanking);
        rankingchange = (ImageView) itemView.findViewById(R.id.imgRankingChange);
    }
}