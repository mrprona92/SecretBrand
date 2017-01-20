package com.mrprona.dota2assitant.stream.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.service.TwitchService;

import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:50
 */
public class TwitchStreamsLoadRequest extends TaskRequest<Stream.List> {
    private List<Stream> mFavs;

    public TwitchStreamsLoadRequest(List<Stream> favourites) {
        super(Stream.List.class);
        mFavs = favourites;
    }

    @Override
    public Stream.List loadData() throws Exception {
        TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
        Stream.List result = twitchService.getGameStreams();
        if (result != null && mFavs != null) {
            for (Stream stream : result) {
                stream.setFavourite(mFavs.contains(stream));
            }
        }
        return result;
    }
}