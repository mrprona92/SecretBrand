package com.mrprona.dota2assitant.trackdota.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.trackdota.api.live.LiveGame;
import com.mrprona.dota2assitant.trackdota.service.TrackdotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:10
 */
public class LiveGameLoadRequest extends TaskRequest<LiveGame> {
    private long mMatchId;
    private Context mContext;

    public LiveGameLoadRequest(Context context, long matchId) {
        super(LiveGame.class);
        this.mContext = context;
        this.mMatchId = matchId;
    }

    @Override
    public LiveGame loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        TrackdotaService trackdotaService = container.getTrackdotaService();
        return trackdotaService.getLiveGame(mContext, mMatchId);
    }
}
