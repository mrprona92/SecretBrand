package com.badr.infodota.hero.task;

import android.content.Context;

import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.api.guide.TitleOnly;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:31
 */
public class GuidesLoadRequest extends TaskRequest<List> {
    private Context mContext;
    private String mHeroDotaId;
    private Map<String, String> mGuideNameMap;

    public GuidesLoadRequest(Context context, String heroDotaId, Map<String, String> guideNameMap) {
        super(List.class);
        this.mContext = context;
        this.mHeroDotaId = heroDotaId;
        this.mGuideNameMap = guideNameMap;
    }

    @Override
    public List loadData() throws Exception {
        String[] guideList = FileUtils.childrenFileNamesFromAssets(mContext, "guides/" + mHeroDotaId);
        File externalFilesDir = FileUtils.externalFileDir(mContext);
        File heroGuidesFolder = new File(externalFilesDir.getAbsolutePath() + File.separator + "guides" + File.separator + mHeroDotaId + File.separator);
        String[] creatorsGuideList;
        if (heroGuidesFolder.exists() && heroGuidesFolder.isDirectory()) {
            creatorsGuideList = heroGuidesFolder.list();
        } else {
            creatorsGuideList = new String[0];
        }
        mGuideNameMap.clear();
        String dir = externalFilesDir.getAbsolutePath();
        for (String guideFileName : creatorsGuideList) {
            String entity = FileUtils.getTextFromFile(dir + File.separator + "guides" + File.separator + mHeroDotaId + File.separator + guideFileName);
            TitleOnly titleOnly = new Gson().fromJson(entity, TitleOnly.class);
            mGuideNameMap.put(externalFilesDir.getAbsolutePath() + File.separator + "guides" + File.separator + mHeroDotaId + File.separator + guideFileName, titleOnly.getTitle());
        }
        for (String guideFileName : guideList) {
            String entity = FileUtils.getTextFromAsset(mContext, "guides" + File.separator + mHeroDotaId + File.separator + guideFileName);
            TitleOnly titleOnly = new Gson().fromJson(entity, TitleOnly.class);
            mGuideNameMap.put("guides" + File.separator + mHeroDotaId + File.separator + guideFileName, titleOnly.getTitle());
        }
        List<String> guideNames = new ArrayList<String>();
        for (String guidePath : mGuideNameMap.keySet()) {
            guideNames.add(mGuideNameMap.get(guidePath));
        }
        return guideNames;
    }
}
