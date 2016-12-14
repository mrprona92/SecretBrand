package com.badr.infodota.match.service.team;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.match.api.detailed.Team;

/**
 * User: ABadretdinov
 * Date: 15.05.14
 * Time: 17:06
 */
public interface TeamService extends InitializingBean {
    String getTeamLogo(Context context, long id);

    void saveTeam(Context context, Team team);

    Team getTeamById(Context context, long id);

}
