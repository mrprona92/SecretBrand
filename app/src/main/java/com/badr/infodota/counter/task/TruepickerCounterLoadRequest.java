package com.badr.infodota.counter.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.counter.service.CounterService;

import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:53
 */
public class TruepickerCounterLoadRequest extends TaskRequest<TruepickerHero.List> {

    private Context mContext;
    private List<Integer> mAllies;
    private List<Integer> mEnemies;

    public TruepickerCounterLoadRequest(Context context, List<Integer> allies, List<Integer> enemies) {
        super(TruepickerHero.List.class);
        mContext = context;
        mAllies = allies;
        mEnemies = enemies;
    }

    @Override
    public TruepickerHero.List loadData() throws Exception {
        BeanContainer beanContainer = BeanContainer.getInstance();
        CounterService service = beanContainer.getCounterService();
        return service.getCounters(mContext, mAllies, mEnemies, 1);

    }
}