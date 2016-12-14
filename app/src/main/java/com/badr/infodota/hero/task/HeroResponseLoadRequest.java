package com.badr.infodota.hero.task;

import android.content.Context;
import android.os.Environment;

import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.api.responses.HeroResponse;
import com.badr.infodota.hero.api.responses.HeroResponsesSection;
import com.google.gson.Gson;

import java.io.File;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:15
 */
public class HeroResponseLoadRequest extends TaskRequest<HeroResponsesSection.List> {

    private Context mContext;
    private String mHeroDotaId;

    public HeroResponseLoadRequest(Context context, String heroDotaId) {
        super(HeroResponsesSection.List.class);
        this.mContext = context;
        this.mHeroDotaId = heroDotaId;
    }

    @Override
    public HeroResponsesSection.List loadData() throws Exception {
        String responsesEntity = FileUtils.getTextFromAsset(mContext,
                "heroes"
                        + File.separator + mHeroDotaId
                        + File.separator + "responses.json");
        HeroResponsesSection.List sections = new Gson().fromJson(
                responsesEntity,
                HeroResponsesSection.List.class);

        File musicFolder = new File(
                Environment.getExternalStorageDirectory()
                        + File.separator + "Music"
                        + File.separator + "dota2"
                        + File.separator + mHeroDotaId + File.separator);
        if (musicFolder.exists()) {
            for (HeroResponsesSection section : sections) {
                for (HeroResponse heroResponse : section.getResponses()) {
                    String[] urlParts = heroResponse.getUrl().split(File.separator);
                    String fileName = musicFolder + File.separator + urlParts[urlParts.length - 1];
                    if (new File(fileName).exists()) {
                        heroResponse.setLocalUrl(fileName);
                    }
                }
            }
        }
        return sections;
    }
}
