package com.mrprona.dota2assitant.hero.task;

import android.content.Context;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.base.util.FileUtils;
import com.mrprona.dota2assitant.hero.api.Skill;
import com.google.gson.Gson;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:38
 */
public class SkillsLoadRequest extends TaskRequest<Skill.List> {

    private Context mContext;
    private String mHeroDotaId;

    public SkillsLoadRequest(Context context, String heroDotaId) {
        super(Skill.List.class);
        this.mContext = context;
        this.mHeroDotaId = heroDotaId;
    }

    @Override
    public Skill.List loadData() throws Exception {
        String locale = mContext.getString(R.string.language);

        String json = FileUtils
                .getTextFromAsset(mContext, "heroes/" + mHeroDotaId + "/skills_" + locale + ".json");
        return new Gson().fromJson(json, Skill.List.class);
    }
}
