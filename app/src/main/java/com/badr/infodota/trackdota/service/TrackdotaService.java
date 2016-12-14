package com.badr.infodota.trackdota.service;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.trackdota.api.LeaguesHolder;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.game.GamesResult;
import com.badr.infodota.trackdota.api.league.LeagueGamesHolder;
import com.badr.infodota.trackdota.api.live.LiveGame;

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
