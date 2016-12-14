package com.badr.infodota.match.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.match.adapter.holder.HistoryMatchHolder;
import com.badr.infodota.match.api.history.PlayerMatch;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * User: ABadretdinov
 * Date: 20.01.14
 * Time: 19:00
 */
public class HistoryMatchesAdapter extends BaseRecyclerAdapter<PlayerMatch, HistoryMatchHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd.MM.yyyy");

    public HistoryMatchesAdapter(List<PlayerMatch> matches) {
        super(matches);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        sdf.setTimeZone(tz);
    }

    public void addMatches(List<PlayerMatch> subMatches) {
        if (subMatches != null) {
            int was = getItemCount();
            getItems().addAll(subMatches);
            notifyItemRangeChanged(was, subMatches.size());
        }
    }

    @Override
    public HistoryMatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        return new HistoryMatchHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(HistoryMatchHolder holder, int position) {
        PlayerMatch entity = getItem(position);
        holder.gameStartTime.setText(sdf.format(entity.getGameTime()));
        int gameType = entity.getLobbyType();
        Context context = holder.gameType.getContext();
        if (gameType != -1) {
            holder.gameType.setText(context.getResources().getStringArray(R.array.lobby_types)[gameType]);
        }
        Hero hero = entity.getPlayer().getHero();
        Glide.with(context).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(holder.heroImg);
        holder.heroName.setText(hero.getLocalizedName());
    }
}
