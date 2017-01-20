package com.mrprona.dota2assitant.player.task;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.service.PlayerService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:56
 */
public class SteamFriendsLoadRequest extends TaskRequest<Unit.List> {
    private long mAccountId;

    public SteamFriendsLoadRequest(long accountId) {
        super(Unit.List.class);
        mAccountId = accountId;
    }

    @Override
    public Unit.List loadData() throws Exception {
        PlayerService playerService = BeanContainer.getInstance().getPlayerService();
        return new Unit.List(playerService.loadSteamFriends(mAccountId));
    }
}