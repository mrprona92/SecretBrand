package com.mrprona.dota2assitant.joindota.customview;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.WeakHashMap;

public class Utils {
    public static final int COLOR_DEFAULT = 1;
    public static final int COLOR_FINISHED = 2;
    public static final int COLOR_FINISHED_BG = 3;
    public static final int COLOR_WHITE = 4;
    private static final String TAG = "Utils";
    private static List<Integer> percentageCutOffs;
    private static WeakHashMap<Integer, Integer> weakColors;


    static {
        weakColors = new WeakHashMap();
        percentageCutOffs = Arrays.asList(new Integer[]{Integer.valueOf(COLOR_DEFAULT), Integer.valueOf(COLOR_FINISHED), Integer.valueOf(5), Integer.valueOf(10), Integer.valueOf(20), Integer.valueOf(30), Integer.valueOf(40), Integer.valueOf(50), Integer.valueOf(60), Integer.valueOf(70), Integer.valueOf(80), Integer.valueOf(90)});
    }

    public static boolean exists(String check, List<String> items) {
        for (String t : items) {
            if (!t.equals(check)) {
                if (check.length() == 0) {
                }
            }
            return true;
        }
        return false;
    }

    public static int getDimensionFromResource(Context context, int resId) {
        return context.getResources().getDimensionPixelOffset(resId);
    }

    public static boolean hasNavBar(Context context) {
        return false;
    }


    public static int dpToPixel(float dp, Context context) {
        return dpToPixel(context, dp);
    }

    public static int dpToPixel(Context context, float dp) {
        return (int) ((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * dp);
    }

    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Drawable getClickFeedbackDrawable(Context context, boolean enable, boolean borderless) {
        if (!enable) {
            return null;
        }
        int[] attrs = new int[0];
        int resId = VERSION.SDK_INT >= 21 ? borderless ? 16843868 : 16843534 : 16843534;
        attrs = new int[COLOR_DEFAULT];
        attrs[0] = resId;
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable drawableFromTheme = ta.getDrawable(0);
        ta.recycle();
        return drawableFromTheme;
    }

    public static int getThemeAttributeColor(Context context, int attr) {
        TypedArray a = null;
        try {
            Theme theme = context.getTheme();
            int[] iArr = new int[COLOR_DEFAULT];
            iArr[0] = attr;
            a = theme.obtainStyledAttributes(iArr);
            int color = a.getColor(0, 0);
            return color;
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public static boolean isPreLollipop() {
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Calendar getDateAsCalendar(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar c = LocaleUtils.getCalendar();
        if (date != null) {
            try {
                if (date.trim().length() > 0) {
                    c.setTime(sdf.parse(date));
                }
            } catch (Exception e) {
                try {
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                } catch (Exception e2) {
                }
            }
        }
        return c;
    }


    public static boolean isToday(Calendar date) {
        return getSimplifiedDate(0).equals(getSimplifiedDate(date, 0));
    }

    public static boolean isTomorrow(Calendar date) {
        return getSimplifiedDate(COLOR_DEFAULT).equals(getSimplifiedDate(date, 0));
    }

    public static boolean isYesterday(Calendar date) {
        return getSimplifiedDate(-1).equals(getSimplifiedDate(date, 0));
    }


    public static String getSimplifiedDate(Calendar cale, int offset) {
        cale.add(5, offset);
        return new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
    }

    public static String getSimplifiedDate(int offset) {
        Calendar cale = LocaleUtils.getCalendar();
        cale.add(5, offset);
        return new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
    }

    public static boolean isConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            return false;
        }
        return true;
    }


    public static String getCapitalizedFirstLetter(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == COLOR_DEFAULT) {
            return s.toUpperCase();
        }
        if (s.length() > COLOR_DEFAULT) {
            return s.substring(0, COLOR_DEFAULT).toUpperCase() + s.substring(COLOR_DEFAULT);
        }
        return s;
    }

    public static boolean setVisibility(View view, boolean visible) {
        return setVisibility(view, visible ? 0 : COLOR_WHITE);
    }

    public static boolean setVisibility(View view, boolean visible, boolean goneInsteadOfInvisible) {
        int i = visible ? 0 : goneInsteadOfInvisible ? 8 : COLOR_WHITE;
        return setVisibility(view, i);
    }

    public static boolean setVisibility(View view, int visibility) {
        if (view == null || view.getVisibility() == visibility) {
            return false;
        }
        view.setVisibility(visibility);
        return true;
    }


    public static void setAlpha(View view, float alpha) {
        if (view != null && view.getAlpha() != alpha) {
            view.setAlpha(alpha);
        }
    }

    public static long getSeconds(long millis) {
        return (millis / 1000) % 60;
    }

    public static long getHours(long millis) {
        return (millis / 3600000) % 24;
    }

    public static long getDays(long millis) {
        return millis / 86400000;
    }

}
