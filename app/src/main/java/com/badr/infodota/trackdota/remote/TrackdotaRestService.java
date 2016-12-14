package com.badr.infodota.trackdota.remote;

import com.badr.infodota.trackdota.api.LeaguesHolder;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.game.GamesResult;
import com.badr.infodota.trackdota.api.league.LeagueGamesHolder;
import com.badr.infodota.trackdota.api.live.LiveGame;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 16:39
 */
public interface TrackdotaRestService {
    @GET("/game/{gameId}/live.json")
    LiveGame getLiveGame(@Path("gameId") long gameId);

    @GET("/game/{gameId}/core.json")
    CoreResult getGameCoreData(@Path("gameId") long gameId);

    @GET("/games_v2.json")
    GamesResult getGames();

    @GET("/leagues.json")
    LeaguesHolder getLeagues();

    @GET("/leagues/{leagueId}/games.json")
    LeagueGamesHolder getLeagueGames(@Path("leagueId") long leagueId);
}
