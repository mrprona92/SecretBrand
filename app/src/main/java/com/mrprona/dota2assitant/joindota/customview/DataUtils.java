package com.mrprona.dota2assitant.joindota.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.format.DateFormat;
import android.util.StateSet;
import com.facebook.appevents.AppEventsConstants;
import com.mrprona.dota2assitant.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.WeakHashMap;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import me.zhanghai.android.materialprogressbar.*;

public class DataUtils {
    public static final String ALLOWED_RESOURCE_MAP = "map";
    public static final int MATCH_PARENTS_SIZE = 3;
    private static final String TAG = "DataUtils";
    private static final String format = "f_webp";
    private static final SimpleDateFormat sdf;
    private static WeakHashMap<String, Drawable> weakDrawables;

    public enum ImageType {
        PlayerEmpty,
        TeamEmpty,
        UserEmpty,
        GameIcon,
        GameBackground,
        GameBackgroundOff
    }

    public enum MatchStatus {
        UNKNOWN,
        COMING,
        LIVE,
        FINISHED,
        MatchStatus;

        public static MatchStatus getMatchStatus(String status) {
            try {
                return valueOf(status.toUpperCase(LocaleUtils.getLocale()));
            } catch (Exception e) {
                return UNKNOWN;
            }
        }
    }

    static {
        weakDrawables = new WeakHashMap();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static boolean isDate(String dateString) {
        try {
            getDateAsCalendar(dateString).getTime();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getFormattedDateForSeasonEnds(Context context, String startDate, String endDate) {
        String format;
        Calendar startCalendar = getDateAsCalendar(startDate);
        Calendar endCalendar = getDateAsCalendar(endDate);
        if (VERSION.SDK_INT >= 18) {
            format = DateFormat.getBestDateTimePattern(LocaleUtils.getLocale(), "MMMd");
        } else {
            format = "MMM dd";
        }
        return ((String) DateFormat.format(format, startCalendar)) + " - " + ((String) DateFormat.format(format, endCalendar));
    }



    public static String getMonth(String date) {
        String stringMonth = new SimpleDateFormat("LLLL", LocaleUtils.getLocale()).format(getDateAsCalendar(date).getTime());
        return stringMonth;
    }

    public static String getSimplifiedDate(Calendar cale, int offset) {
        cale.add(5, offset);
        return new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale()).format(cale.getTime());
    }

    public static String getSimplifiedDate(int offset) {
        Calendar cale = LocaleUtils.getCalendar();
        cale.add(5, offset);
        return new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale()).format(cale.getTime());
    }

    public static boolean isToday(String date) {
        if (date != null) {
            return isToday(getDateAsCalendar(date));
        }
        return false;
    }

    public static boolean isToday(Calendar date) {
        return getSimplifiedDate(0).equals(getSimplifiedDate(date, 0));
    }

    public static boolean isTomorrow(Calendar date) {
        return getSimplifiedDate(1).equals(getSimplifiedDate(date, 0));
    }

    public static boolean isDayAfterTomorrow(Calendar date) {
        return getSimplifiedDate(2).equals(getSimplifiedDate(date, 0));
    }

    public static boolean isYesterday(Calendar date) {
        return getSimplifiedDate(-1).equals(getSimplifiedDate(date, 0));
    }



    public static boolean isWithinFifteenMinutes(String startDate) {
        return LocaleUtils.getCalendar().getTimeInMillis() < 900000 + getDateAsCalendar(startDate).getTimeInMillis();
    }

    public static boolean isWeekend(String date) {
        boolean isWeekday;
        int day = getDateAsCalendar(date).get(7);
        if (day < 2 || day > 6) {
            isWeekday = false;
        } else {
            isWeekday = true;
        }
        if (isWeekday) {
            return false;
        }
        return true;
    }



    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    public static String getFormattedDate(String date) {
        return getFormattedDate(date, false);
    }

    public static String getFormattedDate(String date, boolean leadingZero) {
        Calendar c = getDateAsCalendar(date);
        String out = String.format("%te %tb", new Object[]{c, c}).toUpperCase();
        if (leadingZero) {
            try {
                if (out.length() > 1) {
                    String first = out.substring(0, 1);
                    String second = out.substring(1, 2);
                    if (isNumeric(first) && !isNumeric(second) && Integer.parseInt(first) < 10) {
                        out = AppEventsConstants.EVENT_PARAM_VALUE_NO + out;
                    }
                }
            } catch (Exception e) {
            }
        }
        return out;
    }

    public static Calendar getDateAsCalendar(String date) {
        return getDateAsCalendar(date, "yyyy-MM-dd");
    }

    public static Calendar getDateAsCalendar(String date, String format) {
        Locale.setDefault(LocaleUtils.getLocale());
        Calendar c = LocaleUtils.getCalendar();
        if (date != null) {
            try {
                if (sdf != null && date.trim().length() > 0) {
                    c.setTime(sdf.parse(date));
                }
            } catch (Exception e) {
                try {
                    c.setTime(new SimpleDateFormat(format, LocaleUtils.getLocale()).parse(date));
                } catch (Exception e2) {
                }
            }
        }
        return c;
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String parseValueToString(int value) {
        return value + BuildConfig.FLAVOR;
    }


    public static String fixCloudinaryLink(String url, int width, int height) {
        return fixCloudinaryLink(url, width, height, true);
    }

    public static String fixCloudinaryLink(String url, int width, int height, boolean fill) {
        return fixCloudinaryLink(url, width, height, fill, false);
    }

    public static String fixCloudinaryLink(String url, int width, int height, boolean fill, boolean skipStagingReplace) {
        if (width <= 0) {
            width = 100;
        }
        String suffix = "w_" + width + ",h_" + height + "," + format + ",q_auto" + (fill ? ",g_face,c_fill/" : "/");
        String out = url != null ? url.replace("res.cloudinary.com/strafe/image/upload/", "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix) : BuildConfig.FLAVOR;
        if (!skipStagingReplace) {
            out = out.replace("staging", "production");
        }
        return out;
    }

    public static String fixCloudinaryLinkForHero(String url) {
        String suffix = "w_90,h_90,f_webp,q_auto,g_face,c_fill/";
        String replace = "res.cloudinary.com/strafe/image/upload/";
        String replaceWith = "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix;
        String out = url;
        if (url != null) {
            out = url.replace(replace, replaceWith).replace("staging", "production");
        }
        return out;
    }


    public static String fixCloudinaryLinkForPlayer(String url, int width, int height) {
        if (width <= 0) {
            width = 100;
        }
        String suffix = "w_" + width + ",h_" + height + ",g_faces,c_thumb,r_max," + format + ",q_auto/";
        String out = (url != null ? url.replace("res.cloudinary.com/strafe/image/upload/", "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix) : BuildConfig.FLAVOR).replace("staging", "production");
        return out;
    }

    public static String fixCloudinaryLinkForProfile(String url, int width, int height) {
        if (width <= 0) {
            width = 100;
        }
        String suffix = "w_" + width + ",h_" + height + ",c_fill,r_max," + format + ",q_auto/";
        String out = url != null ? url.replace("res.cloudinary.com/strafe/image/upload/", "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix) : BuildConfig.FLAVOR;
        return out;
    }

    public static String fixCloudinaryLink(String url, int width, boolean square) {
        if (width <= 0) {
            width = 100;
        }
        String suffix = square ? "w_" + width + ",h_" + width + ",g_face,c_fill/" : "h_" + width + ",c_limit," + format + ",q_auto/";
        String out = url.replace("res.cloudinary.com/strafe/image/upload/", "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix).replace("staging", "production");
        return out;
    }

    public static String fixCloudinaryLinkForWidget(String url, int type) {
        return fixCloudinaryLinkForWidget(url, type == 2);
    }

    public static String fixCloudinaryLinkForWidget(String url, boolean isPlayerVsPlayer) {
        String suffix = "w_156,h_156" + (isPlayerVsPlayer ? ",g_face,f_webp,q_auto,c_fill,r_max/" : ",c_fit/");
        String out = url.replace("res.cloudinary.com/strafe/image/upload/", "res.cloudinary.com/strafe/image/upload/".replace(suffix, BuildConfig.FLAVOR) + suffix).replace("staging", "production");
        return out;
    }

    public static String fixCloudinaryBWLink(String url, int width) {
        return fixCloudinaryBWLink(url, width, false);
    }

    public static String fixCloudinaryBWLink(String url, int width, boolean skipOpacity) {
        String out = url;
        if (width > 640) {
            width = 640;
        }
        if (width <= 0) {
            width = 100;
        }
        String suffix = "w_" + width + ",h_" + ((int) (((float) width) * 1.25f)) + ",c_fill" + (skipOpacity ? BuildConfig.FLAVOR : ",o_30") + ",f_webp,q_auto,e_grayscale/";
        String replace1 = "res.cloudinary.com/strafe/image/upload/";
        String replace2 = "res.cloudinary.com/strafe/image/upload/";
        String replaceWith1 = replace1.replace(suffix, BuildConfig.FLAVOR) + suffix;
        String replaceWith2 = replace2.replace(suffix, BuildConfig.FLAVOR) + suffix;
        out = url.replace(replace1, replaceWith1);
        out = url.replace(replace2, replaceWith2);
        if (url.startsWith("csgo")) {
            out = "http://res.cloudinary.com/strafe/image/upload/" + suffix + url;
        }
        return out;
    }

    public static String getFormattedTime(Context context, String date) {
        Calendar c = getDateAsCalendar(date);
        if (DateFormat.is24HourFormat(context)) {
            return new SimpleDateFormat("HH:mm").format(c.getTime());
        }
        return new SimpleDateFormat("h:mm a").format(c.getTime());
    }

    public static String fixAMPMLeadingZero(String time) {
        if (time == null || time.length() <= 2 || !isNumeric(time.substring(0, 2)) || Double.parseDouble(time.substring(0, 2)) >= 10.0d) {
            return time;
        }
        return time.substring(1, time.length());
    }


}
