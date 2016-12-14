package com.badr.infodota.stream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.DialogUtils;
import com.badr.infodota.base.util.ProgressTask;
import com.badr.infodota.base.util.Utils;
import com.badr.infodota.stream.activity.TwitchPlayActivity;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.api.StreamQuality;
import com.badr.infodota.stream.api.twitch.TwitchAccessToken;
import com.badr.infodota.stream.service.TwitchService;
import com.parser.Element;
import com.parser.Playlist;

import java.util.List;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 15:11
 */
public class StreamUtils {
    public final static String TWITCH_PACKAGE = "tv.twitch.android.viewer";
    public final static String DOUYU_PACKAGE = "air.tv.douyu.android";

    public static void openActivity(Context context, Stream stream) {
        Intent intent;
        intent = new Intent(context, TwitchPlayActivity.class);
        intent.putExtra("channelName", stream.getChannel());
        intent.putExtra("channelTitle", stream.getTitle());
        context.startActivity(intent);

    }

    public static void openInSpecialApp(Context context, Stream stream) {
        Intent intent;
        String url;
        if ("twitch".equals(stream.getProvider())) {
            if (Utils.IsPackageInstalled(context, StreamUtils.TWITCH_PACKAGE)) {
                url = "twitch://stream/" + stream.getChannel();
            } else {
                url = "http://www.twitch.tv/" + stream.getChannel();
            }
        } else if ("douyu".equals(stream.getProvider())) {
            /*if(Utils.IsPackageInstalled(context, StreamUtils.DOUYU_PACKAGE)){
                url="douyu://stream/"+stream.getChannel();
            } else {*/
            url = "http://www.douyutv.com/" + stream.getChannel();
            /*}*/
        } else {
            return;
        }
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    public static void openInVideoStreamApp(final Activity activity, Stream stream) {
        final String channelName = stream.getChannel();
        if ("twitch".equals(stream.getProvider())) {
            DialogUtils.showLoaderDialog(((BaseActivity) activity).getSupportFragmentManager(), new ProgressTask<String>() {
                BeanContainer container = BeanContainer.getInstance();
                TwitchService service = container.getTwitchService();

                @Override
                public String doTask(OnPublishProgressListener listener) throws Exception {
                    TwitchAccessToken atResult = service.getAccessToken(channelName);
                    if (atResult != null) {
                        Pair<Playlist, String> playlistResult = service.getPlaylist(activity, channelName, atResult);
                        Playlist playlist = playlistResult.first;
                        List<Element> elements = playlist.getElements();
                        if (elements != null && elements.size() > 0) {
                            return elements.get(0).getURI().toString();
                        }
                    }
                    return "";
                }

                @Override
                public void doAfterTask(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(result), "application/x-mpegURL");//"video/m3u8");  //
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void handleError(String error) {

                }

                @Override
                public String getName() {
                    return null;
                }
            });
        } else {
            if (stream.getQualities() != null && stream.getQualities().size() > 0) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                StreamQuality quality = stream.getQualities().get(0);
                intent.setDataAndType(Uri.parse(quality.getUrl()), "application/x-mpegURL");
                activity.startActivity(intent);
            } else {
                openInSpecialApp(activity, stream);
            }
        }
    }
}
