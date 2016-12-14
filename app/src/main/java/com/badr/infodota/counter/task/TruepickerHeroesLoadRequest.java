package com.badr.infodota.counter.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.counter.service.CounterService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:52
 */
public class TruepickerHeroesLoadRequest extends TaskRequest<TruepickerHero.List> {
    private String mFilter;
    private Context mContext;

    public TruepickerHeroesLoadRequest(Context context, String filter) {
        super(TruepickerHero.List.class);
        this.mFilter = filter;
        this.mContext = context;
    }

    @Override
    public TruepickerHero.List loadData() throws Exception {
        CounterService counterService = BeanContainer.getInstance().getCounterService();
        return counterService.getTruepickerHeroes(mContext, mFilter);
    }
}
