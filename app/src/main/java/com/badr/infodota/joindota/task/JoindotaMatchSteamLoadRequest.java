package com.badr.infodota.joindota.task;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 14:48
 */

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.joindota.api.LiveStream;
import com.badr.infodota.joindota.service.JoinDotaService;

import java.util.List;

public class JoindotaMatchSteamLoadRequest extends TaskRequest<String> {
    private List<LiveStream> mLiveStreams;

    public JoindotaMatchSteamLoadRequest(List<LiveStream> liveStreams) {
        super(String.class);
        this.mLiveStreams = liveStreams;
    }

    @Override
    public String loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        JoinDotaService service = container.getJoinDotaService();
        return service.fillChannelName(mLiveStreams);
    }
}
