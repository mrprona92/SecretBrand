package com.badr.infodota.hero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 13:21
 */
public class OnYoutubeClickListener implements View.OnClickListener {
    String mYoutubeUrl;

    public OnYoutubeClickListener(String youtubeUrl) {
        this.mYoutubeUrl = youtubeUrl;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        if (YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context).equals(YouTubeInitializationResult.SUCCESS)) {
            Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(context, mYoutubeUrl, false, false);
            context.startActivity(intent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mYoutubeUrl)));
        }
    }
}
