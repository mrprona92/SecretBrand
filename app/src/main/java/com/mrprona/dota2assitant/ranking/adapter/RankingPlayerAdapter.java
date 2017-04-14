package com.mrprona.dota2assitant.ranking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.heetch.countrypicker.Utils;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.BaseRecyclerAdapter;
import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * User: ABadretdinov
 * Date: 29.08.13
 * Time: 12:25
 */
public class RankingPlayerAdapter extends BaseRecyclerAdapter<PlayerRanking, PlayerRankingHolder> implements Filterable {
    private List<PlayerRanking> filtered;
    private Context mContext;
    private Map<Long, Integer> mMapType;


    public RankingPlayerAdapter(List<PlayerRanking> playerrankings, Context current) {
        super(playerrankings);
        filtered = mData;
        this.mContext = current;
        this.mMapType = mMapType;
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    @Override
    public PlayerRanking getItem(int position) {
        return filtered.get(position);
    }

    @Override
    public PlayerRankingHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rankingplayer_row, parent, false);
        return new PlayerRankingHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(PlayerRankingHolder holder, int position) {
        PlayerRanking playerRanking = getItem(position);
        holder.name.setText(playerRanking.getPlayerName());
        holder.rank.setText(playerRanking.getRankCurrent());
        holder.scoreranking.setText(playerRanking.getNumberRanking());

        String drawableName = playerRanking.getFlagID() + "_flag";

        if(drawableName.equals("eu_flag")){
            holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.flag_eu));
        }else{
            holder.image.setImageResource(Utils.getMipmapResId(mContext, drawableName));
        }


        holder.rankingchange.setVisibility(View.VISIBLE);

        if(playerRanking.getChangeRanking().equals("Up")){
            holder.rankingchange.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ranking_up));
        }else if(playerRanking.getChangeRanking().equals("Down")){
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
                List<PlayerRanking> filteredHeroes = new ArrayList<PlayerRanking>();
                if (constraint == null) {
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                    return filterResults;
                }
                String lowerConstr = constraint.toString().toLowerCase();
                for (PlayerRanking mTeamRanking : mData) {
                    if (mTeamRanking.getPlayerName().toLowerCase().contains(lowerConstr) || mTeamRanking.getPlayerName().toLowerCase().contains(lowerConstr)) {
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
                filtered = (ArrayList<PlayerRanking>) results.values;
                if (filtered == null) {
                    filtered = new ArrayList<PlayerRanking>();
                }
                notifyDataSetChanged();
            }
        };
    }
}
