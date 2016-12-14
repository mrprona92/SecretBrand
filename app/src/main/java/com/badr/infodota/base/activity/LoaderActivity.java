package com.badr.infodota.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.api.Constants;
import com.badr.infodota.base.dao.Helper;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.base.service.LocalUpdateService;
import com.badr.infodota.base.task.UpdateLoadRequest;
import com.badr.infodota.base.util.UiUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Locale;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 11:58
 */
public class LoaderActivity extends Activity implements RequestListener<String> {
    LocalUpdateService localUpdateService = BeanContainer.getInstance().getLocalUpdateService();
    private boolean doubleBackToExitPressedOnce = false;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    private View mLogo;
    private View mButtonsHolder;

    @Override
    protected void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getApplicationContext());
            final int currentVersion = localUpdateService.getVersion(this);
            if (currentVersion != Helper.DATABASE_VERSION) {
                mSpiceManager.execute(new UpdateLoadRequest(getApplicationContext()), this);
            } else {
                postAnimate();
            }
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loader);
        mLogo = findViewById(R.id.logo);
        mButtonsHolder = findViewById(R.id.buttons_holder);
        SharedPreferences localPrefs = getSharedPreferences("locale", MODE_PRIVATE);
        String loc = localPrefs.getString("current", null);
        if (loc != null) {
            Locale locale = new Locale(loc);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }
    }

    private void animate() {
        UiUtils.moveView(mLogo, 0, -mButtonsHolder.getMeasuredHeight() / 2, DurationInMillis.ONE_SECOND);
        UiUtils.moveView(mButtonsHolder, 0, mButtonsHolder.getMeasuredHeight() / 2, DurationInMillis.ONE_SECOND);
        UiUtils.setViewVisible(mButtonsHolder, DurationInMillis.ONE_SECOND);
    }

    private void postAnimate() {
        mButtonsHolder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                animate();
                mButtonsHolder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_toast), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, Constants.MILLIS_FOR_EXIT);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoaderActivity.this);
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
        localUpdateService.setUpdated(LoaderActivity.this);
        animate();
    }

    public void onNotNow(View view) {
        startActivity(new Intent(this, ListHolderActivity.class));
        finish();
    }

    public void findOnDotabuff(View view) {

    }

    public void loginWithSteam(View view) {
        startActivityForResult(new Intent(this, SteamLoginActivity.class), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }
}
