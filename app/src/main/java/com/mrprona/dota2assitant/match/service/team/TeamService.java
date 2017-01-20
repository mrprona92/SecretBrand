package com.mrprona.dota2assitant.match.service.team;

import android.content.Context;

import com.mrprona.dota2assitant.InitializingBean;
import com.mrprona.dota2assitant.match.api.detailed.Team;

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
