package com.badr.infodota.match.service;

import android.content.Context;

import com.badr.infodota.match.api.detailed.DetailedMatchHolder;
import com.badr.infodota.match.api.history.PlayerMatchResult;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:18
 */
public interface MatchService {
    DetailedMatchHolder getMatchDetails(Context context, String matchId);

    PlayerMatchResult getMatches(Context context, Long accountId, Long fromMatchId, Long heroId);
}
