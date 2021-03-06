package com.mrprona.dota2assitant.stream.remote;

import com.mrprona.dota2assitant.stream.api.twitch.TwitchAccessToken;
import com.mrprona.dota2assitant.stream.api.twitch.TwitchGameStreams;
import com.mrprona.dota2assitant.stream.api.twitch.TwitchStreamTV;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Badr on 23.03.2015.
 */
public interface TwitchRestService {
    @GET("/kraken/streams?game=Dota%202&hls=true")
    TwitchGameStreams getGameStreams();

    @GET("/kraken/streams/{name}")
    TwitchStreamTV getStream(@Path("name") String channelName);

    @GET("/api/channels/{name}/access_token")
    TwitchAccessToken getAccessToken(@Path("name") String channelName);
}
