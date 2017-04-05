package com.mrprona.dota2assitant.ranking.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.api.Constants;
import com.mrprona.dota2assitant.base.dao.DatabaseManager;
import com.mrprona.dota2assitant.hero.api.CarouselHero;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.HeroStats;
import com.mrprona.dota2assitant.hero.api.TalentTree;
import com.mrprona.dota2assitant.hero.api.abilities.Ability;
import com.mrprona.dota2assitant.hero.dao.AbilityDao;
import com.mrprona.dota2assitant.hero.dao.HeroDao;
import com.mrprona.dota2assitant.hero.dao.HeroStatsDao;
import com.mrprona.dota2assitant.hero.dao.TalentDao;
import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;
import com.mrprona.dota2assitant.ranking.Utils.ParserInfoTeam;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:35
 */
public class RankingServiceImpl implements RankingService {

    List<TeamRanking> mListTeamRanked;
    List<PlayerRanking> mListPlayerRanked;
    String[] listURL = {Constants.RANKINGTEAM0, Constants.RANKINGTEAM1, Constants.RANKINGTEAM2, Constants.RANKINGTEAM3};

    @Override
    public void initialize() {
        BeanContainer beanContainer = BeanContainer.getInstance();

    }
    @Override
    public List<TeamRanking> getAllTeamRanked() {
        mListTeamRanked = ParserInfoTeam.getAllTeamRanked(Constants.GOSUGAMER_RANKINGTEAM);
        return mListTeamRanked;
    }

    @Override
    public List<PlayerRanking> getAllPlayerRanked(int type) {
        mListPlayerRanked = ParserInfoTeam.getAllPlayerRanked(listURL[type]);
        return mListPlayerRanked;
    }

    @Override
    public TeamRanking.List getTeamFilterRanking(Context context, String filter, String name) {

        TeamRanking.List filteredHeroes = new TeamRanking.List();
        List<TeamRanking> allTeamRanking = ParserInfoTeam.getAllTeamRankCached();

        if (allTeamRanking != null) {
            String lowerConstr = name.toString().toLowerCase();
            for (TeamRanking mTeamRanking : allTeamRanking) {
                if (mTeamRanking.getTeamName().toLowerCase().contains(lowerConstr)) {
                    filteredHeroes.add(mTeamRanking);
                }
            }
        }
        return filteredHeroes;
    }

    @Override
    public PlayerRanking.List getPlayerFilterRanking(Context context, String filter, String name) {

        PlayerRanking.List filteredPlayers = new PlayerRanking.List();
        List<PlayerRanking> allTeamRanking = ParserInfoTeam.getAllPlayerRankCached();

        if (allTeamRanking != null) {
            String lowerConstr = name.toString().toLowerCase();
            for (PlayerRanking mTeamRanking : allTeamRanking) {
                if (mTeamRanking.getPlayerName().toLowerCase().contains(lowerConstr)) {
                    filteredPlayers.add(mTeamRanking);
                }
            }
        }
        return filteredPlayers;
    }
}
