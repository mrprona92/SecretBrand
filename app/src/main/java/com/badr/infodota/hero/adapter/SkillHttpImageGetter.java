package com.badr.infodota.hero.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.badr.infodota.base.util.FileUtils;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 13:18
 */
public class SkillHttpImageGetter implements Html.ImageGetter {
    private Context mContext;

    public SkillHttpImageGetter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        String[] parts = source.split("/");
        String realFileName = parts[parts.length - 1];
        return FileUtils.getDrawableFromAsset(mContext, realFileName);
    }
}