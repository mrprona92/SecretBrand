package com.mrprona.dota2assitant.ranking.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;
import com.mrprona.dota2assitant.ranking.service.RankingService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 14:42
 */
public class PlayerRankingLoadRequest extends TaskRequest<PlayerRanking.List> {

    private Context mContext;
    private String mFilter;
    private String mSearch;

    public PlayerRankingLoadRequest(Context context, String filter, String search) {
        super(PlayerRanking.List.class);
        mContext = context;
        mFilter = filter;
        mSearch = search;
    }

    @Override
    public PlayerRanking.List loadData() throws Exception {
        RankingService rankingService = BeanContainer.getInstance().getmRankingService();
        return rankingService.getPlayerFilterRanking(mContext, mFilter, mSearch);
    }
}
