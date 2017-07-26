package com.mrprona.dota2assitant.base.api;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:35
 */

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public interface Constants {
    boolean INITIALIZATION = false;
    int MILLIS_FOR_EXIT = 2000;
    String STEAM_API_URL = "https://api.steampowered.com/IDOTA2Match_570/";

    String GITHUB_LAST_APK_URL = "https://github.com/mrprona92/SecretBrand/blob/master/Dota2Assistant.apk?raw=true";
    String GITHUB_UPDATE_URL = "https://github.com/mrprona92/SecretBrand/blob/master/updates.json?raw=true";
    String GOSUGAMER_RANKINGTEAM= "http://www.gosugamers.net/dota2/rankings";

    String RANKINGTEAM0= "http://www.gosugamers.net/dota2/leaderboards/leaderboard?division=0";
    String RANKINGTEAM1= "http://www.gosugamers.net/dota2/leaderboards/leaderboard?division=1";
    String RANKINGTEAM2= "http://www.gosugamers.net/dota2/leaderboards/leaderboard?division=2";
    String RANKINGTEAM3= "http://www.gosugamers.net/dota2/leaderboards/leaderboard?division=3";

    interface History {
        String SUBURL = STEAM_API_URL + "GetMatchHistory/V001/?key=";
        String START_AT_MATCH_ID = "&start_at_match_id=";
        String LEAGUE_ID = "&league_id=";
    }

    interface Team {
        String SUBURL = "http://api.steampowered.com/ISteamRemoteStorage/GetUGCFileDetails/v1/?key={0}&appid=570&ugcid={1}";
    }

    interface DotaBuff {
        String SEARCH_URL = "http://www.dotabuff.com/search?utf8=%E2%9C%93&q=";
    }

    interface Details {
        String SUBURL = STEAM_API_URL + "GetMatchDetails/V001/?key=";
        String MATCH_ID = "&match_id=";
        //player_name=<name> # Search matches with a player name, exact match only
        String PLAYER_NAME = "&player_name=";
        //hero_id=<id> # Search for matches with a specific hero being played, hero id's are in dota/scripts/npc/npc_heroes.txt in your Dota install directory
        String HERO_ID = "&hero_id=";
        //skill=<skill>  # 0 for any, 1 for normal, 2 for high, 3 for very high skill
        String SKILL = "&skill=";
        //date_min=<date> # date in UTC seconds since Jan 1, 1970 (unix time format)
        String DATE_MIN = "&date_min=";
        //date_max=<date> # date in UTC seconds since Jan 1, 1970 (unix time format)
        String DATE_MAX = "&date_max=";
        //account_id=<id> # Steam account id (this is not SteamID, its only the account number portion)
        String ACCOUNT_ID = "&account_id=";
        //league_id=<id> # matches for a particular league
        String LEAGUE_ID = "&league_id=";
        //start_at_match_id=<id> # Start the search at the indicated match id, descending
        String START_AT_MATCH_ID = "&start_at_match_id=";
        //matches_requested=<n> # Defaults is 25 matches, this can limit to less
        String MATCHES_REQUESTED = "&matches_requested=";

    }

    interface Heroes {
        String SUBURL = "https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?language=eu_us&key=";
        String STATS_URL = "http://dotaheroes.herokuapp.com/heroes/";
        String SB_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_sb.png";
        String VERT_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_vert.jpg";
        String FULL_IMAGE_URL = "http://media.steampowered.com/apps/dota2/images/heroes/{0}_full.png";
        String MINIMAP_IMAGE_URL = "http://dota2.cyborgmatt.com/minimap_icons/{0}_icon.png";
        String DOTA2_HEROPEDIA_URL = "http://www.dota2.com/hero/{0}/?l={1}";
        String DOTA2_WIKI_RESPONSES_URL = "http://dota2.gamepedia.com/{0}_responses";
    }

    interface TruePicker {
        String SUBURL = "http://truepicker.com/ru/cap?CaptainV4[action_type]=&CaptainV4[action_data]=&CaptainV4[ban_list]=";//&allies=&enemies=8&info=1&count=108&dota2=0&roles=&level=0
    }

    interface TwitchTV {
        String SUBURL = "https://api.twitch.tv/kraken/";
        String DOTA_GAMES = SUBURL + "streams?game=Dota%202&hls=true";
        String STREAM_SUBURL = SUBURL + "streams/";
        String ACCESS_TOKEN_URL = "https://api.twitch.tv/api/channels/{0}/access_token";
        String M3U8_URL = "http://usher.twitch.tv/api/channel/hls/{0}.m3u8?token={1}&sig={2}";
        String PREVIEW_URL = "http://static-cdn.jtvnw.net/previews-ttv/live_user_{0}-200x100.jpg";
    }

    interface Cosmetics {
        String ITEM_BASE_URL = "http://cdn.dota2.com/apps/570/{0}";
        String SUBURL = "https://api.steampowered.com/IEconItems_570/GetSchema/v0001/?key={0}&language={1}";
        /*public static final String SUBURL="https://raw.githubusercontent.com/SchemaTracker/SteamEcon/master/cache/schema_570.json";*/
        //public static final String SUBURL="http://api.steampowered.com/IEconItems_570/GetSchemaURL/v1/?key={0}&language={1}";
        String PRICES_URL = "http://api.steampowered.com/ISteamEconomy/GetAssetPrices/v0001?language=en&appid=570&key=";
        String PLAYER_ITEMS_URL = "http://api.steampowered.com/IEconItems_570/GetPlayerItems/v0001/?key={0}&SteamID={1}";
        //help http://wiki.teamfortress.com/wiki/WebAPI/GetSchema
        //steam64id = 76561197960265728+account_Id
    }

    interface Players {
        String SUBURL = "http://api.steampowered.com/isteamuser/getplayersummaries/v0002/?key={0}&steamids={1}";
        String FRIENDS = "http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key={0}&steamid={1}";
    }

    interface News {
        String SUBURL = "http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=570&count=50&format=json";
        String ENDDATE = "&enddate={0}";
    }

    interface TI4 {
        String PRIZEPOOL = "http://api.steampowered.com/IEconDOTA2_570/GetTournamentPrizePool/v1?key={0}&leagueid=600";
        //todo отображать часть html в webview: http://stackoverflow.com/questions/12257929/display-a-part-of-the-webpage-on-the-webview-android
    }

	/*  http://dev.dota2.com/showthread.php?t=58317
    * (GetPlayerSummaries)           https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/
	* (GetLeagueListing)             https://api.steampowered.com/IDOTA2Match_570/GetLeagueListing/v0001/ показывает все имеющиеся лиги
	* (GetLiveLeagueGames)           https://api.steampowered.com/IDOTA2Match_570/GetLiveLeagueGames/v0001/  не показывается, т.к. нет билетов
	* картинки лиг берем из шмоток
	* (GetMatchHistoryBySequenceNum) https://api.steampowered.com/IDOTA2Match_570/GetMatchHistoryBySequenceNum/v0001/
	* (GetTeamInfoByTeamID)          https://api.steampowered.com/IDOTA2Match_570/GetTeamInfoByTeamID/v001/
	* (GetHeroesStats)               http://dotaheroes.herokuapp.com/
*/
    //https://developer.valvesoftware.com/wiki/Steam_Web_API#GetNewsForApp_.28v0001.29 описание
    //новости http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=570&count=3&maxlength=300&format=json
    //http://wiki.teamfortress.com/wiki/WebAPI#Dota_2
    // todo тип рарок http://api.steampowered.com/IEconDOTA2_570/GetRarities/v1/?language=en&key=54E61DBFB0A2D4A1B24B4C7EC5C5EFFD
    //https://developer.valvesoftware.com/wiki/Steam_Web_API
    //todo http://wiki.teamfortress.com/wiki/WebAPI#Dota_2
    //https://api.steampowered.com/IEconDOTA2_570/GetHeroes/v0001/?key=08D221D8D34304557A600C9CEE197124&language=fr
    //чье-то api для будущих матчей - http://dota2matches.pp.ua/index.php/api/matches/get
    //stickylistHeadersListView  SectionIndexerAdapterWrapper
    //http://www.joindota.com/en/matches


    public static final float ALPHA_HIGH = 0.75f;
    public static final float ALPHA_LOW = 0.25f;
    public static final float ALPHA_MAX = 1.0f;
    public static final float ALPHA_MID = 0.5f;
    public static final boolean ANALYTICS_LOGGING = true;
    public static final long ANIMATION_DURATION_IN = 225;
    public static final long ANIMATION_DURATION_LONG = 300;
    public static final long ANIMATION_DURATION_MEDIUM = 200;
    public static final long ANIMATION_DURATION_OUT = 195;
    public static final long ANIMATION_DURATION_SHORT = 100;
    public static final long ANIMATION_DURATION_VERY_LONG = 500;
    public static final long ANIMATION_DURATION_VERY_SHORT = 50;

    public static final long ANIMATION_TIMEOUT_DEFAULT = 2000;
    public static boolean BRACKETS_ANIMATIONS_ENABLED = false;
    public static boolean BRACKETS_RESIZING_ENABLED = false;
    public static final boolean CLOUDINARY_LOGGING = false;
    public static String COLOR_BRIGHT_700 = null;
    public static String COLOR_DARK_300 = null;
    public static final long DELAY_POLLING = 30000;
    public static final boolean EXCESSIVE_LOGGING = false;
    public static final boolean EXPERIMENTAL_ANIMATIONS_ON = false;
    public static final long NOTIFYDATASETCHANGED_DELAY = 100;
    public static final float PARALLAX_MULTIPLIER = 1.3f;
    public static final int PASSWORD_MINIMUM_CHARS = 3;
    public static final int SCROLL_THRESHOLD_MINIMUM = 3;
    public static final String SHORTCUT_DISCOVER = "com.strafe.android.SHORTCUT_DISCOVER";
    public static final String SHORTCUT_FEED = "com.strafe.android.SHORTCUT_FEED";
    public static final String SHORTCUT_PROFILE = "com.strafe.android.SHORTCUT_PROFILE";
    public static final String SHORTCUT_TODAY = "com.strafe.android.SHORTCUT_TODAY";
    public static final boolean SHOW_USER_PRINT = false;
    public static final int TOOLTIP_FEED_LANGUAGE_SETTINGS = 2502;
    public static final int TOOLTIP_LIVE_MATCHES = 2500;
    public static final int TOOLTIP_LIVE_MATCHES_EMPTY = 2501;
    public static final int TOOLTIP_MATCH_NOTIFICATIONS = 1;
    public static final boolean TRACKING_SESSION = false;
    public static final boolean TWITCH_ENABLED = false;
    public static final boolean WEAK_REFERENCE_LOGGING = false;




}

