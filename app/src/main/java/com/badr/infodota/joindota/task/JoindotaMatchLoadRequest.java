package com.badr.infodota.joindota.task;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.joindota.api.MatchItem;
import com.badr.infodota.joindota.service.JoinDotaService;

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