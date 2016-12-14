package com.badr.infodota.player.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.match.activity.MatchDetailsActivity;
import com.badr.infodota.player.adapter.PlayerCommonStatsAdapter;
import com.badr.infodota.player.adapter.holder.PlayerCommonStatHolder;
import com.badr.infodota.player.api.PlayerCommonStat;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.task.PlayerCommonStatsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.MessageFormat;

/**
 * User: ABadretdinov
 * Date: 27.03.14
 * Time: 18:27
 */
public class PlayerCommonStats extends UpdatableRecyclerFragment<PlayerCommonStat, PlayerCommonStatHolder> implements RequestListener<PlayerCommonStats.CommonInfo> {
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private String metric;
    private Unit account;

    public static PlayerCommonStats newInstance(Unit account, Bundle args, String metric) {
        PlayerCommonStats fragment = new PlayerCommonStats();
        fragment.setArguments(args);
        fragment.setMetric(metric);
        fragment.setAccount(account);
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            onRefresh();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    public void setAccount(Unit account) {
        this.account = account;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setColumnSize();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setColumnSize();
    }

    private void setColumnSize() {
        if (getRecyclerView() != null) {
            if (getResources().getBoolean(R.bool.is_tablet)) {
                ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(2);
            } else {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(2);
                } else {
                    ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(1);
                }
            }
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(CommonInfo commonInfo) {
        setRefreshing(false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && commonInfo != null) {
            activity.getSupportActionBar().setSubtitle(MessageFormat
                    .format(getString(R.string.record_with_win_rate), commonInfo.wins, commonInfo.loses, commonInfo.winRate));
            setAdapter(new PlayerCommonStatsAdapter(activity, commonInfo.stats));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        PlayerCommonStat entity = getAdapter().getItem(position);
        Intent intent = new Intent(view.getContext(), MatchDetailsActivity.class);
        intent.putExtra("matchId", entity.getMatchId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Activity activity = getActivity();
        if (activity != null) {
            setRefreshing(true);
            mSpiceManager.execute(new PlayerCommonStatsLoadRequest(activity.getApplicationContext(), account.getAccountId(), metric, getArguments()), this);
        }
    }

    public static class CommonInfo {
        public PlayerCommonStat.List stats;
        public String wins;
        public String loses;
        public String winRate;
    }
}
