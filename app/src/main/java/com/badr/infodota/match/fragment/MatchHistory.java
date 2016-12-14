package com.badr.infodota.match.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.base.view.EndlessRecycleScrollListener;
import com.badr.infodota.hero.adapter.HeroesAutoCompleteAdapter;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.match.activity.MatchDetailsActivity;
import com.badr.infodota.match.adapter.HistoryMatchesAdapter;
import com.badr.infodota.match.adapter.holder.HistoryMatchHolder;
import com.badr.infodota.match.api.history.PlayerMatch;
import com.badr.infodota.match.api.history.PlayerMatchResult;
import com.badr.infodota.match.task.PlayerMatchLoadRequest;
import com.badr.infodota.player.api.Unit;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 18.02.14
 * Time: 16:46
 */
public class MatchHistory extends UpdatableRecyclerFragment<PlayerMatch, HistoryMatchHolder> implements RequestListener<PlayerMatchResult> {
    HeroService heroService = BeanContainer.getInstance().getHeroService();
    private Unit mAccount;
    private long mTotal = 1;
    private Long mHeroId = null;
    private AutoCompleteTextView heroView;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    public static MatchHistory newInstance(Unit account) {
        MatchHistory fragment = new MatchHistory();
        fragment.mAccount = account;
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            loadHistory(0, true);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.match_history);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        Activity activity = getActivity();
        if (root != null && activity != null) {
            setColumnSize();
            heroView = (AutoCompleteTextView) root.findViewById(R.id.hero_search);
            List<Hero> heroes = heroService.getAllHeroes(activity);
            heroView.setAdapter(new HeroesAutoCompleteAdapter(activity, heroes));
            heroView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Hero hero = ((HeroesAutoCompleteAdapter) heroView.getAdapter()).getItem(position);
                    mHeroId = hero.getId();
                    heroView.setText(hero.getLocalizedName());
                    loadHistory(0, true);
                }
            });
            getView().findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHeroId = null;
                    heroView.setText("");
                    loadHistory(0, true);
                }
            });
            getRecyclerView().addOnScrollListener(new EndlessRecycleScrollListener() {
                @Override
                public void onLoadMore() {
                    long lastMatchId = 0;
                    HistoryMatchesAdapter adapter = (HistoryMatchesAdapter) getAdapter();
                    int count = adapter.getItemCount();
                    if (count > 0) {
                        PlayerMatch last = adapter.getItem(adapter.getItemCount() - 1);
                        lastMatchId = last.getMatchId() - 1;
                    }
                    loadHistory(lastMatchId, false);
                }
            });
        }
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

    private void loadHistory(long fromMatchId, boolean reCreateAdapter) {
        Activity activity = getActivity();
        if (activity != null) {
            if (reCreateAdapter || mTotal > getAdapter().getItemCount()) {
                setRefreshing(true);
                mSpiceManager.execute(new PlayerMatchLoadRequest(activity.getApplicationContext(), reCreateAdapter, mAccount.getAccountId(), fromMatchId, mHeroId), this);
            }
        }
    }

    @Override
    public void onRefresh() {
        loadHistory(0, true);
    }

    @Override
    public void onItemClick(View view, int position) {
        PlayerMatch entity = getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
        intent.putExtra("matchId", String.valueOf(entity.getMatchId()));
        startActivity(intent);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(PlayerMatchResult playerMatchResult) {
        setRefreshing(false);
        if (playerMatchResult != null) {
            mTotal = playerMatchResult.getTotalMatches();
            if (playerMatchResult.isRecreate() || getAdapter() == null) {
                setAdapter(new HistoryMatchesAdapter(playerMatchResult.getPlayerMatches()));
            } else {
                ((HistoryMatchesAdapter) getAdapter()).addMatches(playerMatchResult.getPlayerMatches());
            }
            if (playerMatchResult.getStatus() == 15) {
                Toast.makeText(getActivity(), getString(R.string.match_history_closed),
                        Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(playerMatchResult.getStatusDetails())) {
                Toast.makeText(getActivity(), playerMatchResult.getStatusDetails(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }


}
