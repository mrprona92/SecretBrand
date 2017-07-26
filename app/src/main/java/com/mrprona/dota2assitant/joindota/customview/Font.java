package com.mrprona.dota2assitant.joindota.customview;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.util.Log;


import com.mrprona.dota2assitant.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import me.zhanghai.android.materialprogressbar.internal.DrawableCompat;

public class Font {
    public static final String DIN_BLACK = "din_black.otf";
    public static final String DIN_BOLD = "din_bold.otf";
    public static final String DIN_LIGHT = "din_light.otf";
    public static final String DIN_MEDIUM = "din_medium.otf";
    public static final String DIN_REGULAR = "din_regular.otf";
    public static final String GRIDNIK_BOLD = "gridnik_bold.otf";
    public static final String GRIDNIK_MEDIUM = "gridnik_medium.otf";
    public static final String GRIDNIK_REGULAR = "gridnik_regular.otf";
    public static final String ROBOTO_BOLD = "roboto_bold.ttf";
    public static final String ROBOTO_MEDIUM = "roboto_medium.ttf";
    public static final String ROBOTO_REGULAR = "roboto_regular.ttf";
    private static final String TAG = "Font";
    private static Hashtable<String, Typeface> fontCache;

    static {
        fontCache = new Hashtable();
    }

    public static Typeface get(String name, Context context) {
        Typeface tf = (Typeface) fontCache.get(name);
        Log.d(TAG, "Getting font: " + name);
        if (tf == null) {
            try {
                tf = getFontFromRes(context, getResourceFromName(name));
                if (tf != null) {
                    fontCache.put(name, tf);
                }
            } catch (Exception e) {
                return null;
            }
        }
        Log.d(TAG, "Font returned: " + (tf != null));
        return tf;
    }

    private static Typeface getFontFromRes(Context context, int resource) {
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(resource);
        } catch (NotFoundException e) {
            Log.d(TAG, "Could not find font in resources!");
        }
        String outPath = context.getCacheDir() + "/tmp" + System.currentTimeMillis() + ".raw";
        try {
            byte[] buffer = new byte[is.available()];
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath));
            while (true) {
                int l = is.read(buffer);
                if (l > 0) {
                    bos.write(buffer, 0, l);
                } else {
                    bos.close();
                    Typeface tf = Typeface.createFromFile(outPath);
                    new File(outPath).delete();
                    Log.d(TAG, "Successfully loaded font.");
                    return tf;
                }
            }
        } catch (IOException e2) {
            Log.d(TAG, "Error reading in font!");
            return null;
        }
    }

    private static int getResourceFromName(String name) {
        Object obj = -1;
        switch (name.hashCode()) {
            case -2112897234:
                if (name.equals(DIN_BOLD)) {
                    obj = 4;
                    break;
                }
                break;
            case -2035305523:
                if (name.equals(GRIDNIK_BOLD)) {
                    obj = 2;
                    break;
                }
                break;
            case -1871741059:
                if (name.equals(GRIDNIK_MEDIUM)) {
                    obj = 1;
                    break;
                }
                break;
            case -500308199:
                if (name.equals(DIN_REGULAR)) {
                    obj = 7;
                    break;
                }
                break;
            case 341948954:
                if (name.equals(GRIDNIK_REGULAR)) {
                    obj = null;
                    break;
                }
                break;
            case 492730899:
                if (name.equals(DIN_LIGHT)) {
                    obj = 5;
                    break;
                }
                break;
            case 872035998:
                if (name.equals(DIN_MEDIUM)) {
                    obj = 6;
                    break;
                }
                break;
            case 1041964698:
                if (name.equals(ROBOTO_REGULAR)) {
                    obj = 10;
                    break;
                }
                break;
            case 1139357820:
                if (name.equals(DIN_BLACK)) {
                    obj = 3;
                    break;
                }
                break;
            case 1337433383:
                if (name.equals(ROBOTO_MEDIUM)) {
                    obj = 8;
                    break;
                }
                break;
            case 1909933751:
                if (name.equals(ROBOTO_BOLD)) {
                    obj = 9;
                    break;
                }
                break;
        }

        int value= Integer.parseInt(obj.toString());
        
        if (value == R.styleable.View_android_theme) {
            return R.raw.gridnik_regular;
        } else if (value == R.styleable.View_android_focusable) {
            return R.raw.gridnik_medium;
        } else if (value == R.styleable.View_paddingStart) {
            return R.raw.gridnik_bold;
        } else if (value == R.styleable.View_paddingEnd) {
            return R.raw.din_black;
        } else if (value == R.styleable.View_theme) {
            return R.raw.din_bold;
        } else if (value == R.styleable.Toolbar_contentInsetStart) {
            return R.raw.din_light;
        } else if (value == R.styleable.Toolbar_contentInsetEnd) {
            return R.raw.din_medium;
        } else if (value == R.styleable.Toolbar_contentInsetLeft) {
            return R.raw.din_regular;
        } else if (value == R.styleable.Toolbar_contentInsetRight) {
            return R.raw.roboto_medium;
        } else if (value == R.styleable.Toolbar_contentInsetStartWithNavigation) {
            return R.raw.roboto_bold;
        } else if (value == R.styleable.Toolbar_contentInsetEndWithActions) {
            return R.raw.roboto_regular;
        } else {
            return R.raw.din_regular;
        }
    }
}

