package com.mrprona.dota2assitant.joindota.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.joindota.api.MatchItem;
import com.mrprona.dota2assitant.joindota.service.JoinDotaService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 14:49
 */
public class JoindotaMatchLoadRequest extends TaskRequest<MatchItem> {

    private MatchItem mMatchItem;

    public JoindotaMatchLoadRequest(MatchItem matchItem) {
        super(MatchItem.class);
        this.mMatchItem = matchItem;
    }

    @Override
    public MatchItem loadData() throws Exception {
        JoinDotaService service = BeanContainer.getInstance().getJoinDotaService();
        return service.updateMatchItem(mMatchItem);
    }
}