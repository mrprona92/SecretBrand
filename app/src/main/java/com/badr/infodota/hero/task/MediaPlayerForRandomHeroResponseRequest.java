package com.badr.infodota.hero.task;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.responses.HeroResponse;
import com.badr.infodota.hero.api.responses.HeroResponsesSection;
import com.google.gson.Gson;

import java.io.File;
import java.util.Random;

/**
 * Created by ABadretdinov
 * 19.08.2015
 * 18:01
 */
public class MediaPlayerForRandomHeroResponseRequest extends TaskRequest<MediaPlayer> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private String mHeroDotaId;
    private Context mContext;
    private String mSectionName;
    private MediaPlayer mMediaPlayer;

    public MediaPlayerForRandomHeroResponseRequest(Context context, Hero hero, String sectionName, MediaPlayer mediaPlayer) {
        super(MediaPlayer.class);
        this.mHeroDotaId = hero.getDotaId();
        this.mContext = context;
        this.mSectionName = sectionName;
    }

    @Override
    public MediaPlayer loadData() throws Exception {
        HeroResponse heroResponse = getHeroResponse();
        if (heroResponse != null) {
            mMediaPlayer = new MediaPlayer();
            String path = heroResponse.getUrl();
            if (!TextUtils.isEmpty(heroResponse.getLocalUrl())) {
                File filePath = new File(heroResponse.getLocalUrl());
                if (filePath.exists()) {
                    path = heroResponse.getLocalUrl();
                }
            }
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnErrorListener(this);
        }
        return mMediaPlayer;
    }

    @Nullable
    private HeroResponse getHeroResponse() {
        String responsesEntity = FileUtils.getTextFromAsset(
                mContext,
                "heroes" + File.separator + mHeroDotaId + File.separator + "responses.json");
        HeroResponsesSection.List sections = new Gson().fromJson(responsesEntity, HeroResponsesSection.List.class);
        if (!TextUtils.isEmpty(mSectionName)) {
            for (HeroResponsesSection section : sections) {
                if (mSectionName.equals(section.getName())) {
                    int size = section.getResponses().size();
                    Random rand = new Random();
                    HeroResponse response = section.getResponses().get(rand.nextInt(size - 1));
                    File musicFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Music" + File.separator + "dota2" + File.separator + mHeroDotaId + File.separator);
                    if (musicFolder.exists()) {
                        String[] urlParts = response.getUrl().split(File.separator);
                        String fileName = musicFolder + File.separator + urlParts[urlParts.length - 1];
                        if (new File(fileName).exists()) {
                            response.setLocalUrl(fileName);
                        }
                    }
                    return response;
                }
            }
        }
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        try {
            mp.start();
        } catch (IllegalStateException e) {
            //ignored
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
