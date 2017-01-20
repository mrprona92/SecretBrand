package com.mrprona.dota2assitant.stream.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.service.TwitchService;

import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:14
 */
public class FavStreamsLoadRequest extends TaskRequest<Stream.List> {

    List<Stream> channels;

    public FavStreamsLoadRequest(List<Stream> channels) {
        super(Stream.List.class);
        this.channels = channels;
    }

    @Override
    public Stream.List loadData() throws Exception {
        TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
        if (channels != null) {
            Stream.List list = new Stream.List();
            for (Stream channel : channels) {
                Stream stream = twitchService.getStream(channel.getChannel());
                if (stream != null) {
                    stream.setFavourite(true);
                    list.add(stream);
                }
            }
            return list;
        }
        return null;
    }
}
