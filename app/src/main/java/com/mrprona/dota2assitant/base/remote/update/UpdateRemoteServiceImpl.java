package com.mrprona.dota2assitant.base.remote.update;

import android.content.Context;
import android.util.Pair;

import com.mrprona.dota2assitant.base.api.Constants;
import com.mrprona.dota2assitant.base.api.Update;
import com.mrprona.dota2assitant.base.remote.BaseRemoteServiceImpl;

/**
 * Created by Badr on 17.02.2015.
 */
public class UpdateRemoteServiceImpl extends BaseRemoteServiceImpl implements UpdateRemoteService {
    @Override
    public Pair<Update, String> getUpdate(Context context) throws Exception {
        String url = Constants.GITHUB_UPDATE_URL;
        return basicRequestSend(context, url, Update.class);
    }
}
