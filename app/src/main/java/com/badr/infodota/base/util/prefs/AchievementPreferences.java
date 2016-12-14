package com.badr.infodota.base.util.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.badr.infodota.quiz.activity.QuizActivity;

/**
 * User: ABadretdinov
 * Date: 12.02.14
 * Time: 17:07
 */
public class AchievementPreferences {
    public static final String FIRST_RUN = "first_run";
    public static final String IM_EVERYWHERE = "im_everywhere";
    public static final String PERFECT_ATTEMPT = "perfect_attempt";
    public static final String LOLER = "loler";
    public static final String NEWBIE = "newbie";
    public static final String LITTLE_BETTER = "little_better";
    public static final String NOT_BAD = "not_bad";
    public static final String HIGH_FIVE = "high_five";
    public static final String PRO = "pro";
    public static final String PROUD_OF_YOU = "proud_of_you";
    public static final String CHEATER = "cheater";
    private SharedPreferences preferences;


    public AchievementPreferences(Context context) {
        preferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE);
    }

    public boolean isFirstGameUnlocked() {
        return preferences.getBoolean(FIRST_RUN, false);
    }

    public void setFirstGameUnlocked() {
        preferences.edit().putBoolean(FIRST_RUN, true).commit();
    }

    public boolean isEverywhere() {
        return preferences.getBoolean(IM_EVERYWHERE + QuizActivity.MODE_NONE, false)
                && preferences.getBoolean(IM_EVERYWHERE + QuizActivity.MODE_ITEMS, false)
                && preferences.getBoolean(IM_EVERYWHERE + QuizActivity.MODE_HEROES, false);
    }

    public void setEverywhereMode(int mode) {
        preferences.edit().putBoolean(IM_EVERYWHERE + mode, true).commit();
    }

    public boolean isImEverywhere() {
        return preferences.getBoolean(IM_EVERYWHERE, false);
    }

    public void setImEverywhere() {
        preferences.edit().putBoolean(IM_EVERYWHERE + QuizActivity.MODE_NONE, true).commit();
        preferences.edit().putBoolean(IM_EVERYWHERE + QuizActivity.MODE_ITEMS, true).commit();
        preferences.edit().putBoolean(IM_EVERYWHERE + QuizActivity.MODE_HEROES, true).commit();
        preferences.edit().putBoolean(IM_EVERYWHERE, true).commit();
    }

    public boolean isPerfectAttemptUnlocked() {
        return preferences.getBoolean(PERFECT_ATTEMPT, false);
    }

    public void setPerfectAttemptUnlocked() {
        preferences.edit().putBoolean(PERFECT_ATTEMPT, true).commit();
    }

    public boolean isLoLerUnlocked() {
        return preferences.getBoolean(LOLER, false);
    }

    public void setLoLerUnlocked() {
        preferences.edit().putBoolean(LOLER, true).commit();
    }

    public boolean isNewbieBetterUnlocked() {
        return preferences.getBoolean(NEWBIE, false);
    }

    public void setNewbieUnlocked() {
        preferences.edit().putBoolean(NEWBIE, true).commit();
    }

    public boolean isLittleBetterUnlocked() {
        return preferences.getBoolean(LITTLE_BETTER, false);
    }

    public void setLittleBetterUnlocked() {
        preferences.edit().putBoolean(LITTLE_BETTER, true).commit();
    }

    public boolean isNotBadUnlocked() {
        return preferences.getBoolean(NOT_BAD, false);
    }

    public void setNotBadUnlocked() {
        preferences.edit().putBoolean(NOT_BAD, true).commit();
    }

    public boolean isHighFiveUnlocked() {
        return preferences.getBoolean(HIGH_FIVE, false);
    }

    public void setHighFiveUnlocked() {
        preferences.edit().putBoolean(HIGH_FIVE, true).commit();
    }

    public boolean isProUnlocked() {
        return preferences.getBoolean(PRO, false);
    }

    public void setProUnlocked() {
        preferences.edit().putBoolean(PRO, true).commit();
    }

    public boolean isProudOfYouUnlocked() {
        return preferences.getBoolean(PROUD_OF_YOU, false);
    }

    public void setProudOfYouUnlocked() {
        preferences.edit().putBoolean(PROUD_OF_YOU, true).commit();
    }

    public boolean isCheaterUnlocked() {
        return preferences.getBoolean(CHEATER, false);
    }

    public void setCheaterUnlocked() {
        preferences.edit().putBoolean(CHEATER, true).commit();
    }

    public void clear() {
        preferences.edit().clear().commit();
    }
}
