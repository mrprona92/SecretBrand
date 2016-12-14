package com.badr.infodota.trackdota.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;
import android.util.SparseArray;

import com.badr.infodota.R;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.base.util.Updatable;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.live.LiveGame;
import com.badr.infodota.trackdota.fragment.game.CommonInfo;
import com.badr.infodota.trackdota.fragment.game.Graphs;
import com.badr.infodota.trackdota.fragment.game.LogList;
import com.badr.infodota.trackdota.fragment.game.MapAndHeroes;
import com.badr.infodota.trackdota.fragment.game.Statistics;
import com.badr.infodota.trackdota.fragment.game.StreamList;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 16:49
 */
public class TrackdotaGamePagerAdapter extends FragmentPagerAdapter {
    private CoreResult coreResult;
    private LiveGame liveGame;
    private String[] titles;
    private Refresher refresher;
    private SparseArray<Updatable<Pair<CoreResult, LiveGame>>> fragmentsMap = new SparseArray<>();

    public TrackdotaGamePagerAdapter(Context context, FragmentManager fragmentManager, Refresher refresher, CoreResult coreResult, LiveGame liveGame) {
        super(fragmentManager);
        titles = context.getResources().getStringArray(R.array.trackdota_game);
        this.coreResult = coreResult;
        this.liveGame = liveGame;
        this.refresher = refresher;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentsMap.indexOfKey(position) >= 0) {
            return (Fragment) fragmentsMap.get(position);
        }
        switch (position) {
            default:
            case 0:
                CommonInfo commonInfo = CommonInfo.newInstance(refresher, coreResult, liveGame);
                fragmentsMap.put(position, commonInfo);
                return commonInfo;
            case 1:
                MapAndHeroes mapAndHeroes = MapAndHeroes.newInstance(refresher, coreResult, liveGame);
                fragmentsMap.put(position, mapAndHeroes);
                return mapAndHeroes;
            case 2:
                Graphs graphs = Graphs.newInstance(refresher, coreResult, liveGame);
                fragmentsMap.put(position, graphs);
                return graphs;
            case 3:
                Statistics statistics = Statistics.newInstance(refresher, coreResult, liveGame);
                fragmentsMap.put(position, statistics);
                return statistics;
            case 4:
                LogList logList = LogList.newInstance(refresher, coreResult, liveGame);
                fragmentsMap.put(position, logList);
                return logList;
            case 5:
                StreamList streamList = StreamList.newInstance(refresher, coreResult);
                fragmentsMap.put(position, streamList);
                return streamList;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public void update(CoreResult coreResult, LiveGame liveGame) {
        this.coreResult = coreResult;
        this.liveGame = liveGame;
        int size = fragmentsMap.size();
        for (int i = 0; i < size; i++) {
            Updatable<Pair<CoreResult, LiveGame>> updatable = fragmentsMap.get(fragmentsMap.keyAt(i));
            updatable.onUpdate(Pair.create(coreResult, liveGame));
        }
    }
}
