package com.badr.infodota.player.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:58
 */
public class PlayersGroupLoadRequest extends TaskRequest<Unit.List> {

    private Unit.Groups mGroup;
    private Context mContext;

    public PlayersGroupLoadRequest(Context context, Unit.Groups group) {
        super(Unit.List.class);
        mContext = context;
        mGroup = group;
    }

    @Override
    public Unit.List loadData() throws Exception {
        PlayerService playerService = BeanContainer.getInstance().getPlayerService();
        return playerService.getAccountsByGroup(mContext, mGroup);

    }
}