package com.mrprona.dota2assitant.trackdota.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.trackdota.api.game.GamesResult;
import com.mrprona.dota2assitant.trackdota.service.TrackdotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:15
 */
public class GamesResultLoadRequest extends TaskRequest<GamesResult> {
    public GamesResultLoadRequest() {
        super(GamesResult.class);
    }

    @Override
    public GamesResult loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        TrackdotaService trackdotaService = container.getTrackdotaService();
        return trackdotaService.getGames();
    }
}
