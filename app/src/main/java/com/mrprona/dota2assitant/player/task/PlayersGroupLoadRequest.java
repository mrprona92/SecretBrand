package com.mrprona.dota2assitant.player.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.service.PlayerService;

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