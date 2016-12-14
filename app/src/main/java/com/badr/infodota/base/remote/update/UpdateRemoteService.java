package com.badr.infodota.base.remote.update;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.base.api.Update;
import com.badr.infodota.base.remote.BaseRemoteService;

/**
 * Created by Badr on 17.02.2015.
 */
public interface UpdateRemoteService extends BaseRemoteService {
    Pair<Update, String> getUpdate(Context context) throws Exception;
}
