package com.badr.infodota.base.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.badr.infodota.base.R;

import java.text.MessageFormat;

/**
 * User: Histler
 * Date: 22.04.14
 */
public class AppRater {

    private final static int DAYS_UNTIL_PROMPT = 2;
    private final static int LAUNCHES_UNTIL_PROMPT = 3;

    private final static String LAUNCH_COUNT = "launch_count";
    private final static String DONT_SHOW_AGAIN = "dontshowagain";
    private final static String FIRST_LAUNCH_DATE = "date_firstlaunch";
    private final static String PREFS_NAME = "apprater";

    public static void onAppLaunched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, 0);
        if (prefs.getBoolean(DONT_SHOW_AGAIN, false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong(LAUNCH_COUNT, 0) + 1;
        editor.putLong(LAUNCH_COUNT, launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong(FIRST_LAUNCH_DATE, 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong(FIRST_LAUNCH_DATE, date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }
        editor.commit();
    }

    public static void openMarketForRate(final Context context) {
        SharedPreferences.Editor currentEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        currentEditor.putBoolean(DONT_SHOW_AGAIN, true);
        currentEditor.commit();
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Utils.getApplicationPackage(context))));
    }

    public static void showRateDialog(final Context context, final SharedPreferences.Editor editor) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        String appName = Utils.getApplicationName(context);
        dialog.setTitle(MessageFormat.format(context.getString(R.string.base_rate_app_name), appName));

        dialog.setPositiveButton(R.string.base_rate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openMarketForRate(context);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton(R.string.base_no_thanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editor != null) {
                    editor.putBoolean(DONT_SHOW_AGAIN, true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        dialog.setMessage(MessageFormat.format(context.getString(R.string.base_rate_message), appName));
        dialog.show();
    }
}
