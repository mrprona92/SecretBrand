package com.mrprona.dota2assitant.base.task;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.dao.DatabaseManager;
import com.mrprona.dota2assitant.base.service.LocalUpdateService;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.base.util.FileUtils;
import com.mrprona.dota2assitant.hero.api.TalentTree;
import com.mrprona.dota2assitant.hero.dao.HeroDao;
import com.parser.JsonSimpleExample;

import java.io.File;
import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:59
 */
public class UpdateLoadRequest extends TaskRequest<String> {

    private Context mContext;

    public UpdateLoadRequest(Context context) {
        super(String.class);
        this.mContext = context;
    }

    @Override
    public String loadData() throws Exception {
        DatabaseManager manager= DatabaseManager.getInstance(mContext);

        SQLiteDatabase db= manager.openDatabase();

        List<TalentTree> mListTalentTreee= JsonSimpleExample.ConvertJsonFile(mContext);
        for (TalentTree mTalentTree: mListTalentTreee) {
            HeroDao.bindItems(db, mTalentTree);
        }

        manager.closeDatabase();

        LocalUpdateService localUpdateService = BeanContainer.getInstance().getLocalUpdateService();
        AssetManager assetManager = mContext.getAssets();
        String[] files = assetManager.list("updates");
        for (String fileName : files) {
            int fileVersion = Integer.valueOf(fileName.split("\\.")[0]);
            String sql = FileUtils.getTextFromAsset(mContext, "updates" + File.separator + fileName);
            localUpdateService.update(mContext, sql, fileVersion);
        }
        return "";
    }
}
