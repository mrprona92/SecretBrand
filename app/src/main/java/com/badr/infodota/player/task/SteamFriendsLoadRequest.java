package com.badr.infodota.player.task;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;

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