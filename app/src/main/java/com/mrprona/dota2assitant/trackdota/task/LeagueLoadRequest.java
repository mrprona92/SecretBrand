package com.mrprona.dota2assitant.trackdota.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.trackdota.api.LeaguesHolder;
import com.mrprona.dota2assitant.trackdota.service.TrackdotaService;

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
