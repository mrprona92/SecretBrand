package com.badr.infodota.counter.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.InitializingBean;
import com.badr.infodota.R;
import com.badr.infodota.base.dao.DatabaseManager;
import com.badr.infodota.counter.api.Counter;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.counter.dao.TruepickerHeroDao;
import com.badr.infodota.counter.remote.CounterRemoteEntityService;
import com.badr.infodota.hero.api.HeroStats;
import com.badr.infodota.hero.dao.HeroStatsDao;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 14:54
 */
public class CounterServiceImpl implements CounterService, InitializingBean {
    private CounterRemoteEntityService service;
    private TruepickerHeroDao truepickerHeroDao;
    private HeroStatsDao heroStatsDao;

    @Override
    public TruepickerHero.List getCounters(Context context, List<Integer> allies, List<Integer> enemies,
                                           int roleCodes) {
        try {
            Pair<List<Counter>, String> serviceResult = service.getCounters(context, allies, enemies, roleCodes);
            if (serviceResult.first == null) {
                String message;
                if (serviceResult.second.contains("\"controller\":\"pick\"")) {
                    message = context.getString(R.string.empty_truepicker);
                } else {
                    message = serviceResult.second;
                }
                Log.e(CounterServiceImpl.class.getName(), message);
                return null;
            } else {
                TruepickerHero.List heroes = new TruepickerHero.List();
                for (Counter counter : serviceResult.first) {
                    TruepickerHero hero = getTruepickerHeroByTpId(context, Integer.valueOf(counter.getHero()));
                    if (hero != null) {
                        heroes.add(hero);
                    }
                }
                return heroes;
            }
        } catch (Exception e) {
            String message = "Failed to get counters, cause: " + e.getMessage();
            Log.e(CounterServiceImpl.class.getName(), message, e);
            return null;
        }
    }

    @Override
    public TruepickerHero.List getTruepickerHeroes(Context context, String filter) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            TruepickerHero.List heroes = (TruepickerHero.List) truepickerHeroDao.getAllEntities(database);
            Iterator<TruepickerHero> iterator = heroes.iterator();
            while (iterator.hasNext()) {
                TruepickerHero hero = iterator.next();
                HeroStats heroStats = heroStatsDao.getShortHeroStats(database, hero.getId());
                if (heroStats.getRoles() != null) {
                    boolean found = filter == null;
                    for (int i = 0, size = heroStats.getRoles().length; !found && i < size; i++) {
                        String role = heroStats.getRoles()[i];
                        if (role.equals(filter)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        iterator.remove();
                    }
                }
            }
            Collections.sort(heroes);
            return heroes;
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public TruepickerHero getTruepickerHeroByTpId(Context context, long tpId) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return truepickerHeroDao.getByTpId(database, tpId);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public TruepickerHero getTruepickerHeroById(Context context, long id) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return truepickerHeroDao.getById(database, id);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void initialize() {
        BeanContainer container = BeanContainer.getInstance();
        service = container.getCounterRemoteEntityService();
        truepickerHeroDao = container.getTruepickerHeroDao();
        heroStatsDao = container.getHeroStatsDao();
    }
}
