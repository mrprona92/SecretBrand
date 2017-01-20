package com.mrprona.dota2assitant.base.remote;

import com.mrprona.dota2assitant.base.api.ti4.PrizePoolHolder;
import com.mrprona.dota2assitant.cosmetic.api.icon.ItemIconHolder;
import com.mrprona.dota2assitant.cosmetic.api.player.PlayerCosmeticItem;
import com.mrprona.dota2assitant.cosmetic.api.price.ItemsPricesHolder;
import com.mrprona.dota2assitant.cosmetic.api.store.StoreItemsHolder;
import com.mrprona.dota2assitant.match.api.detailed.DetailedMatchHolder;
import com.mrprona.dota2assitant.match.api.history.MatchResultHolder;
import com.mrprona.dota2assitant.match.api.team.LogoUrlHolder;
import com.mrprona.dota2assitant.news.api.AppNewsHolder;
import com.mrprona.dota2assitant.player.api.PlayersResult;
import com.mrprona.dota2assitant.player.api.friends.FriendsResult;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ABadretdinov
 * 16.03.2015
 * 14:17
 */
public interface SteamService {

    @GET("/ISteamUser/getplayersummaries/v0002/")
    PlayersResult getPlayers(@Query("steamids") String steamids);

    @GET("/ISteamUser/GetFriendList/v0001/")
    FriendsResult getFriends(@Query("steamid") String steamId);

    @GET("/ISteamRemoteStorage/GetUGCFileDetails/v1/?appid=570")
    LogoUrlHolder getTeamLogo(@Query("ugcid") long logoId);

    @GET("/IEconDOTA2_570/GetTournamentPrizePool/v1")
//ti4 = 600
    PrizePoolHolder getLeaguePrizePool(@Query("leagueid") long leagueId);

    @GET("/ISteamNews/GetNewsForApp/v0002/?appid=570&count=50&format=json")
    AppNewsHolder getNews(@Query("enddate") Long enddate);

    @GET("/IDOTA2Match_570/GetMatchDetails/V001/")
    DetailedMatchHolder getMatchDetails(@Query("match_id") String matchId);

    @GET("/IDOTA2Match_570/GetMatchHistory/V001/")
    MatchResultHolder getMatchHistory(
            @Query("account_id") Long accountId,
            @Query("start_at_match_id") Long startMatchId,
            @Query("hero_id") Long heroId
    );

    @GET("/IEconItems_570/GetSchemaURL/v1/")
    StoreItemsHolder getCosmeticItems(@Query("language") String language);

    @GET("/ISteamEconomy/GetAssetPrices/v0001?language=en&appid=570")
    ItemsPricesHolder getCosmeticItemsPrices();

    @GET("/IEconItems_570/GetPlayerItems/v0001/")
    List<PlayerCosmeticItem> getPlayerCosmeticItems(@Query("steamId") long steamId);

    @GET("/IEconDOTA2_570/GetItemIconPath/v1/?format=json")
    ItemIconHolder getItemIconPath(@Query("iconname") String iconname);
}