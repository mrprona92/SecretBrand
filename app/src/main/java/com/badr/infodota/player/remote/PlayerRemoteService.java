package com.badr.infodota.player.remote;

import com.badr.infodota.player.api.Unit;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 15:49
 */
public interface PlayerRemoteService {

    Unit.List getAccounts(List<Long> ids) throws Exception;

    Unit.List getAccounts(String name) throws Exception;

    Unit.List getSteamFriends(long id) throws Exception;
}
