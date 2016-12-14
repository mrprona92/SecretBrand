package com.badr.infodota.trackdota.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.api.game.League;
import com.badr.infodota.trackdota.api.game.Team;
import com.badr.infodota.trackdota.api.league.LeagueGame;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 17:26
 */
public class TrackdotaLeagueGamesAdapter extends BaseAdapter {
    private League mLeague;
    private List<LeagueGame> mData;

    public TrackdotaLeagueGamesAdapter(League league, List<LeagueGame> data) {
        super();
        mData = data != null ? data : new ArrayList<LeagueGame>();
        mLeague = league;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return mLeague;
        }
        return mData.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        final Context context = parent.getContext();
        if (position == 0) {
            LayoutInflater inflater = LayoutInflater.from(context);
            itemView = inflater.inflate(R.layout.trackdota_league_header, parent, false);
            ((TextView) itemView.findViewById(R.id.description)).setText(mLeague.getDescription());
            ((TextView) itemView.findViewById(R.id.viewers)).setText(MessageFormat.format(context.getString(R.string.viewers_), mLeague.getViews()));
            ((TextView) itemView.findViewById(R.id.matches)).setText(MessageFormat.format(context.getString(R.string.matches_), mLeague.getMatchCount()));

            if (mLeague.isHasImage()) {
                Glide.with(context).load(TrackdotaUtils.getLeagueImageUrl(mLeague.getId())).placeholder(R.drawable.empty_item).into((ImageView) itemView.findViewById(R.id.league_logo));
            } else {
                ((ImageView) itemView.findViewById(R.id.league_logo)).setImageResource(R.drawable.empty_item);
            }
        } else {
            Object object = getItem(position);
            LiveGameHolder holder;
            if (itemView == null || itemView.getTag() == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                itemView = inflater.inflate(R.layout.trackdota_live_game_row, parent, false);
                holder = new LiveGameHolder();
                holder.radiantLogo = (ImageView) itemView.findViewById(R.id.radiant_logo);
                holder.direLogo = (ImageView) itemView.findViewById(R.id.dire_logo);
                holder.radiantTag = (TextView) itemView.findViewById(R.id.radiant_tag);
                holder.direTag = (TextView) itemView.findViewById(R.id.dire_tag);
                holder.radiantScore = (TextView) itemView.findViewById(R.id.radiant_score);
                holder.direScore = (TextView) itemView.findViewById(R.id.dire_score);
                holder.scoreHolder = itemView.findViewById(R.id.score_holder);
                holder.radiantName = (TextView) itemView.findViewById(R.id.radiant_name);
                holder.direName = (TextView) itemView.findViewById(R.id.dire_name);
                holder.gameState = (TextView) itemView.findViewById(R.id.game_state);
                holder.streams = (TextView) itemView.findViewById(R.id.streams);
                holder.gameTime = (TextView) itemView.findViewById(R.id.game_time);
                itemView.setTag(holder);
            } else {
                holder = (LiveGameHolder) itemView.getTag();
            }
            LeagueGame game = (LeagueGame) object;
            ((View) holder.radiantTag.getParent()).setVisibility(View.GONE);
            holder.radiantName.setText(game.getRadiantTeamName());
            Team radiant = new Team();
            radiant.setId(game.getRadiantTeamId());
            Glide.with(context).load(TrackdotaUtils.getTeamImageUrl(radiant)).placeholder(R.drawable.empty_item).into(holder.radiantLogo);

            holder.radiantScore.setText(String.valueOf(game.getRadiantScore()));
            Team dire = new Team();
            dire.setId(game.getDireTeamId());
            holder.direName.setText(game.getDireTeamName());
            Glide.with(context).load(TrackdotaUtils.getTeamImageUrl(dire)).placeholder(R.drawable.empty_item).into(holder.direLogo);

            holder.direScore.setText(String.valueOf(game.getDireScore()));
            StringBuilder gameState = new StringBuilder(context.getString(R.string.game));
            gameState.append(" ");
            gameState.append(game.getRadiantSeriesScore() + game.getDireSeriesScore() + 1);
            gameState.append(" / ");
            gameState.append(context.getString(R.string.bo));
            gameState.append(1 + game.getSeriesType() * 2);
            if (game.getSeriesType() != 0) {
                gameState.append(" (");
                gameState.append(game.getRadiantSeriesScore());
                gameState.append(" - ");
                gameState.append(game.getDireSeriesScore());
                gameState.append(")");
            }
            holder.gameState.setText(gameState.toString());
            holder.streams.setVisibility(View.INVISIBLE);
            holder.scoreHolder.setVisibility(View.VISIBLE);
            holder.gameTime.setVisibility(View.VISIBLE);
            holder.direName.setPaintFlags(0);
            holder.radiantName.setPaintFlags(0);
            switch (game.getStatus()) {
                case 1:
                    holder.gameTime.setText(context.getString(R.string.in_hero_selection));
                    holder.scoreHolder.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    holder.gameTime.setText(context.getString(R.string.waiting_for_horn));
                    break;
                case 3:
                    holder.gameTime.setText(MessageFormat.format(context.getString(R.string.minutes_), game.getDuration() / 60));
                    break;
                case 4:
                    holder.gameTime.setText(MessageFormat.format(context.getString(R.string.minutes_), game.getDuration() / 60));
                    if (game.getWinner() == 0) {
                        holder.direName.setPaintFlags(holder.direName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        holder.radiantName.setPaintFlags(holder.radiantName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        ;
                    }
                default:
            }
        }
        return itemView;
    }

    public class LiveGameHolder {
        ImageView radiantLogo;
        ImageView direLogo;
        TextView radiantTag;
        TextView direTag;
        TextView radiantScore;
        TextView direScore;
        View scoreHolder;
        TextView radiantName;
        TextView direName;
        TextView gameState;
        TextView streams;
        TextView gameTime;
    }
}
