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
import com.badr.infodota.trackdota.api.game.Game;
import com.badr.infodota.trackdota.api.game.League;
import com.badr.infodota.trackdota.api.game.Team;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 12:34
 */
public class FeaturedGamesAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private LayoutInflater inflater;
    private List<Object> mGames = new ArrayList<Object>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm  dd.MM.yyyy");

    public FeaturedGamesAdapter(Context context, List<Game> games) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (games != null) {
            for (Game game : games) {
                int size = mGames.size();
                if (size == 0 || !((Game) mGames.get(size - 1)).getLeague().equals(game.getLeague())) {
                    GameHeader header = new GameHeader();
                    League league = game.getLeague();
                    header.url = league.getUrl();
                    header.id = league.getId();
                    header.hasImage = league.isHasImage();
                    header.name = league.getName();
                    mGames.add(header);
                }
                mGames.add(game);
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
        return mGames.size();
    }

    @Override
    public Object getItem(int position) {
        return mGames.get(position);
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
            FeaturedGameHolder holder;
            if (itemView == null || itemView.getTag() == null) {
                itemView = inflater.inflate(R.layout.trackdota_featured_game_row, parent, false);
                holder = new FeaturedGameHolder();
                holder.radiantLogo = (ImageView) itemView.findViewById(R.id.radiant_logo);
                holder.direLogo = (ImageView) itemView.findViewById(R.id.dire_logo);
                holder.radiantTag = (TextView) itemView.findViewById(R.id.radiant_tag);
                holder.direTag = (TextView) itemView.findViewById(R.id.dire_tag);
                holder.radiantScore = (TextView) itemView.findViewById(R.id.radiant_score);
                holder.direScore = (TextView) itemView.findViewById(R.id.dire_score);
                holder.scoreHolder = itemView.findViewById(R.id.score_holder);
                holder.radiantName = (TextView) itemView.findViewById(R.id.radiant_name);
                holder.direName = (TextView) itemView.findViewById(R.id.dire_name);
                holder.gameStartTime = (TextView) itemView.findViewById(R.id.game_start_time);
                holder.viewers = (TextView) itemView.findViewById(R.id.viewers);
                holder.gameDuration = (TextView) itemView.findViewById(R.id.game_duration);
                itemView.setTag(holder);
            } else {
                holder = (FeaturedGameHolder) itemView.getTag();
            }
            Game game = (Game) object;
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

            holder.gameStartTime.setText(dateTimeFormat.format(new Date(game.getStartTime() * 1000)));
            holder.viewers.setText(MessageFormat.format(context.getString(R.string.viewers_), game.getSpectators()));
            holder.gameDuration.setText(MessageFormat.format(context.getString(R.string.minutes_), game.getDuration() / 60));
        }
        return itemView;
    }

    public class GameHeader {
        String url;
        long id;
        boolean hasImage;
        String name;
    }

    public class FeaturedGameHolder {
        ImageView radiantLogo;
        ImageView direLogo;
        TextView radiantTag;
        TextView direTag;
        TextView radiantScore;
        TextView direScore;
        View scoreHolder;
        TextView radiantName;
        TextView direName;
        TextView gameStartTime;
        TextView viewers;
        TextView gameDuration;
    }
}
