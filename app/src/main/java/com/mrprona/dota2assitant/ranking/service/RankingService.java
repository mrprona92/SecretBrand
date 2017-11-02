package com.mrprona.dota2assitant.ranking.service;

import android.content.Context;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.hero.api.CarouselHero;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.TalentTree;
import com.mrprona.dota2assitant.hero.api.abilities.Ability;
import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:35
 */
public interface RankingService extends InitializingBean {

    List<TeamRanking> getAllTeamRanked();

    List<PlayerRanking> getAllPlayerRanked(int typeRanking);

    TeamRanking.List getTeamFilterRanking(Context context, String filter, String name);

    PlayerRanking.List getPlayerFilterRanking(Context context, String filter, String name);

}
