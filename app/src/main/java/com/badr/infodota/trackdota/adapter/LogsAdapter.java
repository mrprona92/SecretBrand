package com.badr.infodota.trackdota.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.view.PinnedSectionListView;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.api.GameManager;
import com.badr.infodota.trackdota.api.core.Player;
import com.badr.infodota.trackdota.api.game.Team;
import com.badr.infodota.trackdota.api.live.LogEvent;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ABadretdinov
 * 17.04.2015
 * 15:05
 */
public class LogsAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private ItemService itemService = BeanContainer.getInstance().getItemService();
    private GameManager gameManager = GameManager.getInstance();
    private List<Object> logs = new ArrayList<Object>();
    private Team radiant;
    private Team dire;


    public LogsAdapter(List<LogEvent> logEvents, Team radiant, Team dire) {
        this.radiant = radiant;
        this.dire = dire;
        /*add normal sorting*/
        if (logEvents != null) {
            List<LogEvent> events = new ArrayList<>(logEvents);
            Collections.reverse(events);
            for (LogEvent logEvent : events) {
                if (!logs.contains(logEvent.getTimestamp())) {
                    logs.add(logEvent.getTimestamp());
                }
                logs.add(logEvent);
            }
        }
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
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
        if (object instanceof LogEvent) {
            LogEventHolder holder;
            if (itemView == null || itemView.getTag() == null) {
                itemView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trackdota_log_row, parent, false);
                holder = new LogEventHolder();
                holder.heroIcon = (ImageView) itemView.findViewById(R.id.hero_icon);
                holder.text = (TextView) itemView.findViewById(android.R.id.text1);
                holder.itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
                itemView.setTag(holder);
            } else {
                holder = (LogEventHolder) itemView.getTag();
            }
            LogEvent logEvent = (LogEvent) object;
            holder.text.setTextSize(2, 16f);
            holder.text.setVisibility(View.VISIBLE);
            Resources resources = context.getResources();

            if ("kill".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.VISIBLE);
                holder.text.setTextColor(Color.WHITE);
                Player player = gameManager.getPlayer(logEvent.getAccountId());
                Hero hero = gameManager.getHero(player.getHeroId());
                Glide.with(context).load(SteamUtils.getHeroMiniImage(hero.getDotaId())).placeholder(0).into(holder.heroIcon);
                String playerName = getHtmlColorWrap(player.getName(), player.getTeam() == TrackdotaUtils.RADIANT ? resources.getColor(R.color.ally_team) : resources.getColor(R.color.enemy_team));
                if (logEvent.getDelta() > 1) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_kills_multiple_),
                                            playerName,
                                            logEvent.getDelta()
                                    )
                            )
                    );
                } else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_kills_),
                                            playerName
                                    )
                            )
                    );
                }
            } else if ("death".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.VISIBLE);
                holder.text.setTextColor(Color.WHITE);
                Player player = gameManager.getPlayer(logEvent.getAccountId());
                Hero hero = gameManager.getHero(player.getHeroId());
                Glide.with(context).load(SteamUtils.getHeroMiniImage(hero.getDotaId())).placeholder(0).into(holder.heroIcon);
                String playerName = getHtmlColorWrap(player.getName(), player.getTeam() == TrackdotaUtils.RADIANT ? resources.getColor(R.color.ally_team) : resources.getColor(R.color.enemy_team));
                if (logEvent.getDelta() > 1) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_dies_multiple_),
                                            playerName,
                                            logEvent.getDelta()
                                    )
                            )
                    );
                } else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.player_dies_),
                                            playerName
                                    )
                            )
                    );
                }
            } else if ("item".equals(logEvent.getAction())) {
                holder.itemIcon.setVisibility(View.VISIBLE);
                holder.heroIcon.setVisibility(View.VISIBLE);
                Player player = gameManager.getPlayer(logEvent.getAccountId());
                holder.text.setTextColor(Color.WHITE);
                Hero hero = gameManager.getHero(player.getHeroId());
                long itemId = Long.valueOf(logEvent.getId());
                Item item = gameManager.getItem(itemId);
                Glide.with(context).load(SteamUtils.getHeroMiniImage(hero.getDotaId())).placeholder(0).into(holder.heroIcon);
                if (item == null) {
                    item = itemService.getItemById(context, itemId);
                    gameManager.addItem(item);
                }
                Glide.with(context).load(SteamUtils.getItemImage(item.getDotaId())).placeholder(0).into(holder.itemIcon);
                String itemName = getHtmlColorWrap(item.getDotaName(), resources.getColor(R.color.item));
                String playerName = getHtmlColorWrap(player.getName(), player.getTeam() == TrackdotaUtils.RADIANT ? resources.getColor(R.color.ally_team) : resources.getColor(R.color.enemy_team));
                if ((itemId == 117 || itemId == 33) && logEvent.getDelta() >= 1) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_pickup),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                } else if (logEvent.getDelta() >= 1) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_purchased),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                } else if (logEvent.getDelta() == 0) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_sold),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                } else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_used),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }
            } else if ("tower".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setImageResource(0);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextColor(resources.getColor(R.color.tower));
                String teamName;
                long delta = logEvent.getDelta();
                /*первые 11 товеров - radiant*/
                if (delta < 11) {
                    teamName = getHtmlColorWrap(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT), resources.getColor(R.color.ally_team));
                } else {
                    teamName = getHtmlColorWrap(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE), resources.getColor(R.color.enemy_team));
                    delta -= 11;
                }
                String line;
                int level;
                String[] lines = context.getResources().getStringArray(R.array.map_tower_line);
                if (delta == 10) {
                    level = 4;
                    line = lines[2];
                } else if (delta == 9) {
                    level = 4;
                    line = lines[0];
                } else {
                    line = lines[((int) (delta / 3))];
                    level = (int) (1 + delta % 3);
                }
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_team_loses_tower),
                                        teamName,
                                        line,
                                        level
                                )
                        )
                );
            } else if ("barracks".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setImageResource(0);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextColor(resources.getColor(R.color.barracks));
                long delta = logEvent.getDelta();
                String teamName;
                /*первые 6 бараков - radiant*/
                if (delta < 6) {
                    teamName = getHtmlColorWrap(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT), resources.getColor(R.color.ally_team));
                } else {
                    teamName = getHtmlColorWrap(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE), resources.getColor(R.color.enemy_team));
                    delta -= 6;
                }
                String place = context.getResources().getStringArray(R.array.map_barracks_line)[((int) (delta / 2))];
                String type = delta % 2 == 0 ? context.getString(R.string.barrack_melee) : context.getString(R.string.barrack_ranged);
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_team_loses_barracks),
                                        teamName,
                                        place,
                                        type
                                )
                        )
                );

            } else if ("buyback".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setVisibility(View.VISIBLE);
                holder.text.setTextColor(resources.getColor(R.color.buyback));
                Player player = gameManager.getPlayer(logEvent.getAccountId());
                Hero hero = gameManager.getHero(player.getHeroId());
                Glide.with(context).load(SteamUtils.getHeroMiniImage(hero.getDotaId())).placeholder(0).into(holder.heroIcon);
                String playerName = getHtmlColorWrap(player.getName(), player.getTeam() == TrackdotaUtils.RADIANT ? resources.getColor(R.color.ally_team) : resources.getColor(R.color.enemy_team));
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.log_hero_buys_back),
                                        playerName
                                )
                        )
                );
            } else if ("roshan".equals(logEvent.getAction())) {
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.heroIcon.setImageResource(0);
                holder.heroIcon.setVisibility(View.GONE);
                holder.text.setTextSize(2, 20f);
                holder.text.setTextColor(resources.getColor(R.color.roshan));
                if (logEvent.getDelta() < 0) {
                    holder.text.setText(context.getString(R.string.roshan_death));
                } else {
                    holder.text.setText(context.getString(R.string.roshan_respawn));
                }
            } else if ("win".equals(logEvent.getAction())) {
                holder.heroIcon.setImageResource(0);
                holder.heroIcon.setVisibility(View.GONE);
                holder.itemIcon.setImageResource(0);
                holder.itemIcon.setVisibility(View.GONE);
                holder.text.setTextSize(2, 24f);
                holder.text.setTextColor(Color.WHITE);
                String teamName = logEvent.getDelta() == TrackdotaUtils.RADIANT ?
                        getHtmlColorWrap(
                                TrackdotaUtils.getTeamName(
                                        radiant,
                                        TrackdotaUtils.RADIANT
                                ),
                                resources.getColor(R.color.ally_team)
                        )
                        : getHtmlColorWrap(
                        TrackdotaUtils.getTeamName(
                                dire,
                                TrackdotaUtils.DIRE
                        ),
                        resources.getColor(R.color.enemy_team)
                );
                holder.text.setText(
                        Html.fromHtml(
                                MessageFormat.format(
                                        context.getString(R.string.team_wins_),
                                        teamName
                                )
                        )
                );
            } else if ("rapier".equals(logEvent.getAction())) {
                holder.itemIcon.setVisibility(View.VISIBLE);
                holder.heroIcon.setVisibility(View.VISIBLE);
                holder.text.setTextColor(resources.getColor(R.color.rapier));
                holder.text.setTextColor(Color.WHITE);
                Player player = gameManager.getPlayer(logEvent.getAccountId());
                Hero hero = gameManager.getHero(player.getHeroId());
                long itemId = 133;
                Item item = gameManager.getItem(itemId);
                Glide.with(context).load(SteamUtils.getHeroMiniImage(hero.getDotaId())).placeholder(0).into(holder.heroIcon);
                if (item == null) {
                    item = itemService.getItemById(context, itemId);
                    gameManager.addItem(item);
                }
                Glide.with(context).load(SteamUtils.getItemImage(item.getDotaId())).placeholder(0).into(holder.itemIcon);
                String itemName = getHtmlColorWrap(item.getDotaName(), resources.getColor(R.color.item));
                String playerName = getHtmlColorWrap(player.getName(), player.getTeam() == TrackdotaUtils.RADIANT ? resources.getColor(R.color.ally_team) : resources.getColor(R.color.enemy_team));
                if (logEvent.getDelta() > 0) {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_item_pickup),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                } else {
                    holder.text.setText(
                            Html.fromHtml(
                                    MessageFormat.format(
                                            context.getString(R.string.log_hero_drops_item),
                                            playerName,
                                            itemName
                                    )
                            )
                    );
                }

            }
        } else {
            long time = (long) object;
            itemView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.trackdota_log_pinned_header, parent, false);
            long minutes = time / 60;
            long seconds = time - minutes * 60;
            ((TextView) itemView.findViewById(android.R.id.text1)).setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
        }
        return itemView;
    }

    private String getHtmlColorWrap(String text, int color) {
        return "<font color='" + String.format("#%06X", 0xFFFFFF & color) + "'>" + text + "</font>";
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof LogEvent ? 0 : 1;
    }

    public class LogEventHolder {
        ImageView heroIcon;
        TextView text;
        ImageView itemIcon;
    }
}
