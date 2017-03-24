package com.mrprona.dota2assitant.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.api.Constants;
import com.mrprona.dota2assitant.base.dao.DatabaseManager;
import com.mrprona.dota2assitant.base.dao.Helper;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.base.service.LocalUpdateService;
import com.mrprona.dota2assitant.base.task.UpdateLoadRequest;
import com.mrprona.dota2assitant.base.util.UiUtils;
import com.mrprona.dota2assitant.hero.api.TalentTree;
import com.mrprona.dota2assitant.hero.dao.HeroDao;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.parser.JsonSimpleExample;

import java.util.List;
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

    /*private void checkGooglePlayServicesAndRun() {
        *//*int code= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(ConnectionResult.SUCCESS==code){*//*
        *//*new LoaderProgressTask<>(new ProgressTask<String>() {
            @Override
            public String doTask(OnPublishProgressListener listener) throws Exception {
                try {
                    listener.progressUpdated("STARTING");
                    String path=FileUtils.externalFileDir(LoaderActivity.this).getAbsolutePath();
                    String setsVDF=FileUtils.getTextFromAsset(LoaderActivity.this, "item_sets.txt");
                    String jsonSets= VDFtoJsonParser.parse(setsVDF);
                    FileUtils.saveFile(path + File.separator + "sets.json", new ByteArrayInputStream(jsonSets.getBytes("UTF-8")));
                    listener.progressUpdated("SETS DONE");
                    String itemsVDF=FileUtils.getTextFromAsset(LoaderActivity.this,"items.txt");
                    String jsonItems= VDFtoJsonParser.parse(itemsVDF);
                    FileUtils.saveFile(path + File.separator + "items.json", new ByteArrayInputStream(jsonItems.getBytes("UTF-8")));
                    listener.progressUpdated("ITEMS DONE");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return "";
            }
            @Override
            public void doAfterTask(String result) {*//*
        runApp();
            *//*}
            @Override
            public void handleError(String error) {
            }
            @Override
            public String getName() {
                return null;
            }
        }, new LoaderProgressTask.OnProgressUpdateListener() {
            @Override
            public void onStart() {
            }
            @Override
            public void onProgressUpdate(String... progress) {
                Toast.makeText(LoaderActivity.this,progress[0],Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
            }
        }).execute();*//*
        *//*}
        else {
            //showDialog=false;
            GooglePlayServicesUtil.getErrorDialog(code, this,PLAY_SERVICES_REQUEST,new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                    finish();
                }
            }).show();
			info.setText(getString(R.string.loading_heroes_completed_and_stoped));
        }*//*
    }*/

}
