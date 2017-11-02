package com.mrprona.dota2assitant.joindota.customview;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.view.ContextThemeWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LocaleUtils {
    private static String TAG;
    private static boolean mIsUsingPortuguese;
    private static boolean mIsUsingPortugueseBrazilian;
    private static boolean mIsUsingRussian;
    private static boolean mIsUsingTurkish;
    private static Locale sLocale;

    static {
        TAG = "LocaleUtils";
        mIsUsingRussian = false;
        mIsUsingPortuguese = false;
        mIsUsingPortugueseBrazilian = false;
        mIsUsingTurkish = false;
    }

    public static void setLocale(Locale locale) {
        sLocale = locale;
        if (sLocale != null) {
            Locale.setDefault(sLocale);
        }
    }

    public static void updateConfig(ContextThemeWrapper wrapper) {
        if (sLocale != null && VERSION.SDK_INT >= 17) {
            Configuration configuration = new Configuration();
            configuration.setLocale(sLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(Application app, Configuration configuration) {
        updateConfig(app.getApplicationContext(), configuration);
    }

    public static void updateConfig(Context context, Configuration configuration) {
        if (sLocale != null) {
            Configuration config = new Configuration(configuration);
            config.locale = sLocale;
            if (VERSION.SDK_INT < 17) {
                Resources res = context.getResources();
                res.updateConfiguration(config, res.getDisplayMetrics());
            } else {
                context.createConfigurationContext(config);
            }
        }
    }

    public static Locale getLocale() {
        return sLocale;
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance(getLocale());
    }

    public static Locale getDefault(Context context) {
        return (Locale) getDefault(context, false).get(0);
    }

    public static List<Locale> getDefault(Context context, boolean getAllIfPossible) {
        List<Locale> list = new ArrayList();
        if (VERSION.SDK_INT >= 24) {
            LocaleList locales = context.getResources().getConfiguration().getLocales();
            for (int i = 0; i < locales.size(); i++) {
                Locale locale = locales.get(i);
                if (!getAllIfPossible) {
                    list.add(locale);
                    break;
                }
                list.add(locale);
            }
        } else {
            list.add(context.getResources().getConfiguration().locale);
        }
        return list;
    }

    public static boolean isLanguageSwitchingEnabled() {
        return isRussianEnabled() || isPortugueseEnabled() || isTurkishEnabled() || isPortugueseBrazilianEnabled();
    }

    public static boolean isRussianEnabled() {
        return mIsUsingRussian;
    }

    public static void setRussianEnabled(boolean enabled) {
        mIsUsingRussian = enabled;
    }

    public static void setPortugueseBrazilianEnabled(boolean enabled) {
        mIsUsingPortugueseBrazilian = enabled;
    }

    public static boolean isPortugueseBrazilianEnabled() {
        return mIsUsingPortugueseBrazilian;
    }

    public static boolean isPortugueseEnabled() {
        return mIsUsingPortuguese;
    }

    public static void setPortugueseEnabled(boolean enabled) {
        mIsUsingPortuguese = enabled;
    }

    public static boolean isTurkishEnabled() {
        return mIsUsingTurkish;
    }

    public static void setTurkishEnabled(boolean enabled) {
        mIsUsingTurkish = enabled;
    }

    public static String getLocaleString(Context context) {
        if (sLocale != null) {
            return sLocale.toString();
        }
        Locale locale = getDefault(context);
        if (locale != null) {
            return locale.toString();
        }
        return null;
    }

}
