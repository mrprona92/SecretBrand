package com.mrprona.dota2assitant.trackdota.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.trackdota.api.core.CoreResult;
import com.mrprona.dota2assitant.trackdota.service.TrackdotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:10
 */
public class CoreGameLoadRequest extends TaskRequest<CoreResult> {
    private long mMatchId;
    private Context mContext;

    public CoreGameLoadRequest(Context context, long matchId) {
        super(CoreResult.class);
        this.mMatchId = matchId;
        this.mContext = context;
    }

    @Override
    public CoreResult loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        TrackdotaService trackdotaService = container.getTrackdotaService();
        return trackdotaService.getGameCoreData(mContext, mMatchId);
    }
}
