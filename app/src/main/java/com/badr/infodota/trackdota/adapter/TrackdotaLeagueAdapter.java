package com.badr.infodota.trackdota.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.adapter.holder.TrackdotaLeagueHolder;
import com.badr.infodota.trackdota.api.game.League;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 12:34
 */
public class TrackdotaLeagueAdapter extends BaseRecyclerAdapter<League, TrackdotaLeagueHolder> {
    private String viewers;
    private String matches;

    public TrackdotaLeagueAdapter(Context context, List<League> data) {
        super(data);
        viewers = context.getString(R.string.viewers_);
        matches = context.getString(R.string.matches_);
    }

    @Override
    public TrackdotaLeagueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trackdota_league_row, parent, false);
        return new TrackdotaLeagueHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(TrackdotaLeagueHolder holder, int position) {
        League entity = getItem(position);
        holder.title.setText(entity.getName());
        holder.description.setText(entity.getDescription());
        Context context = holder.title.getContext();
        if (entity.isHasImage()) {
            Glide.with(context).load(TrackdotaUtils.getLeagueImageUrl(entity.getId())).placeholder(R.drawable.default_img).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.empty_item);
        }

        holder.viewers.setText(MessageFormat.format(viewers, entity.getViews()));
        holder.matches.setText(MessageFormat.format(matches, entity.getMatchCount()));
    }
}
