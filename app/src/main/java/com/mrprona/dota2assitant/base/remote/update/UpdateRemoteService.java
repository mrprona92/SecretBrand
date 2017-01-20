package com.mrprona.dota2assitant.base.remote.update;

import android.content.Context;
import android.util.Pair;

import com.mrprona.dota2assitant.base.api.Update;
import com.mrprona.dota2assitant.base.remote.BaseRemoteService;

/**
 * Created by Badr on 17.02.2015.
 */
public interface UpdateRemoteService extends BaseRemoteService {
    Pair<Update, String> getUpdate(Context context) throws Exception;
}
