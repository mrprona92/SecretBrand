package com.mrprona.dota2assitant.match.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.util.FileUtils;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.service.HeroService;
import com.mrprona.dota2assitant.match.api.Player;
import com.mrprona.dota2assitant.match.api.detailed.DetailedMatchHolder;
import com.mrprona.dota2assitant.match.api.history.HistoryMatch;
import com.mrprona.dota2assitant.match.api.history.MatchResultHolder;
import com.mrprona.dota2assitant.match.api.history.PlayerMatch;
import com.mrprona.dota2assitant.match.api.history.PlayerMatchResult;
import com.google.gson.Gson;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 16:18
 */
public class MatchServiceImpl implements MatchService {

    @Override
    public DetailedMatchHolder getMatchDetails(Context context, String matchId) {
        File externalFilesDir = FileUtils.externalFileDir(context);
        String matchResult = FileUtils.getTextFromFile(externalFilesDir.getAbsolutePath() + File.separator + "matches" + File.separator + matchId + ".json");
        DetailedMatchHolder result;
        if (TextUtils.isEmpty(matchResult)) {
            result = BeanContainer.getInstance().getSteamService().getMatchDetails(matchId);
            if (result != null) {
                FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "matches" + File.separator + matchId + ".json",
                        result);
            }
        } else {
            result = new Gson().fromJson(matchResult, DetailedMatchHolder.class);
        }
        return result;
    }

    @Override
    public PlayerMatchResult getMatches(Context context, Long accountId, Long fromMatchId,
                                        Long heroId) {
        try {
            MatchResultHolder result = BeanContainer.getInstance().getSteamService().getMatchHistory(accountId, fromMatchId, heroId);
            if (result != null && result.getHistoryMatchResult() != null) {
                HeroService heroService = BeanContainer.getInstance().getHeroService();
                List<HistoryMatch> historyMatches = result.getHistoryMatchResult().getHistoryMatches();
                PlayerMatchResult playerMatchResult = new PlayerMatchResult();
                playerMatchResult.setTotalMatches(result.getHistoryMatchResult().getTotalResults());
                playerMatchResult.setStatus(result.getHistoryMatchResult().getStatus());
                playerMatchResult.setStatusDetails(result.getHistoryMatchResult().getStatusDetail());
                PlayerMatch.List list = new PlayerMatch.List();
                if (historyMatches != null) {
                    for (HistoryMatch historyMatch : historyMatches) {
                        PlayerMatch playerMatch = new PlayerMatch();
                        playerMatch.setMatchId(historyMatch.getId());
                        playerMatch.setLobbyType(historyMatch.getLobbyType());
                        long timestamp = historyMatch.getStartTime();
                        playerMatch.setGameTime(new Date(timestamp * 1000));
                        List<Player> players = historyMatch.getPlayers();
                        boolean found = false;
                        for (int i = 0; i < players.size() && !found; i++) {
                            Player player = players.get(i);
                            if (player.getAccountId() == accountId) {
                                found = true;
                                Hero hero = heroService.getHeroById(context, player.getHeroId());
                                if (hero != null) {
                                    player.setHero(hero);
                                    playerMatch.setPlayer(player);
                                    list.add(playerMatch);
                                }
                            }
                        }
                    }
                }
                playerMatchResult.setPlayerMatches(list);
                return playerMatchResult;
            } else {
                String message = "Failed to get matches for accountId=" + accountId;
                Log.e(MatchServiceImpl.class.getName(), message);
            }
            return null;
        } catch (Exception e) {
            String message = "Failed to get matches, cause: " + e.getMessage();
            Log.e(MatchServiceImpl.class.getName(), message, e);
            return null;
        }
    }
}
