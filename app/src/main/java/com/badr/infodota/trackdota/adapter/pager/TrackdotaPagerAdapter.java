package com.badr.infodota.trackdota.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.base.util.Updatable;
import com.badr.infodota.trackdota.api.game.GamesResult;
import com.badr.infodota.trackdota.fragment.FeaturedGamesList;
import com.badr.infodota.trackdota.fragment.LeaguesList;
import com.badr.infodota.trackdota.fragment.LiveGamesList;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:29
 */
public class TrackdotaPagerAdapter extends FragmentPagerAdapter {
    public static final int LIVE_GAMES = 0;
    public static final int FINISHED_GAMES = 1;
    public static final int RECENT_GAMES = 2;
    public static final int LEAGUES = 3;
    private String[] titles;
    private SparseArray<Object> fragmentsMap = new SparseArray<>();
    private Refresher refresher;

    public TrackdotaPagerAdapter(Context context, FragmentManager fm, Refresher refresher) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.trackdota);
        this.refresher = refresher;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentsMap.indexOfKey(position) >= 0) {
            return (Fragment) fragmentsMap.get(position);
        }
        switch (position) {
            case LIVE_GAMES:
                LiveGamesList liveGames = LiveGamesList.newInstance(refresher);
                fragmentsMap.put(LIVE_GAMES, liveGames);
                return liveGames;
            case LEAGUES:
                LeaguesList leagues = new LeaguesList();
                fragmentsMap.put(LEAGUES, leagues);
                return leagues;
            case FINISHED_GAMES:
            case RECENT_GAMES:
            default:
                FeaturedGamesList featuredGames = FeaturedGamesList.newInstance(refresher);
                fragmentsMap.put(position, featuredGames);
                return featuredGames;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragmentsMap.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @SuppressWarnings("unchecked")
    public void update(GamesResult gamesResult) {
        for (int index = 0, size = fragmentsMap.size(); index < size; index++) {
            int key = fragmentsMap.keyAt(index);
            Object entity;
            if (gamesResult == null) {
                entity = null;
            } else {
                switch (key) {
                    case LIVE_GAMES:
                        entity = gamesResult.getEnhancedMatches();
                        break;
                    case FINISHED_GAMES:
                        entity = gamesResult.getFinishedGames();
                        break;
                    default:
                    case RECENT_GAMES:
                        entity = gamesResult.getRecentGames();
                        break;
                }
            }
            Object fragment = fragmentsMap.get(key);
            if (fragment instanceof Updatable) {
                ((Updatable) fragment).onUpdate(entity);
            }
        }
    }
}
