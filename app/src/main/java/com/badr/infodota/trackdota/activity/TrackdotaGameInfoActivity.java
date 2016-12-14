package com.badr.infodota.trackdota.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.adapter.pager.TrackdotaGamePagerAdapter;
import com.badr.infodota.trackdota.api.GameManager;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.game.Team;
import com.badr.infodota.trackdota.api.live.LiveGame;
import com.badr.infodota.trackdota.api.live.LiveTeam;
import com.badr.infodota.trackdota.task.CoreGameLoadRequest;
import com.badr.infodota.trackdota.task.LiveGameLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.MessageFormat;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 14:11
 */
public class TrackdotaGameInfoActivity extends BaseActivity implements Refresher, RequestListener {

    private static final long DELAY_20_SEC = 20000;
    private CoreResult coreResult;
    private LiveGame liveGame;
    private long matchId;
    private TrackdotaGamePagerAdapter adapter;
    private View progressBar;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private Handler updateHandler = new Handler();
    private Runnable updateTask;
    /*need to initialize*/
    private GameManager mGameManager = GameManager.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            if (liveGame == null || liveGame.getStatus() < 4) {
                onRefresh();
            }
        }
    }

    @Override
    protected void onStop() {
        cancelDelayedUpdate();
        super.onStop();
    }


    private void cancelDelayedUpdate() {
        if (updateTask != null) {
            updateHandler.removeCallbacks(updateTask);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackdota_game_info);
        progressBar = findViewById(R.id.progressBar);
        Bundle intent = getIntent().getExtras();
        if (intent != null && intent.containsKey("id")) {
            matchId = intent.getLong("id");
            adapter = new TrackdotaGamePagerAdapter(this, getSupportFragmentManager(), this, coreResult, liveGame);

            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(2);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        mGameManager = null;
        GameManager.clear();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        cancelDelayedUpdate();
        progressBar.setVisibility(View.VISIBLE);
        mSpiceManager.execute(new CoreGameLoadRequest(getApplicationContext(), matchId), this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Object object) {
        if (object instanceof CoreResult) {
            coreResult = (CoreResult) object;
            mSpiceManager.execute(new LiveGameLoadRequest(getApplicationContext(), matchId), this);
        } else if (object instanceof LiveGame) {
            liveGame = (LiveGame) object;
            LiveTeam radiantLive = liveGame.getRadiant();
            Team radiant = coreResult.getRadiant();
            LiveTeam direLive = liveGame.getDire();
            Team dire = coreResult.getDire();
            if (radiant != null && dire != null && radiantLive != null && direLive != null) {
                getSupportActionBar().setTitle(
                        MessageFormat.format(
                                "{0}:{1} - {2}:{3}",
                                TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT),
                                radiantLive.getScore(),
                                TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE),
                                direLive.getScore())
                );
            }
            progressBar.setVisibility(View.GONE);
            adapter.update(coreResult, liveGame);
            if (liveGame.getStatus() < 4) {
                startDelayedUpdate();
            }
            if (liveGame.getApiDowntime() > 0) {
                Toast.makeText(progressBar.getContext(), R.string.api_is_down, Toast.LENGTH_LONG).show();
            }
        } else if (object == null) {
            progressBar.setVisibility(View.GONE);
            adapter.update(coreResult, liveGame);
            if (coreResult == null && liveGame == null) {
                Toast.makeText(progressBar.getContext(), R.string.could_not_load_match, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void startDelayedUpdate() {
        cancelDelayedUpdate();
        updateTask = new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        };
        updateHandler.postDelayed(updateTask, DELAY_20_SEC);
    }

}
