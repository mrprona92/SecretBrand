package com.badr.infodota.stream.remote;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.base.api.Constants;
import com.badr.infodota.base.remote.BaseRemoteServiceImpl;
import com.badr.infodota.stream.api.twitch.TwitchAccessToken;
import com.parser.Playlist;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * User: ABadretdinov
 * Date: 16.05.14
 * Time: 17:38
 */
public class TwitchRemoteServiceImpl extends BaseRemoteServiceImpl implements TwitchRemoteService {
    @Override
    public TwitchAccessToken getAccessToken(Context context, String channelName) throws Exception {
        String url = MessageFormat.format(Constants.TwitchTV.ACCESS_TOKEN_URL, channelName);
        Pair<TwitchAccessToken, String> result = basicRequestSend(context, url, TwitchAccessToken.class);
        return result.first;
    }

    @Override
    public Pair<Playlist, String> getPlaylist(Context context, String channelName, TwitchAccessToken accessToken)
            throws Exception {
        String url = getPlaylistUrl(context, channelName, accessToken);
        Pair<String, String> result = basicRequestSend(context, url);
        if (result.first != null) {
            Playlist playList = Playlist.parse(result.first);
            return Pair.create(playList, result.second);
        }
        return Pair.create(null, result.second);
    }

    @Override
    public String getPlaylistUrl(Context context, String channelName, TwitchAccessToken accessToken) {
        try {
            return MessageFormat.format(Constants.TwitchTV.M3U8_URL, channelName,
                    URLEncoder.encode(accessToken.getToken(), "UTF-8"), accessToken.getSig());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //
        }
        return null;
    }
}
