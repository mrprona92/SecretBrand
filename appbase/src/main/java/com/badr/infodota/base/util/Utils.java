package com.badr.infodota.base.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.badr.infodota.base.R;

/**
 * Created by ABadretdinov
 * 13.10.2015
 * 15:47
 */
public class Utils {
    public static final int PHONE = 0;
    public static final int TABLET_PORTRAIT = 1;
    public static final int TABLET_LANDSCAPE = 2;

    public static int dpSize(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px * scale + 0.5f);
    }

    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

    public static String getApplicationPackage(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static int getDeviceState(Context context) {
        Resources resources = context.getResources();
        int state;
        if (!resources.getBoolean(R.bool.is_tablet)) {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                state = Utils.TABLET_PORTRAIT;
            } else {
                state = Utils.PHONE;
            }
        } else {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                state = Utils.TABLET_LANDSCAPE;
            } else {
                state = Utils.TABLET_PORTRAIT;
            }
        }
        return state;
    }

    public static boolean IsPackageInstalled(Context context, String PackageUri) {
        final PackageManager pm = context.getPackageManager();
        boolean IsPackageInstalled;
        try {
            pm.getPackageInfo(PackageUri, PackageManager.GET_ACTIVITIES);
            IsPackageInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            IsPackageInstalled = false;
        }
        return IsPackageInstalled;
    }
}
