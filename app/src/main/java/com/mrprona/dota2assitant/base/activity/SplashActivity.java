package com.mrprona.dota2assitant.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.dao.Helper;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.base.service.LocalUpdateService;
import com.mrprona.dota2assitant.base.task.UpdateLoadRequest;
import com.mrprona.dota2assitant.base.util.UiUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Locale;

public class SplashActivity extends Activity implements RequestListener<String> {

    LocalUpdateService localUpdateService = BeanContainer.getInstance().getLocalUpdateService();
    private boolean doubleBackToExitPressedOnce = false;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences localPrefs = getSharedPreferences("locale", MODE_PRIVATE);
        String loc = localPrefs.getString("current", null);
        if (loc != null) {
            Locale locale = new Locale(loc);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, ListHolderActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }


    @Override
    protected void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getApplicationContext());
            final int currentVersion = localUpdateService.getVersion(this);
            if (currentVersion != Helper.DATABASE_VERSION) {
                mSpiceManager.execute(new UpdateLoadRequest(getApplicationContext()), this);
            }
        }
        super.onStart();
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
        dialog.setTitle(getString(R.string.error_during_load));
        dialog.setMessage(spiceException.getLocalizedMessage());
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestSuccess(String s) {
        localUpdateService.setUpdated(SplashActivity.this);
    }


    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

}
