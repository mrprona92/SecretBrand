package com.badr.infodota.match.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.api.detailed.AdditionalUnit;
import com.badr.infodota.match.api.detailed.DetailedMatch;
import com.badr.infodota.match.api.detailed.DetailedMatchHolder;
import com.badr.infodota.match.service.MatchService;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:38
 */
public class MatchDetailsLoadRequest extends TaskRequest<DetailedMatch> {

    private DetailedMatch matchDetailedMatch;
    private String matchId;
    private Context mContext;

    public MatchDetailsLoadRequest(Context context, DetailedMatch matchDetailedMatch, String matchId) {
        super(DetailedMatch.class);
        this.mContext = context;
        this.matchDetailedMatch = matchDetailedMatch;
        this.matchId = matchId;
    }

    @Override
    public DetailedMatch loadData() throws Exception {
        BeanContainer container = BeanContainer.getInstance();
        MatchService matchService = container.getMatchService();
        PlayerService playerService = container.getPlayerService();
        HeroService heroService = container.getHeroService();
        if (matchId != null) {
            DetailedMatchHolder result = matchService.getMatchDetails(mContext, matchId);
            if (result != null) {
                matchDetailedMatch = result.getDetailedMatch();
            }
        }
        if (matchDetailedMatch != null) {
            List<Player> players = matchDetailedMatch.getPlayers();
            if (players != null && players.size() > 0) {
                List<Long> ids = new ArrayList<Long>();
                for (Player player : players) {
                    if (player.getAccountId() != Player.HIDDEN_ID) {
                        ids.add(player.getAccountId());
                    }
                }
                if (ids.size() > 0) {
                    Unit.List unitsResult = playerService.loadAccounts(ids);
                    if (unitsResult != null && unitsResult.size() > 0) {
                        for (Unit unit : unitsResult) {
                            playerService.saveAccount(mContext, unit);
                        }
                        for (Player player : players) {
                            if (player.getAccountId() != Player.HIDDEN_ID) {
                                player.setAccount(playerService.getAccountById(mContext, player.getAccountId()));
                            }
                            player.setHero(heroService.getHeroById(mContext, player.getHeroId()));
                            loadPlayerItems(player);
                        }
                    }
                }
            }

        }
        return matchDetailedMatch;
    }

    private void loadPlayerItems(Player player) {
        BeanContainer container = BeanContainer.getInstance();
        ItemService itemService = container.getItemService();
        Item current = itemService.getItemById(mContext, player.getItem0());
        if (current != null) {
            player.setItem0dotaId(current.getDotaId());
        }
        current = itemService.getItemById(mContext, player.getItem1());
        if (current != null) {
            player.setItem1dotaId(current.getDotaId());
        }
        current = itemService.getItemById(mContext, player.getItem2());
        if (current != null) {
            player.setItem2dotaId(current.getDotaId());
        }
        current = itemService.getItemById(mContext, player.getItem3());
        if (current != null) {
            player.setItem3dotaId(current.getDotaId());
        }
        current = itemService.getItemById(mContext, player.getItem4());
        if (current != null) {
            player.setItem4dotaId(current.getDotaId());
        }
        current = itemService.getItemById(mContext, player.getItem5());
        if (current != null) {
            player.setItem5dotaId(current.getDotaId());
        }
        if (player.getAdditionalUnits() != null) {
            for (AdditionalUnit unit : player.getAdditionalUnits()) {
                current = itemService.getItemById(mContext, unit.getItem0());
                if (current != null) {
                    unit.setItem0dotaId(current.getDotaId());
                }
                current = itemService.getItemById(mContext, unit.getItem1());
                if (current != null) {
                    unit.setItem1dotaId(current.getDotaId());
                }
                current = itemService.getItemById(mContext, unit.getItem2());
                if (current != null) {
                    unit.setItem2dotaId(current.getDotaId());
                }
                current = itemService.getItemById(mContext, unit.getItem3());
                if (current != null) {
                    unit.setItem3dotaId(current.getDotaId());
                }
                current = itemService.getItemById(mContext, unit.getItem4());
                if (current != null) {
                    unit.setItem4dotaId(current.getDotaId());
                }
                current = itemService.getItemById(mContext, unit.getItem5());
                if (current != null) {
                    unit.setItem5dotaId(current.getDotaId());
                }
            }
        }
    }
}