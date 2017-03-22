package com.mrprona.dota2assitant.ranking.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.hero.api.CarouselHero;
import com.mrprona.dota2assitant.hero.service.HeroService;
import com.mrprona.dota2assitant.ranking.TeamRanking;
import com.mrprona.dota2assitant.ranking.service.RankingService;

import java.util.ArrayList;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 14:42
 */
public class TeamRankingLoadRequest extends TaskRequest<TeamRanking.List> {

    private Context mContext;
    private String mFilter;
    private String mSearch;

    public TeamRankingLoadRequest(Context context, String filter, String search) {
        super(TeamRanking.List.class);
        mContext = context;
        mFilter = filter;
        mSearch = search;
    }

    @Override
    public TeamRanking.List loadData() throws Exception {
        RankingService rankingService = BeanContainer.getInstance().getmRankingService();
        return rankingService.getTeamFilterRanking(mContext, mFilter, mSearch);
    }
}
