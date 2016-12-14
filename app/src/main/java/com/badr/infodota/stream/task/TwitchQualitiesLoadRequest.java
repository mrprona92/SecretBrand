package com.badr.infodota.stream.task;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.stream.api.twitch.TwitchAccessToken;
import com.badr.infodota.stream.service.TwitchService;
import com.parser.Element;
import com.parser.Playlist;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:12
 */
public class TwitchQualitiesLoadRequest extends TaskRequest<Element.List> {

    private String mChannelName;
    private Context mContext;

    public TwitchQualitiesLoadRequest(Context context, String channelName) {
        super(Element.List.class);
        this.mChannelName = channelName;
        this.mContext = context;
    }

    @Override
    public Element.List loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        TwitchService twitchService = container.getTwitchService();
        TwitchAccessToken result = twitchService.getAccessToken(mChannelName);

        if (result != null) {
            Pair<Playlist, String> playlistResult = twitchService.getPlaylist(mContext, mChannelName, result);
            if (playlistResult.first != null) {
                Playlist playList = playlistResult.first;
                return new Element.List(playList.getElements());
            }
        }
        return null;
    }
}