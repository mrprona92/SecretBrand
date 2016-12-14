package com.badr.infodota.joindota.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.util.DateUtils;
import com.badr.infodota.base.view.PinnedSectionListView;
import com.badr.infodota.joindota.api.MatchItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:36
 */
public class LeaguesGamesAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private LayoutInflater inflater;
    private List<MatchItem> matchItems;

    public LeaguesGamesAdapter(Context context, List<MatchItem> matchItems) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.matchItems = matchItems != null ? matchItems : new ArrayList<MatchItem>();
        for (int i = 0; i < this.matchItems.size(); i++) {
            MatchItem item = this.matchItems.get(i);
            MatchItem possibleHeader = new MatchItem();
            possibleHeader.setDate(item.getDate());
            possibleHeader.setSection(true);
            if (!this.matchItems.contains(possibleHeader)) {
                this.matchItems.add(i, possibleHeader);
            }
        }
    }

    public void addMatchItems(List<MatchItem> matchItems) {
        if (matchItems != null) {
            for (MatchItem matchItem : matchItems) {
                if (!this.matchItems.contains(matchItem)) {
                    this.matchItems.add(matchItem);
                }
            }
        }
        for (int i = 0; i < this.matchItems.size(); i++) {
            MatchItem item = this.matchItems.get(i);
            MatchItem possibleHeader = new MatchItem();
            possibleHeader.setDate(item.getDate());
            possibleHeader.setSection(true);
            if (!this.matchItems.contains(possibleHeader)) {
                this.matchItems.add(i, possibleHeader);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return matchItems.size();
    }

    @Override
    public MatchItem getItem(int position) {
        return matchItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isSection() ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        MatchItem item = getItem(position);
        if (item.isSection()) {
            vi = inflater.inflate(R.layout.list_section, parent, false);
            TextView sectionHeader = (TextView) vi.findViewById(R.id.section_title);
            sectionHeader.setText(DateUtils.DATE_FORMAT.format(item.getDate()));
        } else {
            MatchItemHolder holder;
            if (vi == null || vi.getTag() == null) {
                vi = inflater.inflate(R.layout.leagues_games_row, parent, false);
                holder = new MatchItemHolder();
                holder.flag1 = (ImageView) vi.findViewById(R.id.flag1);
                holder.team1 = (TextView) vi.findViewById(R.id.team1);
                holder.flag2 = (ImageView) vi.findViewById(R.id.flag2);
                holder.team2 = (TextView) vi.findViewById(R.id.team2);
                holder.middleText = (TextView) vi.findViewById(R.id.middle_text);
                vi.setTag(holder);
            } else {
                holder = (MatchItemHolder) vi.getTag();
            }
            holder.team1.setText(item.getTeam1name());
            holder.team2.setText(item.getTeam2name());
            Context context = parent.getContext();
            Glide.with(context).load(item.getTeam1flagLink()).placeholder(R.drawable.flag_default).into(holder.flag1);
            Glide.with(context).load(item.getTeam2flagLink()).placeholder(R.drawable.flag_default).into(holder.flag2);

            holder.middleText.setText(item.getMiddleText());
        }
        return vi;
    }

    public class MatchItemHolder {
        ImageView flag1;
        TextView team1;
        ImageView flag2;
        TextView team2;
        TextView middleText;
    }
}
