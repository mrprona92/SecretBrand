package com.mrprona.dota2assitant.joindota.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.joindota.api.MatchItem;
import com.mrprona.dota2assitant.joindota.service.JoinDotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:49
 */
public class JoindotaMatchesLoadRequest extends TaskRequest<MatchItem.List> {

    private int mPage;
    private Context mContext;
    private String mExtraParams;

    public JoindotaMatchesLoadRequest(Context context, String extraParams, int page) {
        super(MatchItem.List.class);
        this.mPage = page;
        this.mContext = context;
        this.mExtraParams = extraParams;
    }

    @Override
    public MatchItem.List loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        JoinDotaService joinDotaService = container.getJoinDotaService();
        return joinDotaService.getMatchItems(mContext, mPage, mExtraParams);
    }
}
