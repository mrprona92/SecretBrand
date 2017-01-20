package com.mrprona.dota2assitant.joindota.task;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 14:48
 */

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.joindota.api.LiveStream;
import com.mrprona.dota2assitant.joindota.service.JoinDotaService;

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
