package com.mrprona.dota2assitant.trackdota.service;

import android.content.Context;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.trackdota.api.LeaguesHolder;
import com.mrprona.dota2assitant.trackdota.api.core.CoreResult;
import com.mrprona.dota2assitant.trackdota.api.game.GamesResult;
import com.mrprona.dota2assitant.trackdota.api.league.LeagueGamesHolder;
import com.mrprona.dota2assitant.trackdota.api.live.LiveGame;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 17:17
 */
public interface TrackdotaService extends InitializingBean {

    LiveGame getLiveGame(Context context, long gameId);

    CoreResult getGameCoreData(Context context, long gameId);

    GamesResult getGames();

    LeaguesHolder getLeagues();

    LeagueGamesHolder getLeagueGames(long leagueId);
}
