package com.badr.infodota.trackdota.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.trackdota.adapter.TrackdotaLeagueGamesAdapter;
import com.badr.infodota.trackdota.api.game.League;
import com.badr.infodota.trackdota.api.league.LeagueGame;
import com.badr.infodota.trackdota.api.league.LeagueGamesHolder;
import com.badr.infodota.trackdota.task.TrackdotaLeagueGamesLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by ABadretdinov
 * 08.06.2015
 * 15:45
 */
public class TrackdotaLeagueInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, RequestListener<LeagueGamesHolder>, AdapterView.OnItemClickListener {
    protected SwipeRefreshLayout mListContainer;
    protected ListView mListView;
    protected View mProgressBar;
    protected View mEmptyView;
    protected TrackdotaLeagueGamesAdapter mAdapter;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private long leagueId;

    private void ensureList() {
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setVerticalScrollBarEnabled(true);
        mListContainer = (SwipeRefreshLayout) findViewById(R.id.listContainer);
        mListContainer.setColorSchemeResources(R.color.primary);
        mListContainer.setOnRefreshListener(this);
        mEmptyView = findViewById(R.id.internalEmpty);
        mProgressBar = findViewById(R.id.progressBar);
        if (mAdapter != null) {
            setAdapter(mAdapter);
        }
    }

    public TrackdotaLeagueGamesAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(TrackdotaLeagueGamesAdapter adapter) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        mAdapter = adapter;
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);
        }
        if (mEmptyView != null) {
            if (mAdapter.getCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackdota_league_info);
        ensureList();
        Bundle intent = getIntent().getExtras();
        if (intent != null && intent.containsKey("id")) {
            leagueId = intent.getLong("id");
            onRefresh();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mListContainer.setRefreshing(true);
        mSpiceManager.execute(new TrackdotaLeagueGamesLoadRequest(leagueId), this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        mListContainer.setRefreshing(false);
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(LeagueGamesHolder leagueGamesHolder) {
        mListContainer.setRefreshing(false);
        if (leagueGamesHolder != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(leagueGamesHolder.getLeague().getName());
            }
            setAdapter(new TrackdotaLeagueGamesAdapter(leagueGamesHolder.getLeague(), leagueGamesHolder.getGames()));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object entity = mAdapter.getItem(position);
        Intent intent;
        if (position == 0) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(((League) entity).getUrl()));
        } else {
            intent = new Intent(this, TrackdotaGameInfoActivity.class);
            intent.putExtra("id", ((LeagueGame) entity).getId());
        }
        startActivity(intent);
    }
}
