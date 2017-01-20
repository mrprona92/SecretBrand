package com.mrprona.dota2assitant.joindota.service;

import android.content.Context;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.joindota.api.LiveStream;
import com.mrprona.dota2assitant.joindota.api.MatchItem;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:12
 */
public interface JoinDotaService extends InitializingBean {
    MatchItem.List getMatchItems(Context context, int page, String extraParams);

    MatchItem updateMatchItem(MatchItem item);

    String fillChannelName(List<LiveStream> streams);
}
