package com.mrprona.dota2assitant.match.fragment;

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

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.fragment.UpdatableRecyclerFragment;
import com.mrprona.dota2assitant.base.view.EndlessRecycleScrollListener;
import com.mrprona.dota2assitant.hero.adapter.HeroesAutoCompleteAdapter;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.service.HeroService;
import com.mrprona.dota2assitant.match.activity.MatchDetailsActivity;
import com.mrprona.dota2assitant.match.adapter.HistoryMatchesAdapter;
import com.mrprona.dota2assitant.match.adapter.holder.HistoryMatchHolder;
import com.mrprona.dota2assitant.match.api.history.PlayerMatch;
import com.mrprona.dota2assitant.match.api.history.PlayerMatchResult;
import com.mrprona.dota2assitant.match.task.PlayerMatchLoadRequest;
import com.mrprona.dota2assitant.player.api.Unit;
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
    public int getToolbarTitle() {
        return 0;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }

    @Override
    protected int getViewContent() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListener() {

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
