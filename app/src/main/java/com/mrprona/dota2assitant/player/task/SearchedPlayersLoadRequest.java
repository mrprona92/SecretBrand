package com.mrprona.dota2assitant.player.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.service.PlayerService;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:35
 */
public class SearchedPlayersLoadRequest extends TaskRequest<Unit.List> {
    private String searchText;
    private Context mContext;

    public SearchedPlayersLoadRequest(Context context, String search) {
        super(Unit.List.class);
        this.mContext = context;
        this.searchText = search;
    }

    @Override
    public Unit.List loadData() throws Exception {
        PlayerService playerService = BeanContainer.getInstance().getPlayerService();
        if (StringUtils.isEmpty(searchText)) {
            return playerService.getSearchedAccounts(mContext);
        } else {
            Unit.List result = playerService.loadAccounts(searchText);
            Unit.List players = new Unit.List();
            for (Unit unit : result) {
                Unit local = playerService.getAccountById(mContext, unit.getAccountId());
                if (local != null) {
                    unit.setGroup(local.getGroup());
                    unit.setLocalName(local.getLocalName());
                }
                players.add(unit);
            }
            return players;
        }
    }
}
