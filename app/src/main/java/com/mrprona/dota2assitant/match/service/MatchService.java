package com.mrprona.dota2assitant.match.service;

import android.content.Context;

import com.mrprona.dota2assitant.match.api.detailed.DetailedMatchHolder;
import com.mrprona.dota2assitant.match.api.history.PlayerMatchResult;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:18
 */
public interface MatchService {
    DetailedMatchHolder getMatchDetails(Context context, String matchId);

    PlayerMatchResult getMatches(Context context, Long accountId, Long fromMatchId, Long heroId);
}
