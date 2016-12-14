package com.badr.infodota.trackdota.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.trackdota.api.live.LiveGame;
import com.badr.infodota.trackdota.service.TrackdotaService;

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
