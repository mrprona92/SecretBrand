package com.util.infoparser.loader;

import android.content.Context;
import android.os.Environment;

import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.base.util.VDFtoJsonParser;
import com.google.gson.Gson;
import com.util.infoparser.api.CosmeticItemStringHolder;

/**
 * Created by Badr on 25.08.2015.
 */
public class CosmeticItemsStringsLoadRequest extends TaskRequest<String> {
    private String mLocale;
    private Context mContext;

    public CosmeticItemsStringsLoadRequest(Context context, String locale) {
        super(String.class);
        mLocale = locale;
        mContext = context;
    }

    @Override
    public String loadData() throws Exception {
        String textVdf = FileUtils.getTextFromAsset(mContext, "cosmetic_items_" + mLocale + ".txt");

        String textJson = VDFtoJsonParser.parse(textVdf).replaceFirst("\uFEFF\"lang\"", "\"lang\"");
        FileUtils.saveStringFile("cosmetic_items_text_" + mLocale + ".json", textJson);

        CosmeticItemStringHolder holder = new Gson().fromJson(textJson, CosmeticItemStringHolder.class);

        FileUtils.saveJsonFile(Environment.getExternalStorageDirectory().getPath() + "/dota/cosmetic_text_" + mLocale + ".json", holder.getLang());

        return null;
    }
}
