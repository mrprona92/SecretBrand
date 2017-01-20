package com.mrprona.dota2assitant.player.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.BaseRecyclerAdapter;
import com.mrprona.dota2assitant.base.util.SteamUtils;
import com.mrprona.dota2assitant.player.adapter.holder.PlayerCommonStatHolder;
import com.mrprona.dota2assitant.player.api.PlayerCommonStat;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ABadretdinov
 * 20.03.2015
 * 18:06
 */
public class PlayerCommonStatsAdapter extends BaseRecyclerAdapter<PlayerCommonStat, PlayerCommonStatHolder> {
    private String[] localizedWinLose;

    public PlayerCommonStatsAdapter(Context context, List<PlayerCommonStat> data) {
        super(data);
        localizedWinLose = new String[]{context.getString(R.string.win), context.getString(R.string.lost)};
    }

    @Override
    public PlayerCommonStatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_common_stats_row, parent, false);
        return new PlayerCommonStatHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(PlayerCommonStatHolder holder, int position) {
        PlayerCommonStat entity = getItem(position);
        holder.header.setText(entity.getHeader());
        Context context = holder.header.getContext();
        Glide
                .with(context)
                .load(SteamUtils.getHeroFullImage(entity.getHero().getDotaId()))
                .into(holder.heroImg);
        holder.heroName.setText(entity.getHero().getLocalizedName());

        int color = entity.isWon() ? Color.GREEN : Color.RED;
        holder.winLoose.setText(localizedWinLose[entity.isWon() ? 0 : 1]);
        holder.winLoose.setTextColor(color);

        holder.gameStartTime.setText(entity.getDateTime());
        holder.result.setText(entity.getResult());
    }
}
