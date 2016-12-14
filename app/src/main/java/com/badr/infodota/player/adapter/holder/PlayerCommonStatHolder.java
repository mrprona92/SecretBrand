package com.badr.infodota.player.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 20.03.2015
 * 17:53
 */
public class PlayerCommonStatHolder extends BaseViewHolder {

    public TextView header;
    public ImageView heroImg;
    public TextView heroName;
    public TextView winLoose;
    public TextView gameStartTime;
    public TextView result;

    public PlayerCommonStatHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        header = (TextView) itemView.findViewById(R.id.header);
        heroImg = (ImageView) itemView.findViewById(R.id.hero_img);
        heroName = (TextView) itemView.findViewById(R.id.hero_name);
        winLoose = (TextView) itemView.findViewById(R.id.win_lose);
        gameStartTime = (TextView) itemView.findViewById(R.id.game_start_time);
        result = (TextView) itemView.findViewById(R.id.result);
    }
}
