package com.badr.infodota.trackdota.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 12:35
 */
public class TrackdotaLeagueHolder extends BaseViewHolder {
    public ImageView image;
    public TextView title;
    public TextView description;
    public TextView viewers;
    public TextView matches;

    public TrackdotaLeagueHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        image = (ImageView) itemView.findViewById(R.id.league_logo);
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        viewers = (TextView) itemView.findViewById(R.id.viewers);
        matches = (TextView) itemView.findViewById(R.id.matches);
    }
}
