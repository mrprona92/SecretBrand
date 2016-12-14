package com.badr.infodota.trackdota.task;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.trackdota.api.LeaguesHolder;
import com.badr.infodota.trackdota.service.TrackdotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:37
 */
public class LeagueLoadRequest extends TaskRequest<LeaguesHolder> {

    public LeagueLoadRequest() {
        super(LeaguesHolder.class);
    }

    @Override
    public LeaguesHolder loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        TrackdotaService trackdotaService = container.getTrackdotaService();
        return trackdotaService.getLeagues();
    }
}
