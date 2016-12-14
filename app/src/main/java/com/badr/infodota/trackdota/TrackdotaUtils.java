package com.badr.infodota.trackdota;

import android.content.Context;
import android.text.TextUtils;

import com.badr.infodota.R;
import com.badr.infodota.trackdota.api.game.Team;

/**
 * Created by ABadretdinov
 * 16.04.2015
 * 12:11
 */
public class TrackdotaUtils {
    public static final int RADIANT = 0;
    public static final int DIRE = 1;

    public static String getTeamName(Team team, int align) {
        return team != null && !TextUtils.isEmpty(team.getName()) ? team.getName() : align == RADIANT ? "Radiant" : "Dire";
    }

    public static String getTeamTag(Team team, int align) {
        return team != null && !TextUtils.isEmpty(team.getTag()) ? team.getTag() : align == RADIANT ? "Radiant" : "Dire";
    }

    public static String getTeamImageUrl(Team team) {
        return "http://www.trackdota.com/data/images/teams/" + team.getId() + ".png";
    }

    public static String getLeagueImageUrl(long leagueId) {
        return "http://www.trackdota.com/data/images/leagues/" + leagueId + ".jpg";
    }

    public static String getGameStatus(Context context, int status) {
        switch (status) {
            case 1:
                return context.getString(R.string.in_hero_selection);
            case 2:
                return context.getString(R.string.waiting_for_horn);
            case 3:
                return context.getString(R.string.in_progress);
            case 4:
                return context.getString(R.string.finished);
            default:
                return context.getString(R.string.match_status_unknown);

        }
    }
}
