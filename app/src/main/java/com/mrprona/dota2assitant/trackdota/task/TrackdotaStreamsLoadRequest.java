package com.mrprona.dota2assitant.trackdota.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.service.DouyuService;
import com.mrprona.dota2assitant.stream.service.TwitchService;

import java.util.Collections;
import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:40
 */
public class TrackdotaStreamsLoadRequest extends TaskRequest<Stream.List> {

    private Context mContext;
    private List<Stream> mStreams;

    public TrackdotaStreamsLoadRequest(Context context, List<Stream> streams) {
        super(Stream.List.class);
        mContext = context;
        mStreams = streams;
    }

    @Override
    public Stream.List loadData() throws Exception {
        TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
        DouyuService douyuService = BeanContainer.getInstance().getDouyuService();
        if (mStreams != null) {
            List<Stream> favs = twitchService.getFavouriteStreams(mContext);
            Stream.List list = new Stream.List();
            for (Stream channel : mStreams) {
                if ("twitch".equals(channel.getProvider())) {
                    Stream stream = twitchService.getStream(channel.getChannel());
                    if (stream != null) {
                        stream.setFavourite(favs != null && favs.contains(stream));
                        list.add(stream);
                    }
                } else if ("douyu".equals(channel.getProvider())) {
                    Stream stream = douyuService.getStream(channel);
                    if (stream != null) {
                        stream.setFavourite(favs != null && favs.contains(stream));
                        list.add(stream);
                    }
                }
            }
            Collections.sort(list);
            Collections.reverse(list);
            return list;
        }
        return null;
    }
}