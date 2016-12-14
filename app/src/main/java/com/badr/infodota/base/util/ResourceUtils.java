package com.badr.infodota.base.util;

import android.content.Context;
import android.content.res.Resources;

import com.badr.infodota.R;

/**
 * User: ABadretdinov
 * Date: 20.02.14
 * Time: 11:33
 */
public class ResourceUtils {

    public static final int[] COSMETIC_ITEM_RARITY_IDS = {android.R.color.white, R.color.common, R.color.uncommon, R.color.rare, R.color.mythical, R.color.legendary, R.color.ancient, R.color.immortal, R.color.arcana};
    public static final int[] COSMETIC_ITEM_QUALITY_IDS = {
            R.color.quality_normal,
            R.color.quality_genuine,
            R.color.quality_vintage,
            R.color.quality_unusual,
            R.color.quality_unique,
            R.color.quality_community,
            R.color.quality_developer,
            R.color.quality_selfmade,
            R.color.quality_customized,
            R.color.quality_strange,
            R.color.quality_completed,
            R.color.quality_haunted,
            R.color.quality_tournament,
            R.color.quality_favored,
            R.color.quality_ascendant,
            R.color.quality_autographed,
            R.color.quality_legacy,
            R.color.quality_exalted,
            R.color.quality_frozen,
            R.color.quality_corrupted,
            R.color.quality_lucky
    };

    public static int getRoleDrawable(String role) {
        int drawableId = 0;
        if ("Carry".equals(role)) {
            drawableId = R.drawable.carry;
        } else if ("Disabler".equals(role)) {
            drawableId = R.drawable.disabler;
        } else if ("Durable".equals(role)) {
            drawableId = R.drawable.durable;
        } else if ("Escape".equals(role)) {
            drawableId = R.drawable.escape;
        } else if ("Initiator".equals(role)) {
            drawableId = R.drawable.initiator;
        } else if ("Jungler".equals(role)) {
            drawableId = R.drawable.junger;
        } else if ("Lane Support".equals(role)) {
            drawableId = R.drawable.lane_support;
        } else if ("Nuker".equals(role)) {
            drawableId = R.drawable.nuker;
        } else if ("Pusher".equals(role)) {
            drawableId = R.drawable.pusher;
        } else if ("Support".equals(role)) {
            drawableId = R.drawable.support;
        }
        return drawableId;
    }

    public static String getItemType(int id) {
        switch (id) {
            default:
            case 0:
                return null;
            case 1:
                return "arcane";
            case 2:
                return "armaments";
            case 3:
                return "armor";
            case 4:
                return "artifacts";
            case 5:
                return "attributes";
            case 6:
                return "caster";
            case 7:
                return "common";
            case 8:
                return "consumable";
            case 9:
                return "support";
            case 10:
                return "secret_shop";
            case 11:
                return "weapons";
        }
    }

    public static String getLocalizedItemType(Context context, String code) {
        Resources resources = context.getResources();
        String[] array = resources.getStringArray(R.array.item_types);
        if ("arcane".equals(code)) {
            return array[1];
        } else if ("armaments".equals(code)) {
            return array[2];
        } else if ("armor".equals(code)) {
            return array[3];
        } else if ("artifacts".equals(code)) {
            return array[4];
        } else if ("attributes".equals(code)) {
            return array[5];
        } else if ("caster".equals(code)) {
            return array[6];
        } else if ("common".equals(code)) {
            return array[7];
        } else if ("consumable".equals(code)) {
            return array[8];
        } else if ("support".equals(code)) {
            return array[9];
        } else if ("secret_shop".equals(code)) {
            return array[10];
        } else if ("weapons".equals(code)) {
            return array[11];
        }
        return "";
    }

    public static String getHeroRole(int id) {
        switch (id) {
            default:
            case 0:
                return null;
            case 1:
                return "Carry";
            case 2:
                return "Disabler";
            case 3:
                return "Durable";
            case 4:
                return "Escape";
            case 5:
                return "Initiator";
            case 6:
                return "Jungler";
            case 7:
                return "Lane Support";
            case 8:
                return "Nuker";
            case 9:
                return "Pusher";
            case 10:
                return "Support";
        }
    }

    public static String getLocalizedHeroRole(Context context, String code) {
        Resources res = context.getResources();
        String[] array = res.getStringArray(R.array.hero_roles);
        if ("Carry".equals(code)) {
            return array[1];
        } else if ("Disabler".equals(code)) {
            return array[2];
        } else if ("Durable".equals(code)) {
            return array[3];
        } else if ("Escape".equals(code)) {
            return array[4];
        } else if ("Initiator".equals(code)) {
            return array[5];
        } else if ("Jungler".equals(code)) {
            return array[6];
        } else if ("Lane Support".equals(code)) {
            return array[7];
        } else if ("Nuker".equals(code)) {
            return array[8];
        } else if ("Pusher".equals(code)) {
            return array[9];
        } else if ("Support".equals(code)) {
            return array[10];
        }
        return array[0];
    }

    //todo передавать контекст и возвращать String, иначе куча ошибок будет
    public static String getStatsHeader(Context context, String header) {
        int id;
        if ("Highest Gold Per Minute".equals(header)) {
            id = R.string.highest_gpm;
        } else if ("Highest Experience Per Minute".equals(header)) {
            id = R.string.highest_xpm;
        } else if ("Highest Kills Per Minute".equals(header)) {
            id = R.string.highest_kills_pm;
        } else if ("Highest Deaths Per Minute".equals(header)) {
            id = R.string.highest_deaths_pm;
        } else if ("Highest Assists Per Minute".equals(header)) {
            id = R.string.highest_assists_pm;
        } else if ("Highest Last Hits Per Minute".equals(header)) {
            id = R.string.highest_lh_pm;
        } else if ("Highest Denies Per Minute".equals(header)) {
            id = R.string.highest_denies_pm;
        } else if ("Highest Hero Damage Per Minute".equals(header)) {
            id = R.string.highest_hd_pm;
        } else if ("Highest Hero Healing Per Minute".equals(header)) {
            id = R.string.highest_hh_pm;
        } else if ("Highest Tower Damage Per Minute".equals(header)) {
            id = R.string.highest_td_pm;
        } else if ("Most Kills".equals(header)) {
            id = R.string.highest_kills;
        } else if ("Most Gold".equals(header)) {
            id = R.string.highest_gold;
        } else if ("Most Experience".equals(header)) {
            id = R.string.highest_experience;
        } else if ("Most Deaths".equals(header)) {
            id = R.string.highest_deaths;
        } else if ("Most Assists".equals(header)) {
            id = R.string.highest_assists;
        } else if ("Most Last Hits".equals(header)) {
            id = R.string.highest_last_hits;
        } else if ("Most Denies".equals(header)) {
            id = R.string.highest_denies;
        } else if ("Most Hero Damage".equals(header)) {
            id = R.string.highest_hd;
        } else if ("Most Hero Healing".equals(header)) {
            id = R.string.highest_hh;
        } else if ("Longest Match".equals(header)) {
            id = R.string.longest_match_duration;
        } else if ("Most Tower Damage".equals(header)) {
            id = R.string.highest_td;
        } else if ("Best KDA Ratio".equals(header)) {
            id = R.string.best_kda;
        } else if ("Highest Kill Participation".equals(header)) {
            id = R.string.highest_kill_participation;
        } else if ("Longest Winning Streak".equals(header)) {
            id = R.string.longest_win_streak;
        } else if ("Longest Losing Streak".equals(header)) {
            id = R.string.longest_lost_streak;
        } else {
            return header;
        }
        return context.getString(id);
    }

    public static String getStatsResultTitle(Context context, String header) {
        int id;
        if ("Highest Gold Per Minute".equals(header)) {
            id = R.string.gpm_;
        } else if ("Highest Experience Per Minute".equals(header)) {
            id = R.string.xpm_;
        } else if ("Highest Kills Per Minute".equals(header)) {
            id = R.string.kills_;
        } else if ("Most Deaths Per Minute".equals(header)) {
            id = R.string.deaths_;
        } else if ("Highest Assists Per Minute".equals(header)) {
            id = R.string.assists_;
        } else if ("Highest Last Hits Per Minute".equals(header)) {
            id = R.string.last_hits_;
        } else if ("Highest Denies Per Minute".equals(header)) {
            id = R.string.denies_;
        } else if ("Highest Hero Damage Per Minute".equals(header)) {
            id = R.string.hero_damage_;
        } else if ("Highest Hero Healing Per Minute".equals(header)) {
            id = R.string.hero_healing_;
        } else if ("Highest Tower Damage Per Minute".equals(header)) {
            id = R.string.tower_damage_;
        } else if ("Most Kills".equals(header)) {
            id = R.string.kills_;
        } else if ("Most Gold".equals(header)) {
            id = R.string.gold_;
        } else if ("Most Experience".equals(header)) {
            id = R.string.experience_;
        } else if ("Most Deaths".equals(header)) {
            id = R.string.deaths_;
        } else if ("Most Assists".equals(header)) {
            id = R.string.assists_;
        } else if ("Most Last Hits".equals(header)) {
            id = R.string.last_hits_;
        } else if ("Most Denies".equals(header)) {
            id = R.string.denies_;
        } else if ("Most Hero Damage".equals(header)) {
            id = R.string.hero_damage_;
        } else if ("Most Hero Healing".equals(header)) {
            id = R.string.hero_healing_;
        } else if ("Longest Match".equals(header)) {
            id = R.string.match_length_;
        } else if ("Most Tower Damage".equals(header)) {
            id = R.string.tower_damage_;
        } else if ("Best KDA Ratio".equals(header)) {
            id = R.string.kda_;
        } else if ("Highest Kill Participation".equals(header)) {
            id = R.string.kill_participation_;
        } else if ("Longest Winning Streak".equals(header)) {
            id = R.string.win_streak_;
        } else if ("Longest Losing Streak".equals(header)) {
            id = R.string.lost_streak_;
        } else {
            return header;
        }
        return context.getString(id);
    }

    public static int getByHeroStatsHeaders(String header) {
        if ("Hero".equals(header)) {
            return R.string.hero_header;
        }
        if ("Gold / Minute".equals(header)) {
            return R.string.gpm_header;
        }
        if ("Experience / Minute".equals(header)) {
            return R.string.xpm_header;
        }
        if ("Matches Played".equals(header)) {
            return R.string.matches_played_header;
        }
        if ("Win Rate".equals(header)) {
            return R.string.winRate_header;
        }
        if ("KDA Ratio".equals(header)) {
            return R.string.kda_header;
        }
        if ("Kills".equals(header)) {
            return R.string.kills_header;
        }
        if ("Deaths".equals(header)) {
            return R.string.deaths_header;
        }
        if ("Assists".equals(header)) {
            return R.string.assists_header;
        } else {
            return 0;
        }
    }

    public static String getCosmeticItemType(int id) {
        switch (id) {
            default:
            case 0:
                return null;
            case 1:
                return "dota_item_wearable";
            case 2:
                return "player_card";
            case 3:
                return "tool";
            case 4:
                return "bundle";
            case 5:
                return "supply_crate";
        }
    }

}
