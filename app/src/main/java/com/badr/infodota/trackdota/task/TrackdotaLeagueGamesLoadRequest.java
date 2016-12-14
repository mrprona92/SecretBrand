package com.badr.infodota.trackdota.task;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.trackdota.api.league.LeagueGamesHolder;
import com.badr.infodota.trackdota.service.TrackdotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:36
 */
public class TrackdotaLeagueGamesLoadRequest extends TaskRequest<LeagueGamesHolder> {
    private long mLeagueId;

    public TrackdotaLeagueGamesLoadRequest(long leagueId) {
        super(LeagueGamesHolder.class);
        this.mLeagueId = leagueId;
    }

    @Override
    public LeagueGamesHolder loadData() throws Exception {
        TrackdotaService trackdotaService = BeanContainer.getInstance().getTrackdotaService();
        return trackdotaService.getLeagueGames(mLeagueId);
    }
}
