package com.badr.infodota.hero.task;

import android.media.MediaPlayer;
import android.text.TextUtils;

import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.hero.api.responses.HeroResponse;

import java.io.File;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:20
 */
public class MusicLoadRequest extends TaskRequest<MediaPlayer> {
    private MediaPlayer mMediaPlayer;
    private HeroResponse mHeroResponse;

    public MusicLoadRequest(MediaPlayer mediaPlayer, HeroResponse heroResponse) {
        super(MediaPlayer.class);
        this.mMediaPlayer = mediaPlayer;
        this.mHeroResponse = heroResponse;
    }

    @Override
    public MediaPlayer loadData() throws Exception {
        String path = mHeroResponse.getUrl();
        if (!TextUtils.isEmpty(mHeroResponse.getLocalUrl())) {
            File filePath = new File(mHeroResponse.getLocalUrl());
            if (filePath.exists()) {
                path = mHeroResponse.getLocalUrl();
            }
        }
        try { //http://developer.android.com/intl/ru/reference/android/media/AsyncPlayer.html
            if (mMediaPlayer != null) {
                try {
                    mMediaPlayer.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(path); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
            mMediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    try {
                        mp.start();
                    } catch (IllegalStateException e) {
                        //ignored
                    }
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMediaPlayer;
    }
}
