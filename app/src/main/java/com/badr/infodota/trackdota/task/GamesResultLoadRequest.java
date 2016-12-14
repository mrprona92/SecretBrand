package com.badr.infodota.trackdota.task;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.trackdota.api.game.GamesResult;
import com.badr.infodota.trackdota.service.TrackdotaService;

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
