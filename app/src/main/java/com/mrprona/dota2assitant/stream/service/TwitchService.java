package com.mrprona.dota2assitant.stream.service;

import android.content.Context;
import android.util.Pair;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.api.twitch.TwitchAccessToken;
import com.mrprona.dota2assitant.stream.api.twitch.TwitchChannel;
import com.parser.Playlist;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 16.05.14
 * Time: 17:55
 */
public interface TwitchService extends InitializingBean {
    TwitchAccessToken getAccessToken(String channelName);

    Stream.List getGameStreams();

    Stream getStream(String channelName);

    Pair<Playlist, String> getPlaylist(Context context, String channelName, TwitchAccessToken accessToken);

    boolean isStreamFavourite(Context context, TwitchChannel channel);

    void addStream(Context context, Stream channel);

    void deleteStream(Context context, Stream channel);

    List<Stream> getFavouriteStreams(Context context);
}
