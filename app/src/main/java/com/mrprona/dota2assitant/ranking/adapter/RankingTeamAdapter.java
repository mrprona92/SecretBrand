package com.mrprona.dota2assitant.ranking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Glide;
import com.heetch.countrypicker.Utils;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.BaseRecyclerAdapter;
import com.mrprona.dota2assitant.base.util.SteamUtils;
import com.mrprona.dota2assitant.hero.adapter.holder.HeroHolder;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.ranking.TeamRanking;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * User: ABadretdinov
 * Date: 29.08.13
 * Time: 12:25
 */
public class RankingTeamAdapter extends BaseRecyclerAdapter<TeamRanking, TeamRankingHolder> implements Filterable {
    private List<TeamRanking> filtered;
    private Context mContext;
    private Map<Long, Integer> mMapType;


    public RankingTeamAdapter(List<TeamRanking> teamrankings, Context current) {
        super(teamrankings);
        filtered = mData;
        this.mContext = current;
        this.mMapType = mMapType;
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    @Override
    public TeamRanking getItem(int position) {
        return filtered.get(position);
    }

    @Override
    public TeamRankingHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rankingteam_row, parent, false);
        return new TeamRankingHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(TeamRankingHolder holder, int position) {
        TeamRanking teamRanking = getItem(position);
        holder.name.setText(teamRanking.getTeamName());
        holder.rank.setText(teamRanking.getRankCurrent());
        holder.scoreranking.setText(teamRanking.getNumberRanking());

        String drawableName = teamRanking.getFlagID() + "_flag";

        if(drawableName.equals("eu_flag")){
            holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.flag_eu));
        }else{
            holder.image.setImageResource(Utils.getMipmapResId(mContext, drawableName));
        }

        holder.rankingchange.setVisibility(View.VISIBLE);

        if(teamRanking.getChangeRanking().equals("Up")){
            holder.rankingchange.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_up));
        }else if(teamRanking.getChangeRanking().equals("Down")){
            holder.rankingchange.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_down));
        }else{
            holder.rankingchange.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_nochange));
        }



        //Context context = holder.name.getContext();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<TeamRanking> filteredHeroes = new ArrayList<TeamRanking>();
                if (constraint == null) {
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                    return filterResults;
                }

                String lowerConstr = constraint.toString().toLowerCase();
                for (TeamRanking mTeamRanking : mData) {
                    if (mTeamRanking.getTeamName().toLowerCase().contains(lowerConstr) || mTeamRanking.getTeamName().toLowerCase().contains(lowerConstr)) {
                        filteredHeroes.add(mTeamRanking);
                    }
                }

                filterResults.count = filteredHeroes.size();
                filterResults.values = filteredHeroes;
                return filterResults;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered = (ArrayList<TeamRanking>) results.values;
                if (filtered == null) {
                    filtered = new ArrayList<TeamRanking>();
                }
                notifyDataSetChanged();
            }
        };
    }
}
