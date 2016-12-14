package com.badr.infodota.trackdota.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.view.PinnedSectionListView;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.api.game.EnhancedGame;
import com.badr.infodota.trackdota.api.game.EnhancedMatch;
import com.badr.infodota.trackdota.api.game.Team;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 18:54
 */
public class LiveGamesAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private LayoutInflater inflater;
    private List<Object> games = new ArrayList<Object>();

    public LiveGamesAdapter(Context context, List<EnhancedMatch> matches) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (matches != null) {
            for (EnhancedMatch match : matches) {
                GameHeader header = new GameHeader();
                header.url = match.getUrl();
                header.id = match.getId();
                header.hasImage = match.isHasImage();
                header.name = match.getName();
                games.add(header);
                games.addAll(match.getGames());
            }
        }
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof GameHeader ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        Object object = getItem(position);
        Context context = parent.getContext();
        if (object instanceof GameHeader) {
            itemView = inflater.inflate(R.layout.trackdota_live_game_list_section, parent, false);
            TextView sectionHeader = (TextView) itemView.findViewById(R.id.text);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
            GameHeader header = (GameHeader) object;
            if (!TextUtils.isEmpty(header.name)) {
                sectionHeader.setText(header.name);
            } else {
                sectionHeader.setText(context.getString(R.string.unspecified_league));
            }
            if (header.hasImage) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(TrackdotaUtils.getLeagueImageUrl(header.id)).placeholder(R.drawable.empty_item).into(imageView);
            } else {
                imageView.setVisibility(View.INVISIBLE);
            }
        } else {
            LiveGameHolder holder;
            if (itemView == null || itemView.getTag() == null) {
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
            EnhancedGame game = (EnhancedGame) object;
            Team radiant = game.getRadiant();
            if (radiant != null) {
                holder.radiantTag.setText(TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT));
                holder.radiantName.setText(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT));
                if (radiant.isHasLogo()) {
                    Glide.with(context).load(TrackdotaUtils.getTeamImageUrl(radiant)).placeholder(R.drawable.empty_item).into(holder.radiantLogo);
                } else {
                    holder.radiantLogo.setImageResource(R.drawable.default_img);
                }
            } else {
                holder.radiantTag.setText("Radiant");
                holder.radiantName.setText("Radiant");
                holder.radiantLogo.setImageResource(R.drawable.default_img);
            }
            holder.radiantScore.setText(String.valueOf(game.getRadiantKills()));
            Team dire = game.getDire();
            if (dire != null) {
                holder.direTag.setText(TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE));
                holder.direName.setText(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE));
                if (dire.isHasLogo()) {
                    Glide.with(context).load(TrackdotaUtils.getTeamImageUrl(dire)).placeholder(R.drawable.empty_item).into(holder.direLogo);
                } else {
                    holder.direLogo.setImageResource(R.drawable.default_img);
                }
            } else {
                holder.direTag.setText("Dire");
                holder.direName.setText("Dire");
                holder.direLogo.setImageResource(R.drawable.default_img);
            }
            holder.direScore.setText(String.valueOf(game.getDireKills()));
            StringBuilder gameState = new StringBuilder(context.getString(R.string.game));
            gameState.append(" ");
            gameState.append(game.getDireWins() + game.getRadiantWins() + 1);
            gameState.append(" / ");
            gameState.append(context.getString(R.string.bo));
            gameState.append(1 + game.getSeriesType() * 2);
            if (game.getSeriesType() != 0) {
                gameState.append(" (");
                gameState.append(game.getRadiantWins());
                gameState.append(" - ");
                gameState.append(game.getDireWins());
                gameState.append(")");
            }
            holder.gameState.setText(gameState.toString());
            if (game.getStreams() > 0) {
                holder.streams.setText(MessageFormat.format(context.getString(R.string.streams_), game.getStreams()));
                holder.streams.setVisibility(View.VISIBLE);
            } else {
                holder.streams.setVisibility(View.INVISIBLE);
            }
            holder.scoreHolder.setVisibility(View.VISIBLE);
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
                default:
            }

        }
        return itemView;
    }

    public class GameHeader {
        String url;
        long id;
        boolean hasImage;
        String name;
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
