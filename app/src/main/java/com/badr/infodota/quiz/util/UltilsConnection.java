package com.badr.infodota.quiz.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.badr.infodota.base.activity.ListHolderActivity;

/**
 * Created by BinhTran on 12/29/16.
 */

public class UltilsConnection {


    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) ListHolderActivity.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
