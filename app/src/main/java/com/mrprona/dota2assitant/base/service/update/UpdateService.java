package com.mrprona.dota2assitant.base.service.update;

import android.content.Context;
import android.util.Pair;

import com.mrprona.dota2assitant.InitializingBean;

/**
 * Created by Badr on 17.02.2015.
 */
public interface UpdateService extends InitializingBean {
    /*
    first - need to update?
    second - message, if needed
    * */
    Pair<Boolean, String> checkUpdate(Context context);

    long loadNewVersion(Context context);
}
